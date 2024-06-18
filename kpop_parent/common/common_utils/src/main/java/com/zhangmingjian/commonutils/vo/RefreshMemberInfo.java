package com.zhangmingjian.commonutils.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;


// 这个类 是个用于修改message里面的用户信息的  包括昵称和头像地址
@Data
public class RefreshMemberInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    // 会员id
    private String memberId;

    // 昵称
    private String nickname;

    // 用户头像
    private String avatar;
}
