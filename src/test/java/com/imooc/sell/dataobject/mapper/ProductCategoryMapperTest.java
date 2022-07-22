package com.imooc.sell.dataobject.mapper;

import com.imooc.sell.dataobject.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class ProductCategoryMapperTest {

    @Autowired
    private ProductCategoryMapper mapper;

    @Test
    public void insertByMap() throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("categoryName","师兄最不爱");
        map.put("category_type","101");
        int result =  mapper.insertByMap(map);
        Assert.assertEquals(1,result);
    }

    @Test
    public void insertByObject() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("师兄最不爱");
        productCategory.setCategoryId(102);
        int result =  mapper.insertByObject(productCategory);
        Assert.assertEquals(1,result);
    }


    @Test
    public void findByCategoryType(){
        ProductCategory productCategory = mapper.findByCategoryType(102);
        Assert.assertNotNull(productCategory);
    }

    @Test
    public void findByCategoryName(){
        List<ProductCategory> result = mapper.findByCategoryName("师兄最不爱");
        Assert.assertNotEquals(0,result);
    }

    @Test
    public void updateByCategoryType(){
        int result =mapper.updateByCategoryType("师兄最不爱的分类",102);
        Assert.assertEquals(1,result);
    }

    @Test
    public void updateByObject(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("师兄最不爱");
        productCategory.setCategoryId(102);
        int result =mapper.updateByObject(productCategory);
        Assert.assertEquals(1,result);
    }

    @Test
    public void deleteByCategoryType(){
        int result =mapper.deleteByCategoryType(102);
        Assert.assertEquals(1,result);
    }

//    这个是xml文件使用的方法
//    @Test
//    public void selectByCategoryType(){
//        ProductCategory productCategory=mapper.selectByCategoryType(101);
//        Assert.assertNotNull(productCategory);
//    }




}