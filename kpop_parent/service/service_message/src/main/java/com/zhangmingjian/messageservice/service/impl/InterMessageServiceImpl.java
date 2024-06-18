package com.zhangmingjian.messageservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangmingjian.commonutils.vo.RefreshMemberInfo;
import com.zhangmingjian.messageservice.entity.InterMessage;
import com.zhangmingjian.messageservice.mapper.InterMessageMapper;
import com.zhangmingjian.messageservice.service.InterMessageService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author zhangmingjian
 * @since 2024-01-02
 */
@Service
public class InterMessageServiceImpl extends ServiceImpl<InterMessageMapper, InterMessage> implements InterMessageService {

    // 修改用户信息后 刷新对应的用户留言
    @Override
    public void refreshMessage(RefreshMemberInfo refreshMemberInfo) {
        baseMapper.refreshMessage(refreshMemberInfo);
    }

    // 一键快速回复
    @Override
    public void quickReply(String messageId) {
        baseMapper.quickReply(messageId);
    }


    // 禁用用户的同时封禁用户的留言
    // 根据memberId 封禁对应的留言
    @Override
    public void disableMessageByMemberId(String memberId) {
        baseMapper.disableMessageByMemberId(memberId);
    }
}
