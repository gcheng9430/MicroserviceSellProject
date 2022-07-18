package com.imooc.sell.controller;


import com.imooc.sell.Service.CategoryService;
import com.imooc.sell.Service.ProductService;
import com.imooc.sell.dataobject.ProductCategory;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.exception.SellException;
import org.apache.catalina.valves.rewrite.Substitution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * 卖家端商品
 */
@Controller
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;
    /**
     * 列表
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String,Object> map){

        PageRequest request =  PageRequest.of(page-1,size); //因为这个是从第0页开始
        Page<ProductInfo> productInfoPage= productService.findAll(request);
        //最后再把这个结果写到模板里面去
        map.put("productInfoPage",productInfoPage);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("product/list",map); //这个模板怎么用呢 第一个是模板路径 第二个是map：名字：内容

    }

    /**
     * 上架
     * @param productId
     * @param map
     * @return
     */
    @RequestMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String,Object> map){
        try{
        productService.onSale(productId);
        } catch(SellException e){
            //如果失败 跳到错误页面然后回到商品列表页
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        //如果成功的话 跳到成功页
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("/common/success",map);

    }

    /**
     * 下架
     * @param productId
     * @param map
     * @return
     */
    @RequestMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                               Map<String,Object> map){
        try{
            productService.offSale(productId);
        } catch(SellException e){
            //如果失败 跳到错误页面然后回到商品列表页
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        //如果成功的话 跳到成功页
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("/common/success",map);

    }

    @GetMapping("/index")
    public void index(@RequestParam(value = "productId", required = false) String productId,
                      Map<String,Object> map){
        if(StringUtils.isEmpty(productId)){
            ProductInfo productInfo =  productService.findOne(productId);
            map.put("productInfo", productInfo);
        }
        //查询所有的类目
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList",categoryList);

        return new ModelAndView("/product/index",map);
    }

    @PostMapping("/save")
    public ModelAndView save(){
        
    }


}
