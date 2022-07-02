package com.imooc.sell;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j //就不用自己去initialize logger还要放这个class的名字 下面的logger也直接写log就可以了
public class LoggerTest {
    //private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);
    @Test
    public void test1(){
        String name = "imooc";
        String password = "123456";
        log.debug("debug...");
        log.info("name:{},password:{}",name,password); //大括号是占位符
        log.error("error...");
        log.warn("warn...");

    }
}
