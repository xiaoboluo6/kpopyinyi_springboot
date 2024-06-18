package com.zhangmingjian.kpopservice.entity.vo;

import com.zhangmingjian.kpopservice.entity.KpopSinger;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

// 封装发布页面的数据 包括songBaseInfo singerInfo transliteration
@Data
public class SongPublishVo implements Serializable {

    private static final long serialVersionUID = 1L;

    // 歌手名称  这个是拼接名字
    private String singerName;

    // 歌手列表，可能是多个歌手
    private List<KpopSinger> singerList;

    // 歌曲名称
    private String songName;

    // 歌曲流行度/热度
    private Integer popularity;

    // 歌曲封面地址
    private String cover;

    // 发行时间 这里是从数据库中获取 不需要json
    private Date releaseTime;

    // 音译内容
    private String transliteration;
}
