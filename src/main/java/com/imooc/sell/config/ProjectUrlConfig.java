package com.imooc.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 对应的是configuration（也就是application.yml)里面的东西 那个"projectUrl"就是root
 */
@Data
@ConfigurationProperties(prefix = "projectUrl")
@Component
public class ProjectUrlConfig {

    /**
     * 微信公众平台授权url
     */
    public String wechatMpAuthorize;

    /**
     * 微信开放平台url
     *
     */
    public String wechatOpenAuthorize;

    /**
     * 点餐系统
     */
    public String sell;


}
