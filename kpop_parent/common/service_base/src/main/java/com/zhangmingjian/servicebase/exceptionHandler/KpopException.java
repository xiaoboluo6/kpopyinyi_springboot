package com.zhangmingjian.servicebase.exceptionHandler;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 自定义异常
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KpopException extends RuntimeException{

    // 异常状态码
    private Integer code;
    // 异常消息
    private String msg;
}
