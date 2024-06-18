package com.zhangmingjian.kpopservice;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.zhangmingjian"})
public class KpopApplication {

    public static void main(String[] args) {
        SpringApplication.run(KpopApplication.class,args);
    }

}
