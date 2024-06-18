package com.zhangmingjian.kpopservice.entity.vo;


import lombok.Data;

import java.io.Serializable;


// 新增banner的时候 选择对应的歌曲     查询过程中的查询类
@Data
public class BannerSongQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    // 根据歌手名称 模糊查询
    private String singerName;

    // 根据歌曲名称 模糊查询
    private String songName;
}
