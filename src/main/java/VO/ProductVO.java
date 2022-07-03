package VO;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


/**
 * 商品（包含类目） 类目：商品商品商品
 */
@Data
public class ProductVO {

    @JsonProperty("name") //下面是我自己取的名字方便记忆 这里会让前端依然呈现这里的名字
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;


    //这里用新建的ProductInfoVO而不用原来的ProductInfo是为了安全保护（前端需要什么信息就定义一个VO返回什么信息 不要什么都暴露出去）
    @JsonProperty("food")
    private List<ProductInfoVO> productInfoVOList;

}
