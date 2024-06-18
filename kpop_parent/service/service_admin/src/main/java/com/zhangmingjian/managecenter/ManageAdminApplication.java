package com.zhangmingjian.managecenter;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.zhangmingjian"})
public class ManageAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManageAdminApplication.class,args);
    }
}
