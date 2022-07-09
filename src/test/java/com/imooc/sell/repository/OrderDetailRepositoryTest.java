package com.imooc.sell.repository;

import com.imooc.sell.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class OrderDetailRepositoryTest {
    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void saveTest(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("1111112");
        orderDetail.setOrderId("12345670");
        orderDetail.setProductIcon("xxx.jpg");
        orderDetail.setProductId("a111");
        orderDetail.setProductName("pumpkin");
        orderDetail.setProductPrice(new BigDecimal("2.5"));
        orderDetail.setProductQuantity(3);
        OrderDetail result = repository.save(orderDetail);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderIdTest(){
        List<OrderDetail> orderDetailList = repository.findByOrderId("12345678");
        assertNotEquals(0,orderDetailList.size());
    }

}