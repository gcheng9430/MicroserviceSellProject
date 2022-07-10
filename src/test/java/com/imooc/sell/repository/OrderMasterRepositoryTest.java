package com.imooc.sell.repository;

import com.imooc.sell.dataobject.OrderMaster;
import org.hibernate.annotations.Proxy;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;


import java.awt.print.Pageable;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Proxy(lazy = false)
class OrderMasterRepositoryTest {
    @Autowired
    private OrderMasterRepository repository;

    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("1234568");
        orderMaster.setBuyerName("Someone");
        orderMaster.setBuyerPhone("888888888");
        orderMaster.setBuyerOpenid("110110");
        orderMaster.setBuyerAddress("Chengdu");
        orderMaster.setOrderAmount(new BigDecimal(2.5));
        OrderMaster result = repository.save(orderMaster);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByBuyerOpenid(){
        PageRequest pageRequest = PageRequest.of(0,1);
        Page<OrderMaster> result = repository.findByBuyerOpenid("110110",  pageRequest);
        System.out.println(result.getTotalElements());
        assertNotEquals(0,result.getSize());
    }


}