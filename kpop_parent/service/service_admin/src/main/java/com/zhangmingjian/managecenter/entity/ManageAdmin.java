package com.zhangmingjian.managecenter.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ManageAdmin implements Serializable {

    private static final long serialVersionUID = 1L;

    // 管理员id
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    // 手机号
    private String mobile;

    // 密码
    private String password;

    // 真实姓名
    private String name;

    // 用户头像
    private String avatar;

    // 0 普通管理员  1 超级管理员
    private Integer type;

    // 是否禁用 1（true）已禁用，  0（false）未禁用
    private Integer isDisabled;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

}

