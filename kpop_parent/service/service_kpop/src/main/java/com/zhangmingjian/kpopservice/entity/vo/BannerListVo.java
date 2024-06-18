package com.zhangmingjian.kpopservice.entity.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class BannerListVo implements Serializable {

    private static final long serialVersionUID = 1L;

    // bannerId  实现跳转
    private String id;

    // 标题
    private String title;

    // 图片地址
    private String imageUrl;

    // 歌手名称
    private String singerName;

    // 歌曲名称
    private String songName;


    // 是否展示 0不展示 1展示
    private Integer isShow;

    // 排序
    private Integer sort;

}
