package com.imooc.sell.Service;


import lombok.extern.slf4j.Slf4j;
import org.simpleframework.xml.core.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 分布式锁
 */
@Component
@Service
@Slf4j
public class RedisLock {
    @Autowired
    private StringRedisTemplate redisTemplate;

  /**
   * @param key: productId
   * @param value: 当前时间+超时时间
   * @return
   */
  public boolean lock(String key, String value) {
    if (redisTemplate.opsForValue().setIfAbsent(key, value)) {
      // 锁住 返回true
      return true;
    }

    // 如果不加下面这一堆代码 会造成死锁 就是比如用锁的那段代码中间某个地方出问题了 抛了
    // 一个异常 他就进行不到解锁哪一步 然后下一个请求发过来 他加锁 到我们这里 因为已经有value
    // 没有这堆代码的话 咱们是没有才设置 有就不设置 return false 他因为之前那个没解开嘛
    // 这里就一直return false 大家都没法继续加锁了 就成了死锁
    // 假设上面锁被占用了 已经有值  现在两个线程同时到了这一步 两个人都得到了currentValue=A 两个人的value都是B
    String currentValue = redisTemplate.opsForValue().get(key);
    // 如果锁超时/过期
    if (!StringUtils.isEmpty(currentValue)
        && Long.parseLong(currentValue) < System.currentTimeMillis()) {
      // 在设置新的value的时候同时get被替换掉的value 这一步getAndSet只会有一个线程在执行 要么第一个要么第二个
      String oldValue = redisTemplate.opsForValue().getAndSet(key, value);
      // 假设第一个线程成功执行了上一条指令 把自己的B给设置进去了 他得到的value是A  跟刚刚一样 所以对他来说是成功 return true
      // 而第二个线程执行上一步不幸排到了刚刚那条线程之后 那他读到的value就是被刚刚那个线程放进去的B 跟最初读取的A不一样 所以没有抢到锁 出去return false
      // 这个判断保证了只有一个线程 也就是第一个写进去的线程抢到锁
      if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
        return true;
      }
    }
    //上面这一堆拿来处理异常情况


    return false;
    }

    /**
     * 解锁
     */
    public void unlock(String key,String value){
        try {
        //获取值并进行判断
        String currentValue=redisTemplate.opsForValue().get(key);
        //如果不为空且等于咱们现在放进去的东西
        if (!StringUtils.isEmpty(currentValue)&& currentValue.equals(value)){
            //删掉=解锁
            redisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch(Exception e){
            log.error("【redis分布式锁】解锁异常,{}",e);
        }


    }



}
