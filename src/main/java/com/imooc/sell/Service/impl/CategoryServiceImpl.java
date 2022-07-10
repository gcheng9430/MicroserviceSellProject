package com.imooc.sell.Service.impl;

import com.imooc.sell.Service.CategoryService;
import com.imooc.sell.dataobject.ProductCategory;
import com.imooc.sell.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 类目 Category Type Service
 * @author guo
 * @date 7/2/22
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepository respository;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return respository.getReferenceById(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return respository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return respository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return respository.save(productCategory);
    }
}
