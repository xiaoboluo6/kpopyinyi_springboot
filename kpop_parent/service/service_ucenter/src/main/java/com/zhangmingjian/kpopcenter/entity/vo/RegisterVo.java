package com.zhangmingjian.kpopcenter.entity.vo;

import lombok.Data;

import java.io.Serializable;


// 用户注册 输入信息封装
@Data
public class RegisterVo implements Serializable {

    // 昵称
    private String nickname;

    // 手机号
    private String mobile;

    // 密码
    private String password;

    // 验证码
    private String code;
}
