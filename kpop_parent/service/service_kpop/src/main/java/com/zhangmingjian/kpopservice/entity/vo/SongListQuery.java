package com.zhangmingjian.kpopservice.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SongListQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    // 根据歌手名称 模糊查询
    private String singerName;

    // 根据歌曲名称 模糊查询
    private String songName;

    // 根据发布状态状态进行查询
    private Integer status;

}
