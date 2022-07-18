package com.imooc.sell.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.converter.json.GsonFactoryBean;

public class JsonUtil {
    public static String toJson(Object object){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson =gsonBuilder.create();
        return gson.toJson(object);
    }
}
