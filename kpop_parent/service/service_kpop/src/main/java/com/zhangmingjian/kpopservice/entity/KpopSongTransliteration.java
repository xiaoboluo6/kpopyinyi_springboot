package com.zhangmingjian.kpopservice.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * KPOP歌曲音译表
 * </p>
 *
 * @author zhangmingjian
 * @since 2024-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class KpopSongTransliteration implements Serializable {

    private static final long serialVersionUID = 1L;

    // 音译ID
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    // 歌曲ID
    private String songId;

    // 音译
    private String transliteration;

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
