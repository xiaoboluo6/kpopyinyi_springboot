package com.zhangmingjian.kpopcenter;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.zhangmingjian"})
@SpringBootApplication
@MapperScan("com.zhangmingjian.kpopcenter.mapper")
@EnableDiscoveryClient //nacos注册启动    nacos用来注册
@EnableFeignClients //feign启动  feign用来调用
public class UcenterMemberApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterMemberApplication.class,args);
    }
}
