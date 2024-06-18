package com.zhangmingjian.managecenter.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zhangmingjian.managecenter.entity.ManageAdmin;
import com.zhangmingjian.managecenter.entity.vo.LoginAdminVo;
import com.zhangmingjian.managecenter.entity.vo.RegisterAdminVo;

public interface ManageAdminService extends IService<ManageAdmin> {

    // 管理员登录
    String login(LoginAdminVo loginAdmin);

    // =======================================================================================================
    // =======================================================================================================
    // =======================================================================================================
    // 管理员列表管理
    // 增加管理员  普通管理员/超级管理员
    void registerAdmin(RegisterAdminVo admin);

    // 根据管理员id修改用户权限  升级为超级管理员
    void upgrade(String adminId);

    // 根据管理员id修改用户权限  降级为普通管理员
    void downgrade(String adminId);

    // 根据管理员id禁用管理员
    void block(String adminId);

    // 根据管理员id解封管理员
    void unblock(String adminId);


}
