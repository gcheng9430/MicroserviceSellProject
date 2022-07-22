package com.imooc.sell.dataobject.mapper;

import com.imooc.sell.dataobject.ProductCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface ProductCategoryMapper {
    //通过map写入就像写mysql query 一样 后面values（）里面是type #{}写对应的变量名字和type
    @Insert("insert into product_category(category_name, category_type) values (#{categoryName,jdbcType=VARCHAR},#{category_type,jdbcType=INTEGER})")
    int insertByMap(Map<String,Object> map);

    //通过对象写入 注意后面value的地方 variable一定要跟对象里面的对应起来
    @Insert("insert into product_category(category_name, category_type) values (#{categoryName,jdbcType=VARCHAR},#{categoryType,jdbcType=INTEGER})")
    int insertByObject(ProductCategory productCategory);


    //查询by CategoryType 这个type是unique的查了也只会有一条
    @Select("select * from product_category where category_type = #{categoryType}")
    //查出来还不够 还要显示出来
    @Results({  //每一个result是一个字段 还要把字段名映射成变量 从数据库里的字段变成我们要的对象return的映射
            @Result(column = "category_type",property = "categoryType"),
            @Result(column = "category_id",property = "categoryId"),
            @Result(column = "category_name",property = "categoryName")
            //像create time之类的 虽然数据库里面有字段 但是这里没有写 是不会查出来显示出来的
    })
    ProductCategory findByCategoryType(Integer categoryType);



    //通过category name来查 可能会查到多条 Jpa会给你查到的第一条 但是mybatis很严格必须是list
    @Select("select * from product_category where category_name = #{categoryName}")
    //查出来还不够 还要显示出来
    @Results({  //每一个result是一个字段 还要把字段名映射成变量 从数据库里的字段变成我们要的对象return的映射
            @Result(column = "category_type",property = "categoryType"),
            @Result(column = "category_id",property = "categoryId"),
            @Result(column = "category_name",property = "categoryName")
            //像create time之类的 虽然数据库里面有字段 但是这里没有写 是不会查出来显示出来的
    })
    List<ProductCategory> findByCategoryName(String categoryName);

    //更新
    //根据type更新
    @Update("update product_category set category_name = #{categoryName} where category_type = #{categoryType}")
    int updateByCategoryType(@Param("categoryName")String categoryName,
                             @Param("categoryType")Integer categoryType);

    //根据对象更新
    @Update("update product_category set category_name = #{categoryName} where category_type = #{categoryType}")
    int updateByObject(ProductCategory productCategory);


    //删除
    @Delete("delete from product_category where category_type = #{categoryType}")
    int deleteByCategoryType(Integer categoryType);

    //print sql语句版查询 xml文件使用版
//    ProductCategory selectByCategoryType(Integer categoryType);



}
