package com.imooc.sell.Service.impl;

import com.imooc.sell.Service.PushMessageService;
import com.imooc.sell.config.WechatAccountConfig;
import com.imooc.sell.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


/**
 * 在什么时候推送这个消息呢 在订单完结的时候 所以去orderServiceImpl
 */
@Service
@Slf4j
public class PushMessageServiceImpl implements PushMessageService {

    @Qualifier("wxMpService")
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WechatAccountConfig accountConfig;

    @Override
    public void orderStatus(OrderDTO orderDTO) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setTemplateId(accountConfig.getTemplateId().get("orderStatus"));
        templateMessage.setToUser(orderDTO.getBuyerOpenid());


        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first", "亲，请记得收货"),
                new WxMpTemplateData("keyword1", "微信点餐"),
                new WxMpTemplateData("keyword2", "188888"),
                new WxMpTemplateData("keyword3", orderDTO.getOrderId()),
                new WxMpTemplateData("keyword4", orderDTO.getOrderStatusEnum().getMsg()),
                new WxMpTemplateData("keyword5", "￥" + orderDTO.getOrderAmount()),
                new WxMpTemplateData("remark", "欢迎再次光临")
                );

        templateMessage.setData(data);


        try{
        wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        }catch(WxErrorException e){
            log.error("【Wechat Template Message】Sending Failure,{}",e);

        }


    }
}
