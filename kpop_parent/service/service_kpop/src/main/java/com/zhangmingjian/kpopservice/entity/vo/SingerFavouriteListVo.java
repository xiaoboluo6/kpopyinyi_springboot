package com.zhangmingjian.kpopservice.entity.vo;


import lombok.Data;

// 封装后台 推荐歌手的列表信息
@Data
public class SingerFavouriteListVo {

    // 喜爱歌手表id     保留id的目的是 可以后端根据表id操作数据库
    private String id;

    // 歌手id  保留id的目的是 可以前端可以根据歌手id进行跳转
    private String singerId;

    // 歌手名称
    private String singerName;

    // 头像地址
    private String avatar;

    // 歌手描述
    private String description;
}
