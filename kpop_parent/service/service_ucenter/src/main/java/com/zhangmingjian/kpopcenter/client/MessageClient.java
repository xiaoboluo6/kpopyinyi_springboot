package com.zhangmingjian.kpopcenter.client;


import com.zhangmingjian.commonutils.R;
import com.zhangmingjian.commonutils.vo.RefreshMemberInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(value = "service-message",fallback = MessageDegradedFeignClient.class)
public interface MessageClient {

    // 调用nacos服务中的service-message中的方法  根据memberId获取member信息
    // 修改用户信息后  根据用户id修改message里面存储的信息
    @PostMapping("/interaction/message/refreshMessageByMemberInfo")
    public void refreshMessageByMemberInfo(@RequestBody RefreshMemberInfo refreshMemberInfo);


    // 调用nacos服务中的service-message中的方法  根据memberId 封禁对应的留言
    // 禁用用户的同时封禁用户的留言
    @GetMapping("/interaction/message/disableMessageByMemberId/{memberId}")
    public void disableMessageByMemberId(@PathVariable("memberId") String memberId);
}
