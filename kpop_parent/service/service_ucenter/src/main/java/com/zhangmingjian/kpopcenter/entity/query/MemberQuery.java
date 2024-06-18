package com.zhangmingjian.kpopcenter.entity.query;


import lombok.Data;

// 后台用户列表查询条件
@Data
public class MemberQuery {

    // 手机号
    private String mobile;

    // 昵称
    private String nickname;

    // 是否禁用 1（true）已禁用，  0（false）未禁用
    private Integer isDisabled;
}
