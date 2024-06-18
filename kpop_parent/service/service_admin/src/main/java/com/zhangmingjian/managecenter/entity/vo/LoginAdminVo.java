package com.zhangmingjian.managecenter.entity.vo;


import lombok.Data;

import java.io.Serializable;

// 登录封装类
@Data
public class LoginAdminVo implements Serializable {

    // 手机号
    private String mobile;

    // 密码
    private String password;
}
