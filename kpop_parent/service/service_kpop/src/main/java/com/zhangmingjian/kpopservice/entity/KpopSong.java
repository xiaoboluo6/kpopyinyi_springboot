package com.zhangmingjian.kpopservice.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 歌曲表
 * </p>
 *
 * @author zhangmingjian
 * @since 2024-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class KpopSong implements Serializable {

    private static final long serialVersionUID = 1L;

    // 歌曲id
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    // 歌曲名称
    private String name;

    // 歌手名字  方便后面进行搜索  如果有多名歌手 进行直接进行拼接
    private String singerName;

    // 封面地址
    private String cover;

    // 流行度 popularity
    private int popularity;

    // 发行时间
    // 前端传递过来的日期是 object类型的"2024-01-05 09:36:45" 通过注解变成date类型
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date releaseTime;

    // 状态 0表示编辑状态 1表示已经发布
    private Integer status;

    // 逻辑删除 1（true）已删除， 0（false）未删除
    @TableLogic  // 逻辑删除的注解
    private Integer isDeleted;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    // 修改时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
