package com.zhangmingjian.kpopcenter.client;

import com.zhangmingjian.commonutils.vo.RefreshMemberInfo;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MessageDegradedFeignClient implements MessageClient{
    @Override
    public void refreshMessageByMemberInfo(RefreshMemberInfo refreshMemberInfo) {
        System.out.println("nacos服务中的service-message断连");
    }

    @Override
    public void disableMessageByMemberId(String memberId) {
        System.out.println("nacos服务中的service-message断连");
    }
}