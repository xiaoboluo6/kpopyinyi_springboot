package com.zhangmingjian.kpopcenter.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UcenterMember implements Serializable {

    private static final long serialVersionUID = 1L;

    // 会员id
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    // 微信openid
    private String openid;

    // 手机号
    private String mobile;

    // 密码
    private String password;

    // 昵称
    private String nickname;

    // 性别 0 未设置/秘密 , 1 女，2 男
    private Integer sex;

    // 年龄
    private Integer age;

    // 用户头像
    private String avatar;

    // 用户签名
    private String sign;

    // 是否禁用 1（true）已禁用，  0（false）未禁用
    private Integer isDisabled;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
    
}
