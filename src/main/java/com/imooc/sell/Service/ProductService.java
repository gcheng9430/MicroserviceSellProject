package com.imooc.sell.Service;

import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.dto.CartDTO;
import com.imooc.sell.repository.ProductInfoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {


    ProductInfo findOne(String productId);

    /**
     * 查询所有在架商品列表
     *
     */
    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存add stock
    void increaseStock(List<CartDTO> cartDTOList);



    //减库存reduce stock
    void decreaseStock(List<CartDTO> cartDTOList);

    //上架
    ProductInfo onSale(String productId);
    //下架
    ProductInfo offSale(String productId);
}
