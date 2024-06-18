package com.zhangmingjian.kpopcenter.entity.vo;


import lombok.Data;

import java.io.Serializable;


// 用户登录输入 账号密码 封装
@Data
public class LoginVo implements Serializable {

    // 手机号
    private String mobile;

    // 密码
    private String password;

}
