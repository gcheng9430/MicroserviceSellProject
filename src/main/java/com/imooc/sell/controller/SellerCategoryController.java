package com.imooc.sell.controller;

import com.imooc.sell.Service.CategoryService;
import com.imooc.sell.dataobject.ProductCategory;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.form.CategoryForm;
import org.simpleframework.xml.core.Validate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * 卖家类目controller
 */
@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 类目列表
     * @param map
     * @return
     */
    @GetMapping("list")
    public ModelAndView list(Map<String,Object> map){
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList",categoryList);
        return new ModelAndView("category/list",map);

    }

    /**
     * 展示
     * @param category
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value ="categoryId",required = false) Integer categoryId,
                              Map<String,Object> map){
        if (categoryId != null){
//            有传值过来 先查询出来
            ProductCategory productCategory = categoryService.findOne(categoryId);
            map.put("category", productCategory);
        }
        return new ModelAndView("category/index",map);

    }

    /**
     * 保存/更新
     * @param form
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Validate CategoryForm form,
                             BindingResult bindingResult,
                             Map<String, Object> map){
//        如果有错误 返回错误页面
        if(bindingResult.hasErrors()){
            //如果失败 跳到错误页面然后回到新增商品的页
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/category/index");
            return new ModelAndView("common/error",map);
        }

//        判断传过来的id是不是null  不是的话再去数据库里查询
        ProductCategory productCategory = new ProductCategory();
        try{
        if (form.getCategoryId() !=null){
            productCategory = categoryService.findOne(form.getCategoryId());
        }
        BeanUtils.copyProperties(form,productCategory);
        categoryService.save(productCategory);
        } catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/category/index");
            return new ModelAndView("common/error",map);
        }

        //        没问题
        map.put("url","/sell/seller/category/list");
        return new ModelAndView("common/success",map);

    }

}
