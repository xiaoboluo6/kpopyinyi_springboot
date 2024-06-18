package com.zhangmingjian.kpopcenter.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan("com.zhangmingjian.kpopcenter.mapper")
@EnableTransactionManagement   // 启用 Spring 的事务管理功能
public class UcenterConfig {

    /**
     * 添加分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
