package com.imooc.sell.Service.impl;

import com.imooc.sell.Service.ProductService;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.dto.CartDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.ProductInfoRepository;
import com.imooc.sell.enums.ProductStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
//@CacheConfig(cacheNames = "product")  避免在function里面反复写cacheNames了
public class ProductInfoServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository repository;


//    @Cacheable(cacheNames = "product",key="123")
    @Override
    public ProductInfo findOne(String productId) {
        return repository.getReferenceById(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

//    @CachePut(cacheNames = "product",key="123")
    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO:cartDTOList){
            //先查询
            ProductInfo productInfo = repository.getReferenceById(cartDTO.getProductId());
            if (productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock()+cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }


    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        //对于每一个
        for (CartDTO cartDTO:cartDTOList){
            //先用id找到对应的productinfo
            ProductInfo productInfo = repository.getReferenceById(cartDTO.getProductId());
            //如果没有 throw error 商品不存在
            if (productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //存在的话算接故宫的库存 stock-买的数量
            Integer result = productInfo.getProductStock()-cartDTO.getProductQuantity();
            if (result<0){ //如果低于0也是error
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            //设置新的库存然后save
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }

    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = repository.getReferenceById(productId);
        //如果找不着
        if (productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        //如果已经上架了
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        //更新 并储存
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return repository.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = repository.getReferenceById(productId);
        //如果找不着
        if (productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        //如果已经下架了
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        //更新 并储存
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return repository.save(productInfo);
    }
}
