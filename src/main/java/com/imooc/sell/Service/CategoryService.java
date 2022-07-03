package com.imooc.sell.Service;


import com.imooc.sell.dataobject.ProductCategory;

import java.util.List;
import java.util.Optional;

/**
 * Product Category Service（类目）
 */
public interface CategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
