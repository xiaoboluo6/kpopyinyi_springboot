package com.zhangmingjian.kpopservice.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

import static com.baomidou.mybatisplus.annotation.IdType.ID_WORKER_STR;

@Data
@EqualsAndHashCode(callSuper = false)   // 表示调用equals和hash的时候 不返回父亲类的方法
@Accessors(chain = true)  // 生成的setter方法返回当前对象，从而支持链式调用。例如，object.setField1(value1).setField2(value2)
public class KpopCategory implements Serializable {

    // 通常建议在类中显式地定义 serialVersionUID。通过设定一个明确的值，你可以控制序列化版本
    private static final long serialVersionUID = 1L;

    // 主键 类别id
    // value = "id"：表示数据库表中对应的字段名是 "id"。
    // type = IdType.ID_WORKER_STR：表示采用的主键生成策略是字符串类型的雪花算法（ID_WORKER_STR）
    @TableId(value = "id",type = ID_WORKER_STR)
    private String id;

    // 类别名称
    private String name;

    // 类别排序(暂时没用 默认为0)
    private Integer sort;

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
