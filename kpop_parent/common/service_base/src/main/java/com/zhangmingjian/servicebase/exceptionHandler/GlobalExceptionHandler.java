package com.zhangmingjian.servicebase.exceptionHandler;


import com.zhangmingjian.commonutils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice  // 它通常被用于集中处理异常和全局数据绑定，为多个控制器提供共享的功能。
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行全局异常处理");
    }

    @ExceptionHandler(KpopException.class)
    public R error(KpopException e){
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMessage());
    }
}
