package com.imooc.sell.Service;


import com.imooc.sell.dataobject.SellerInfo;

/**
 * 卖家端service
 */
public interface SellerService {
    /**
     * 通过openid查询卖家端信息
     * @param openid
     * @return
     */
    SellerInfo findSellerInfoByOpenid(String openid);
}
