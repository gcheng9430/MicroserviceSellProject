package com.imooc.sell.handler;


import com.imooc.sell.config.ProjectUrlConfig;
import com.imooc.sell.exception.SellerAuthorizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class SellerExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    //拦截登录异常 拦截完了做一个跳转
    @ExceptionHandler(value= SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException(){
        //这里用到跳转 跳到登录界面去
        return new ModelAndView("redirect:"
                .concat(projectUrlConfig.getWechatOpenAuthorize()) //注意这里是授权地址
                .concat("/sell/wechat/qrAuthorize")
                .concat("?returnUrl=")
                .concat(projectUrlConfig.getSell())
                .concat("/sell/seller/login"));

    }

}
