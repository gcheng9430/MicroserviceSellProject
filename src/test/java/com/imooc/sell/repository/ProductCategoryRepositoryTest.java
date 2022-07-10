package com.imooc.sell.repository;

import com.imooc.sell.dataobject.ProductCategory;
import org.hibernate.annotations.Proxy;
import org.hibernate.query.criteria.internal.expression.function.CurrentTimeFunction;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//没有做unit test隔离
@RunWith(SpringRunner.class)
@SpringBootTest
@Proxy(lazy = false)
class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOneTest(){
        System.out.println(System.getProperty("java.class.path"));
        Optional<ProductCategory> productCategory = repository.findById(1);

        System.out.println(productCategory.toString());
    }
    @Test
    public void saveTest(){
        ProductCategory productCategory = new ProductCategory("女生最爱",3);

        // productCategory.setCategoryName("女生最爱");  //girls' favorite
        //productCategory.setCategoryType(3);
        ProductCategory result = repository.save(productCategory);
        Assert.assertNotNull(result);
        //Assert.assertNotEquals(null,result);
    }

    @Test
    public void updateTest(){
        ProductCategory productCategory  = repository.getReferenceById(9);
        productCategory.setCategoryName("最受人们欢迎的"); //all person's favorite
        productCategory.setCategoryType(3);
        repository.save(productCategory);

    }

    @Test
    public void findByCategoryIdInTest(){
        List<Integer> list = Arrays.asList(2,3);
        List<ProductCategory> result = repository.findByCategoryTypeIn(list);
        //这种查询的时候需要一个无参的构造方法
        Assert.assertNotEquals(0,result.size());
    }

}