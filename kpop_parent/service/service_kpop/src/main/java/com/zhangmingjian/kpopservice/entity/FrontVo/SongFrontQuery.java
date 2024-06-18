package com.zhangmingjian.kpopservice.entity.FrontVo;

import lombok.Data;

import java.io.Serializable;


// 前端歌曲查询条件类
@Data
public class SongFrontQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    // 这里全是查询条件
    // 根据歌曲名称查询
    private String songName;

    // 根据热度排序 如果为空就不增加这个判断条件
    private String popularitySort;

    // 根据发布日期排序  如果为空就不增加这个判断条件
    private String releaseTimeSort;
}
