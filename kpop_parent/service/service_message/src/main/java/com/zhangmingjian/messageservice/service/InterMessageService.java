package com.zhangmingjian.messageservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhangmingjian.commonutils.vo.RefreshMemberInfo;
import com.zhangmingjian.messageservice.entity.InterMessage;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author zhangmingjian
 * @since 2024-01-02
 */
public interface InterMessageService extends IService<InterMessage> {

    // 修改用户信息后 刷新对应的用户留言
    void refreshMessage(RefreshMemberInfo refreshMemberInfo);

    // 一键快速回复
    void quickReply(String messageId);


    // 禁用用户的同时封禁用户的留言
    // 根据memberId 封禁对应的留言
    void disableMessageByMemberId(String memberId);
}
