package com.imooc.sell.Service.impl;

import com.imooc.sell.Service.RedisLock;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class SecKillServiceImpl {


    private static final int TIMEOUT= 10*1000;//超时时间 十秒
    @Autowired
    private RedisLock redisLock;


    /**
     * 国庆活动 皮蛋粥特价 限量100000份
     */
    static Map<String,Integer> products; //productId 对应这个product活动开始有多少份
    static Map<String,Integer> stock; //productId: 还剩多少stock
    static Map<String,String> orders; //unique单号对应 productId
    static {
        /**
         * 模拟多个表商品信息表 库存表 秒杀成功订单表
         */
        products= new HashMap<>();
        stock =new HashMap<>();
        orders= new HashMap<>();
        products.put("123456",100000);
        stock.put("12345",100000);
    }




    /**
     *
     * @param productId
     * @return
     */
    public String queryMap(String productId){
        return "特供特价皮蛋粥"+products.get(productId)+"份，"
                +"已经下单"+orders.size()+"份，"
                +"剩余"+stock.get(productId)+"份！";
    }

    public String querySecKillProductInfo(String productId){
        return this.queryMap(productId);
    }

    /**
     *
     * 秒杀下单某一商品
     * @param productId
     */
    public void orderProductMockDiffUser(String productId){
        //加锁
        //定义一个时间加一个超时时间
        long time = System.currentTimeMillis()+TIMEOUT;
        if (!redisLock.lock(productId, String.valueOf(time))){
            //不成功的话抛出异常
            throw new SellException(101,"哎呦喂，人也太多了，换个姿势再试试！");
        }
        //被锁住才会执行下面的代码

        //1. 查询该商品库存
        int stockNum= stock.get(productId);
        //如果没有库存了 活动结束
        if (stockNum == 0){
            throw  new SellException(100,"活动结束");
        }else{
            //2. 下单（模拟不同用户openid不同
            orders.put(KeyUtil.genUniqueKey(),productId);
            //3. 减库存 先减再放入
            stockNum = stockNum-1;
            try{
                Thread.sleep(100);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            stock.put(productId,stockNum);
        }

        //解锁
        redisLock.unlock(productId,String.valueOf(time));




    }
}
