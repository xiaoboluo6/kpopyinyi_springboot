package com.zhangmingjian.managecenter.entity.vo;

import lombok.Data;

import java.io.Serializable;

// 新增管理员的封装类
@Data
public class RegisterAdminVo implements Serializable {

    // 姓名
    private String name;

    // 手机号
    private String mobile;

    // 密码
    private String password;

}
