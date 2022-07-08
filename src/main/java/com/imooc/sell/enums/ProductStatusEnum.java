package com.imooc.sell.enums;


import lombok.Getter;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * 商品状态
 */

@Getter
public enum ProductStatusEnum {
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
