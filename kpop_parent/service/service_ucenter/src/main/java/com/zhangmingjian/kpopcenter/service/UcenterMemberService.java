package com.zhangmingjian.kpopcenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhangmingjian.kpopcenter.entity.UcenterMember;
import com.zhangmingjian.kpopcenter.entity.vo.LoginVo;
import com.zhangmingjian.kpopcenter.entity.vo.RegisterVo;

public interface UcenterMemberService extends IService<UcenterMember> {

    String login(LoginVo loginVo);

    void register(RegisterVo registerVo);

    // 禁用用户
    void block(String memberId);

    // 解封用户
    void unblock(String memberId);
}
