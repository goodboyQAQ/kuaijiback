package org.wang.kuaijiback.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wang.kuaijiback.config.JedisConnection;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class RedisUtil {
    @Autowired
    private JedisPool jedisPool;

    private static Jedis jedis=null;

    public boolean hasKey(String key){
        Jedis jedis=jedisPool.getResource();
        boolean b=jedis.exists(key);
        jedis.close();
        return b;
    }

    public void set(String key,String value,int expire_s){
        Jedis jedis=jedisPool.getResource();
        // NX是不存在时才set， XX是存在时才set， EX是秒，PX是毫秒
        jedis.set(key,value,"NX", "EX",expire_s);
        jedis.close();
    }

    public String get(String key){
        Jedis jedis=jedisPool.getResource();
        String value=jedis.get(key);
        return value;
    }

    public long del(String key){
        Jedis jedis=jedisPool.getResource();
        long i=jedis.del(key);
        jedis.close();
        return i;
    }



}
