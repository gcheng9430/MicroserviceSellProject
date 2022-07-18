package com.imooc.sell.utils;


import com.imooc.sell.enums.CodeEnum;

/**
 * 给一个code和一个enumClass 比如payStatus或者orderStatus 返回这个class里
 * 这个codee对应的枚举 比如我给0和orderStatus 就应该返回orderStatus.NEW
 */
public class EnumUtil {
    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass){
        for (T each: enumClass.getEnumConstants()){
            if (code.equals(each.getCode())){
                return each;
            }
        }
        return null;
    }
}
