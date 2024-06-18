package com.zhangmingjian.messageservice.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InterMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    // id
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    // 用户id
    private String memberId;

    // 用户昵称
    private String nickname;

    // 用户头像
    private String avatar;

    // 留言内容
    private String content;

    // 评论回复 可以为null
    private String reply;

    // 状态 0表示未处理 1表示已经处理
    private Integer status;

    // 状态 0表示正常显示 1表示封禁
    // 用户被封禁后 留言也会被封禁  就算解封 留言依旧保持封禁状态(永不解封)
    @TableLogic  // 逻辑删除的注解
    private Integer isDeleted;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
