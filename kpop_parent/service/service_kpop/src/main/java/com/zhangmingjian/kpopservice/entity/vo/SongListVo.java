package com.zhangmingjian.kpopservice.entity.vo;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


// 在查看song列表的过程中 封装song的信息   包括了把singerid变成singername等等
@Data
public class SongListVo implements Serializable {

    private static final long serialVersionUID = 1L;

    // 歌曲id     保留id的目的是 可以进行页面跳转
    private String id;

    // 所属歌手的姓名 KpopSong里面有拼接好的singerName
    private String singerName;

    // 歌曲名称
    private String songName;

    // 封面地址
    private String cover;

    // 发行时间
    // 前端传递过来的日期是 object类型的"2024-01-05 09:36:45" 通过注解变成date类型
    private Date releaseTime;

    // 状态 0表示编辑状态 1表示已经发布
    private Integer status;

}
