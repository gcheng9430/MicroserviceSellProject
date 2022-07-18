package com.imooc.sell.controller;


import com.imooc.sell.Service.BuyerService;
import com.imooc.sell.Service.OrderService;
import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.converter.OrderForm2OrderDTOConverter;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.form.OrderForm;
import com.imooc.sell.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private BuyerService buyerService;

    //创建订单 给我一堆buyer信息外加什么东西要几个（items）（也是string 但是是JSON格式）创建创建存数据库完了过后返回一个成功code成功msg外加一个创建好的orderId（返还的data里面是 orderId：orderId所以可以用一个map）
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Validated OrderForm orderForm,
                                               BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确, orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            //万一购物车是空的
            log.error("【创建订单】购物车 不能为空 ");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO createResult = orderService.create(orderDTO);
        Map<String,String> map = new HashMap<>();
        map.put("orderId", createResult.getOrderId());
        return ResultVOUtil.success(map);

    }

    @GetMapping("/List")
    //订单列表  given OpenId和page和size 返回的ResultVO的data应该是订单列表orderDTO(但是orderDetiallist可以空的）
    public ResultVO<List<OrderDTO>>  List(@RequestParam("openid") String openid,
                                          @RequestParam(value = "page", defaultValue = "0") Integer page,
                                          @RequestParam(value = "size", defaultValue = "10") Integer size){
        //openid不能为空
        if (StringUtils.isEmpty(openid)){
            log.error("【查询订单列表】open id为空is empty");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest request =  PageRequest.of(page,size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid,request );
        return ResultVOUtil.success(orderDTOPage.getContent());
    }


    //订单详情/c查询单个订单 （given buyer openid and orderId 返回orderDTO再resulVO的data里 注意这里OrderDetail不能是空的了
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId){
        OrderDTO orderDTO = buyerService.findOrderOne(openid,orderId);
        return ResultVOUtil.success(orderDTO);
    }

    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel (@RequestParam("openid") String openid,
                            @RequestParam("orderId") String orderId){

        // 不安全 改进 改完了 用了BuyerService去查该订单 是不是该用户的并且在哪里取消
        buyerService.cancelOrder(openid, orderId);
        return ResultVOUtil.success();
    }


}
