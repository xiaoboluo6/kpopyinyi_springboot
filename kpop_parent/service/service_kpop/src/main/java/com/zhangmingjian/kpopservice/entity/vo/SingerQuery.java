package com.zhangmingjian.kpopservice.entity.vo;

import lombok.Data;

import java.io.Serializable;

// 封装KpopSinger 查询对象
@Data
public class SingerQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    // 根据name进行模糊查询
    private String name;

    // 根据歌手类别进行精准查询
    private String categoryId;
}
