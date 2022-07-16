package com.imooc.sell.Service.impl;


import com.imooc.sell.Service.PayService;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.utils.JsonUtil;
import com.imooc.sell.utils.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class PayServiceImpl implements PayService {

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderServiceImpl orderService;



    private static final String ORDER_NAME = "wechat food order";
    @Override
    public PayResponse create(OrderDTO orderDTO){
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【WechatPay】initiate pay, request={}", JsonUtil.toJson(payRequest));

        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【WechatPay】initiate pay, response={}",JsonUtil.toJson(payResponse));
        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {
        //1. 为了安全 验证签名-有可能是别人不是微信给你发的  bestPaySDK已经帮我们做了
        //2。 支付状态 -有可能是支付失败  bestPaySDK已经帮我们做了
        //3. 支付金额 是不是还是一样的
        //4. 下单人 == 支付人  看业务具体需求

        PayResponse payResponse =  bestPayService.asyncNotify(notifyData);
        log.info("【WechatPay】asych notify, payResponse={}",payResponse);

        //查询订单
        OrderDTO orderDTO = orderService.findOne(payResponse.getOrderId());

        //判断订单是否存在
        if (orderDTO == null){
            log.error("【WechatPay】asych notify, order doesn't exist. orderId={}",payResponse.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //判断金额是否一致 这里注意 一个double一个big decimal 要转成bigDecimal而且要用conpareTo not equals
        // 还要考虑 0.10和0.1  解法：相减如果精度小于某个精度就算相等 这个 在math Util里面
        if (!MathUtil.equals(payResponse.getOrderAmount(),orderDTO.getOrderAmount().doubleValue())){
            log.error("【WechatPay】asych notify,order Amount not equal! orderId={}, notifyAmount={}, systemAmount={}",
                    payResponse.getOrderId(),
                    payResponse.getOrderAmount(),
                    orderDTO.getOrderAmount()
            );
            throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
        }


        //修改订单支付状态
        orderService.paid(orderDTO);

        return payResponse;
    }

    /**
     * 退款  也是直接调用sdk
     * @param orderDTO
     */
    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest request = new RefundRequest();
        request.setOrderId(orderDTO.getOrderId());
        request.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        request.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【wechat refund】request={}",JsonUtil.toJson(request));

        RefundResponse refundResponse = bestPayService.refund(request);
        log.info("【wechat refund】refundResponse={}",JsonUtil.toJson(refundResponse));

    }
}
