package com.imooc.sell.controller;


import com.imooc.sell.Service.OrderService;
import com.imooc.sell.Service.impl.OrderServiceImpl;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 卖家端 订单
 */
@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 订单列表
     * @param Page 第几页 从第一页开始
     *        size 一页有多少数据
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String,Object> map){
        PageRequest request =  PageRequest.of(page-1,size); //因为这个是从第0页开始
        Page<OrderDTO> orderDTOPage= orderService.findList(request);
        //最后再把这个结果写到模板里面去
        map.put("orderDTOPage",orderDTOPage);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("order/list",map); //这个模板怎么用呢 第一个是模板路径 第二个是map：名字：内容
    }


    /**
     * 取消订单
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId")String orderId,
                               Map<String,Object> map){
//        如果错误跳到错误页面
//        先查询并且取消 如果不存在之类的 查询里面就会throw Exception ，
//        catch之后显示error界面 common/error里的error界面设置了SetTImeOut只会存在三秒就跳转到list界面
        try{
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        }catch(SellException e){
            log.error("【Seller Cancel Order】cauth exception {}",e);
//            发生错误了 传一个提示信息 一个跳转url
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/order/list"); //跳到订单列表页
            return new ModelAndView("common/error",map);
        }
//        如果成功跳到成功的界面
        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list"); //跳到订单列表页
        return new ModelAndView("common/success",map);



    }

    /**
     * 订单详情
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId")String orderId,
                               Map<String,Object> map){
        OrderDTO orderDTO = new OrderDTO();
        try{
            orderDTO = orderService.findOne(orderId);
        }catch(SellException e){
            log.error("【Seller Show Order Details】cauth exception {}",e);
//            发生错误了 传一个提示信息 一个跳转url  //跳到订单列表页
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }

        map.put("orderDTO",orderDTO);
        //如果成功 应该跳到订单详情页
        return new ModelAndView("order/detail",map);
    }

    /**
     * 完结订单
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/finish")
    public ModelAndView finished(@RequestParam("orderId")String orderId,
                                 Map<String,Object> map){

        try{
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.finish(orderDTO);
            }catch(SellException e){
            log.error("【Seller finish Order】caught exception {}",e);
//            发生错误了 传一个提示信息 一个跳转url
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/order/list"); //跳到订单列表页
            return new ModelAndView("common/error",map);
        }
        //如果成功跳到成功的界面
        map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list"); //跳到订单列表页
        return new ModelAndView("common/success",map);


    }

}
