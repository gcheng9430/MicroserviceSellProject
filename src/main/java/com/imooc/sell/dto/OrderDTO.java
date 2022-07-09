package com.imooc.sell.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
    //订单ID
    private String orderId;
    //买家姓名
    private String buyerName;
    //买家电话
    private String buyerPhone;
    //买家地址
    private String buyerAddress;
    //买家openid
    private String buyerOpenid;
    //订单总额
    private BigDecimal orderAmount;
    //订单状态 默认为新下单 0是新下单 2是已完成 2是已取消
    private Integer orderStatus ;
    //支付状态 默认为0未支付
    private Integer payStatus ;
    //创建时间
    //@JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
    //更新时间
    //@JsonSerialize(using  = Date2LongSerializer.class)
    private Date updateTIme;

    private List<OrderDetail> orderDetailList;


}
