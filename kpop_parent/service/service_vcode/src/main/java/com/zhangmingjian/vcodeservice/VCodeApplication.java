package com.zhangmingjian.vcodeservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)  // 取消数据源的自动配置
@ComponentScan({"com.zhangmingjian"})
public class VCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(VCodeApplication.class,args);
    }

}


// 解释一下这里为什么不需要nacos 方法调用
// 短信发送的触发条件是 前端点击发送验证码 然后把验证码存入服务器(这里就是本地)的redis中
// 后续如果需要判别验证码是否正确 直接和redis中的验证码进行比较就可以了