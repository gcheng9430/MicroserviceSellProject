package com.imooc.sell.aspect;

import com.imooc.sell.constant.CookieConstant;
import com.imooc.sell.constant.RedisConstant;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.exception.SellerAuthorizeException;
import com.imooc.sell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 卖家登录之前是看不到订单和商品列表的
 */
@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 这里定义的是切入点
     * 以seller开头的controller(但不是让seller登录的那些controller）
     */
    @Pointcut("execution(public * com.imooc.controller.Seller*.*(..))"+
    "&& !execution(public * com.imooc.controller.SellerUserController.*(..))")
    public void verify(){}

    /**
     * 什么时候操作？切入点之前
     */
    @Before("verify()")
    public void doVerify(){
        //获取需要的http request
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //查询cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie==null){
            log.warn("【login authentication】cannot find token in cookie");
            throw new SellerAuthorizeException();
        }
        //去redis里查询
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX+cookie.getValue()));
        if (StringUtils.isEmpty(tokenValue)){
            log.warn("【login authentication】cannot find token in redis");
            throw new SellerAuthorizeException();
        }



    }
}
