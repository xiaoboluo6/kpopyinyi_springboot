package com.zhangmingjian.messageservice.client;


import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MemberDegradedFeignClient implements MemberClient{
    @Override
    public Map<String, String> getMemberMapInfoById(String memberId) {
        System.out.println("nacos服务中的service-ucenter断连");
        return null;
    }

    @Override
    public boolean getMemberIsDisabled(String memberId) {
        System.out.println("nacos服务中的service-ucenter断连");
        return true;  // 这里表示系统有问题
    }
}
