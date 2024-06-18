package com.zhangmingjian.managecenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangmingjian.commonutils.JwtUtils;
import com.zhangmingjian.commonutils.MD5;
import com.zhangmingjian.managecenter.entity.ManageAdmin;
import com.zhangmingjian.managecenter.entity.vo.LoginAdminVo;
import com.zhangmingjian.managecenter.entity.vo.RegisterAdminVo;
import com.zhangmingjian.managecenter.mapper.ManageAdminMapper;
import com.zhangmingjian.managecenter.service.ManageAdminService;
import com.zhangmingjian.servicebase.exceptionHandler.KpopException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ManageAdminServiceImpl extends ServiceImpl<ManageAdminMapper, ManageAdmin> implements ManageAdminService {

    @Override
    public String login(LoginAdminVo loginAdmin) {
        // 前端传过来的登录信息 包含手机和密码
        String mobile = loginAdmin.getMobile();
        String password = loginAdmin.getPassword();
        // 先判断是否为空 其实也可以交给前端去做
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new KpopException(20001,"登录失败");
        }

        LambdaQueryWrapper<ManageAdmin> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ManageAdmin::getMobile,mobile);
        ManageAdmin mobileAdmin = baseMapper.selectOne(lqw);
        // 判断查询对象是否为空
        if(mobileAdmin == null){
            throw new KpopException(20001,"此管理员不存在");
        }

        // 判断密码是否正确
        // 存储密码的时候回进行md5加密，在数据库中存储的密码也都是经过加密，且md5不可以解密
        // 我们可以将 输入的密码进行加密后 再和数据库中的进行比较
        if(!MD5.encrypt(password).equals(mobileAdmin.getPassword())){
            throw new KpopException(20001,"密码错误");
        }

        // 判断 is_deleted标签
        if(mobileAdmin.getIsDisabled() == 1){
            throw new KpopException(20001,"此账号已经被禁用，请联系超级管理员");
        }

        // 如果经过了上面的判断 则表示登录成功
        // 接下来制作登录用户的token值，利用jwt工具类生成
        // 这里的token是随意生成的 管理系统没有对token做验证
        String token = JwtUtils.getJwtToken(mobileAdmin.getId(),mobileAdmin.getName());

        return token;
    }

    // =======================================================================================================
    // =======================================================================================================
    // =======================================================================================================
    // 管理员列表管理
    // 1.增加管理员  普通管理员/超级管理员
    @Override
    public void registerAdmin(RegisterAdminVo registerAdmin) {
        //获取注册信息，进行校验
        String name = registerAdmin.getName();
        String mobile = registerAdmin.getMobile();
        String password = registerAdmin.getPassword();

        if(StringUtils.isEmpty(name) || StringUtils.isEmpty(mobile) ||StringUtils.isEmpty(password)){
            throw new KpopException(20001,"添加失败");
        }

        //判断手机号是否重复
        LambdaQueryWrapper<ManageAdmin> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ManageAdmin::getMobile,mobile);
        Integer count = baseMapper.selectCount(lqw);
        if(count>0){
            throw new KpopException(20001,"手机号重复，注册失败");
        }

        // 注册成功 将数据添加到数据库中
        ManageAdmin admin = new ManageAdmin();
        admin.setMobile(mobile);
        admin.setName(name);
        admin.setPassword(MD5.encrypt(password));
        admin.setIsDisabled(0);
        admin.setType(0);    // 默认新增普通管理员
        admin.setAvatar("https://ts1.cn.mm.bing.net/th/id/R-C.9554ab64073eaa3732934a516a804c8b?rik=eKTVaw7K04LUvw&riu=http%3a%2f%2fpic.616pic.com%2fys_b_img%2f00%2f08%2f10%2fcwqWnfsDXU.jpg&ehk=e5wIUw6y254vcjn3r47Sq6TbzqGTHiZjiWQKQhjVwAk%3d&risl=&pid=ImgRaw&r=0&sres=1&sresct=1");
        this.save(admin);
    }

    // 根据管理员id修改用户权限  升级为超级管理员
    @Override
    public void upgrade(String adminId) {
        baseMapper.upgrade(adminId);
    }

    // 根据管理员id修改用户权限  降级为普通管理员
    @Override
    public void downgrade(String adminId) {
        baseMapper.downgrade(adminId);
    }

    // 根据管理员id禁用管理员
    @Override
    public void block(String adminId) {
        // 首先看这个是不是超级管理员
        LambdaQueryWrapper<ManageAdmin> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ManageAdmin::getId,adminId);
        ManageAdmin admin = this.getById(adminId);
        if(admin.getType()==1){
            throw new KpopException(20001,"你敢禁用超级用户???");
        }else{
            baseMapper.block(adminId);
        }
    }

    // 根据管理员id解封管理员
    @Override
    public void unblock(String adminId) {
        baseMapper.unblock(adminId);
    }
}
