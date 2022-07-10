package com.imooc.sell.Service.impl;

import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;



import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductInfoServiceImplTest {
    @Autowired
    private ProductInfoServiceImpl productService;
    @Test
    void findOne() throws Exception{
        ProductInfo productInfo = productService.findOne("123456");
        Assert.assertEquals(String.valueOf("123456"),productInfo.getProductId());
    }

    @Test
    void findUpAll() throws Exception{
        List<ProductInfo> productInfoList =  productService.findUpAll();
        Assert.assertNotEquals(0,productInfoList.size());
    }

    @Test
    void findAll() throws Exception{
        PageRequest request = PageRequest.of(0,2);  //查第几页 在那一页有多少条内容
        Page<ProductInfo> productInfoPage  = productService.findAll(request);
        //System.out.println(productInfoPage.getTotalElements());
        Assert.assertNotEquals(0,productInfoPage.getTotalElements());

    }

    @Test
    void save() throws Exception{
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123457");
        productInfo.setProductName("shrimp");
        productInfo.setProductPrice(new BigDecimal("3.2"));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("Great shrimp");
        productInfo.setProductIcon("http://XXX.jpe");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(2);

        //存的时候就reutrn一个然后assert not null
        ProductInfo result = productService.save(productInfo);
        Assert.assertNotNull(result);
    }
}