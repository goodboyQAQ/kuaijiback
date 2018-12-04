package org.wang.kuaijiback;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

//@EnableCaching  开启缓存
@SpringBootApplication
public class KuaijibackApplication {

    public static void main(String[] args) {
        SpringApplication.run(KuaijibackApplication.class, args);
    }
}
