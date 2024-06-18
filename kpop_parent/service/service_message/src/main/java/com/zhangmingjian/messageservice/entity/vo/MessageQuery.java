package com.zhangmingjian.messageservice.entity.vo;

import lombok.Data;

import java.io.Serializable;


@Data
public class MessageQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    // 用户名模糊查询
    private String nickname;

    // 消息状态查询 status 0表示未回复 1表示已经回复
    private Integer status;

}
