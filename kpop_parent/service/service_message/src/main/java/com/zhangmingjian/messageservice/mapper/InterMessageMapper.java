package com.zhangmingjian.messageservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhangmingjian.commonutils.vo.RefreshMemberInfo;
import com.zhangmingjian.messageservice.entity.InterMessage;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface InterMessageMapper extends BaseMapper<InterMessage> {

    // 修改用户信息后 刷新对应的用户留言
    void refreshMessage(RefreshMemberInfo refreshMemberInfo);

    // 一键快速回复
    void quickReply(String messageId);

    // 禁用用户的同时封禁用户的留言
    // 根据memberId 封禁对应的留言
    void disableMessageByMemberId(String memberId);
}
