package com.zhangmingjian.messageservice.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Component
@FeignClient(value = "service-ucenter",fallback = MemberDegradedFeignClient.class)
public interface MemberClient {

    // 调用nacos服务中的service-ucenter中的方法  根据memberId获取member信息
    @GetMapping("/usercenter/member/getMemberInfoById/{memberId}")
    public Map<String,String> getMemberMapInfoById(@PathVariable("memberId") String memberId);

    // 给feign调用 根据memberId返回member是否被封禁
    @GetMapping("/usercenter/member/getMemberIsDisabled/{memberId}")
    public boolean getMemberIsDisabled(@PathVariable("memberId") String memberId);
    
}
