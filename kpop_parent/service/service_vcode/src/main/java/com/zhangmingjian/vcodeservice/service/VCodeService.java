package com.zhangmingjian.vcodeservice.service;

import java.util.Map;

public interface VCodeService {

    // 发送验证码
    boolean send(Map<String, Object> param, String phone);
}
