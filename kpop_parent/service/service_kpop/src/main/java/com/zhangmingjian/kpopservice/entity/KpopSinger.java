package com.zhangmingjian.kpopservice.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 歌手表
 * </p>
 *
 * @author zhangmingjian
 * @since 2024-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class KpopSinger implements Serializable {

    private static final long serialVersionUID = 1L;

    // 歌手ID
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    // 所属类别ID
    private String categoryId;

    // 歌手名称
    private String name;

    // 头像地址
    private String avatar;

    // 歌手描述
    private String description;

    // 热度
    private Integer popularity;

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
