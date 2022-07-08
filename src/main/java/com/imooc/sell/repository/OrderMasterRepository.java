package com.imooc.sell.repository;

import com.imooc.sell.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {
    //通过用户来查找订单主表
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenId, Pageable pageable);

}
