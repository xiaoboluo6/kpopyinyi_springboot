package com.zhangmingjian.vcodeservice.controller;


import com.mysql.cj.util.StringUtils;
import com.zhangmingjian.commonutils.R;
import com.zhangmingjian.vcodeservice.service.VCodeService;
import com.zhangmingjian.vcodeservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/verificationCode/code")
@CrossOrigin
public class VCodeController {

    @Autowired
    private VCodeService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("send/{phone}")
    public R sendCode(@PathVariable String phone){

        // 1.已经发送过并存储在redis中 不需要再次发送 直接去redis找
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isNullOrEmpty(code)){
            return R.ok().message("上次发送的验证码还未过期，可以输入使用");
        }


        // 2.发送验证码 并存储在redis
        // 生成随机四位验证码
        code = RandomUtil.getFourBitRandom();
        // 根据阿里云验证码传输的规则 设置一个map  {code}
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
        boolean isSend = msmService.send(param,phone);
        // 然后存放到redis里面
        if(isSend){
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            // return R.ok().message("短信发送成功");
            return R.ok().data("code",code).data("success",isSend);
        }else{
            return R.error().message("短信发送失败");
        }
    }
}
