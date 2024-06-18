package com.zhangmingjian.kpopcenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhangmingjian.kpopcenter.entity.UcenterMember;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    // 禁用用户
    void block(String memberId);


    // 解封用户
    void unblock(String memberId);
}
