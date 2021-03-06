package com.imooc.sell.enums;


import lombok.Getter;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * εεηΆζ
 */

@Getter
public enum ProductStatusEnum implements CodeEnum{
    UP(0,"in stock"),
    DOWN(1,"out of stock")
    ;
    private Integer code;

    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}
