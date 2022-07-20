package com.imooc.sell.dataobject;

import jdk.jfr.DataAmount;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.annotation.processing.Generated;
import javax.persistence.*;
import java.util.Date;

/**
 * 类目
 * product_category
 * 用注解把数据库映射成对象
 */
@Entity
@DynamicUpdate //使得修改的时候日期会自动变化不然updateTime不会变
@Data   //lombok helps you set up getter setter and toString () otheerise use command+n shortcut
@Proxy(lazy = false)
@Table(name = "ProductCategory")
public class ProductCategory {


    /** 类目id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //自动增加
    @Column(name = "categoryId",unique = true,nullable = false)
    private Integer categoryId;

    /** 类目名字 */
    private String categoryName;

    /** 类目编号 */
    private Integer categoryType;

    private Date createTime;
    private Date updateTime;

    public ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
