package org.wang.kuaijiback.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class JedisConnection {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private  int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.redis.pool.max-active}")
    private int maxTotal;
    @Value("${spring.redis.pool.max-wait}")
    private int maxWaitMillis;

    @Bean
    public JedisPool getJedisPool(){
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxIdle(maxIdle);
        config.setMaxTotal(maxTotal);
        config.setMaxWaitMillis(maxWaitMillis);
        JedisPool pool=new JedisPool(config,host,port,timeout);
        return pool;
    }


}
