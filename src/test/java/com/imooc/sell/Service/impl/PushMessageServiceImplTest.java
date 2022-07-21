package com.imooc.sell.Service.impl;

import com.imooc.sell.Service.PushMessageService;
import com.imooc.sell.dto.OrderDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class PushMessageServiceImplTest {

    @Autowired
    private PushMessageServiceImpl pushMessageService;

    @Autowired
    private OrderServiceImpl orderService;

    @Test
    public void orderStatus() throws Exception{

        OrderDTO orderDTO = orderService.findOne("123456");

        pushMessageService.orderStatus(orderDTO);

    }

}