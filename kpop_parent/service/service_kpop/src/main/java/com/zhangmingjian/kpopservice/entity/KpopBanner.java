package com.zhangmingjian.kpopservice.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class KpopBanner implements Serializable {

    private static final long serialVersionUID = 1L;

    // ID
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    // 标题
    private String title;

    // 图片地址
    private String imageUrl;

    // 链接歌曲id
    private String songId;

    // 是否展示 0不展示 1展示
    private Integer isShow;

    // 排序
    private Integer sort;

    // 逻辑删除 1（true）已删除， 0（false）未删除
    @TableLogic  // 逻辑删除的注解
    private Integer isDeleted;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
}
