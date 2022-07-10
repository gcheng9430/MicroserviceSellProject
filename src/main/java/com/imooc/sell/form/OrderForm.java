package com.imooc.sell.form;


import com.sun.istack.NotNull;
import lombok.Data;


/**
 * 用于表单验证 就是前端传回来的参数 太多了所以单独做一个class
 */
@Data
public class OrderForm {

    /**
     * 买家姓名
     */
    //@NotEmpty(message = "姓名必填 name required")
    @NotNull
    private String name;

    /**
     * 买家手机号
     */
    @NotNull
    private String phone;

    /**
     * 买家对峙
     */
    @NotNull
    private String address;

    /**
     * 买家微信id
     */
    @NotNull
    private String openid;

    /**
     * 购物车
     */
    @NotNull
    private String items;



}
