package com.zhangmingjian.kpopservice.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


// 推荐歌手对应表
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class KpopSingerFavourite implements Serializable {

    private static final long serialVersionUID = 1L;

    // 歌手推荐的id
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    // 歌手ID
    private String singerId;

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
