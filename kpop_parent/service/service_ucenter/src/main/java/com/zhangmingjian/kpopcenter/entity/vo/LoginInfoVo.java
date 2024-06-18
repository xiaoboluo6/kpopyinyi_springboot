package com.zhangmingjian.kpopcenter.entity.vo;

import lombok.Data;

import java.io.Serializable;

// 前端查询之后 后端返回的用户信息封装类
@Data
public class LoginInfoVo implements Serializable {

    // 用户id
    private String id;

    // 昵称
    private String nickname;

    // 性别 1 女，2 男
    private Integer sex;

    // 年龄
    private Integer age;

    // 用户头像
    private String avatar;

    // 用户签名
    private String sign;
}
