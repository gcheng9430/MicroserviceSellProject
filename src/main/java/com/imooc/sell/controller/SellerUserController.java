package com.imooc.sell.controller;


import com.imooc.sell.Service.SellerService;
import com.imooc.sell.config.ProjectUrlConfig;
import com.imooc.sell.constant.CookieConstant;
import com.imooc.sell.constant.RedisConstant;
import com.imooc.sell.dataobject.SellerInfo;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.utils.CookieUtil;
import com.mysql.cj.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 卖家用户相关的操作
 * 涉及到页面跳转和页面呈现所以用controller
 */
@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;


    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                              HttpServletRequest response,
                              Map<String,Object> map){
        //1. openid 去和数据库里的数据匹配
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        //如果查不到
        if (sellerInfo == null){
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url", "/sell/seller/order/list"); //这个是失败信息之的return url 如果登录失败 跳转到订单列表页
            return new ModelAndView("/common/error",map);

        }
        //2. 设置token至redis
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),openid,expire, TimeUnit.SECONDS);


        //3. 设置token至cookie 每次新建然后设置值 exp 再add很烦 extract到util里面
        CookieUtil.set((HttpServletResponse) response, CookieConstant.TOKEN, token, expire);
        //成功的话跳到订单列表界面 但是是跳转 而不是直接进行模版的渲染
        return new ModelAndView("redirect:"+ projectUrlConfig.getSell()+"/sell/seller/order/list");


    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Map<String,Object>map){
        //1. 从cookie里查询
        Cookie cookie = CookieUtil.get(request,CookieConstant.TOKEN);
        if(cookie!=null){
            //2. 清楚redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
            //3. 清除cookie respone,name,value设置为null,过期时间设置为0
            CookieUtil.set(response,CookieConstant.TOKEN,null,0);
        }
        //跳转到成功界面
        map.put("msg",ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url","sell/seller/order/list");
        return new ModelAndView("common/success",map);





    }

}
