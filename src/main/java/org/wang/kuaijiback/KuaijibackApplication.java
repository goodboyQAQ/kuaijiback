package org.wang.kuaijiback;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

//@EnableCaching  开启缓存
@SpringBootApplication
public class KuaijibackApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(KuaijibackApplication.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意这里要指向原先用main方法执行的Application启动类
        return builder.sources(KuaijibackApplication.class);
    }
}
