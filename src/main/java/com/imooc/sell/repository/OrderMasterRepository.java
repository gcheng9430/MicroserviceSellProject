package com.imooc.sell.repository;

import com.imooc.sell.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {
    //通过用户来查找订单主表
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenId, Pageable pageable);

}
