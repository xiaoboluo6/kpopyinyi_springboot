package com.zhangmingjian.managecenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhangmingjian.managecenter.entity.ManageAdmin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ManageAdminMapper extends BaseMapper<ManageAdmin> {

    // 根据管理员id修改用户权限  升级为超级管理员
    void upgrade(String adminId);


    // 根据管理员id修改用户权限  降级为普通管理员
    void downgrade(String adminId);

    // 根据管理员id禁用管理员
    void block(String adminId);

    // 根据管理员id解封管理员
    void unblock(String adminId);
}
