package com.zhangmingjian.commonutils;


import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)   // 生成的setter方法返回当前对象，从而支持链式调用。例如，object.setField1(value1).setField2(value2)
public class R {


    private boolean success;

    private Integer code;

    private String message;

    private Map<String,Object> data = new HashMap<>();


    // 通过将构造方法设为私有，外部代码就无法直接创建类的实例，只能通过类内部提供的静态方法或其他方式来获取实例。
    // 简单来说 这里使用private 外部就不能new一个实例来使用  只能调用内部的静态构造方法
    private R(){}

    /**
     * 这里其实很有意思
     * ！！！！！！！！！！！！！！！！！！！！！！！！
     * 虽然构造方法被private私有了，但是使用R里面的static静态方法  == new一个新的对象   说到底，还是new一个实体类
     * 唯一的区别在于 static静态方法返回的对象是可以的经过处理的 可以做一些预设
     */
    public static R ok(){
        R r = new R();
        r.setSuccess(true).setCode(ResultCode.SUCCESS).setMessage("成功");
        return r;
    }

    // 失败静态方法
    public static R error(){
        R r = new R();
        r.setSuccess(false).setCode(ResultCode.ERROR).setMessage("失败");
        return r;
    }

    /**
     * 后面的方法可以使用链式编程   比如 R.ok().data() 或者 R.ok.code()   完善或者修改实体类里面的信息
     */
    public R success(boolean success){
        this.setSuccess(success);
        return this;  // 这里的this就是实体类，通过链式编程的方式 对实体类的信息进行修改补充  后面都是一样的
    }

    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    public R data(String key,Object value){
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> map){
        this.setData(map);
        return this;
    }









}
