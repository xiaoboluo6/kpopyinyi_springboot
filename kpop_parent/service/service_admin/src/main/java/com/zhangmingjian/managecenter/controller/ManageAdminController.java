package com.zhangmingjian.managecenter.controller;

import com.zhangmingjian.commonutils.JwtUtils;
import com.zhangmingjian.commonutils.R;
import com.zhangmingjian.managecenter.entity.ManageAdmin;
import com.zhangmingjian.managecenter.entity.vo.LoginAdminVo;
import com.zhangmingjian.managecenter.entity.vo.RegisterAdminVo;
import com.zhangmingjian.managecenter.service.ManageAdminService;
import com.zhangmingjian.servicebase.exceptionHandler.KpopException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/managecenter/admin")
public class ManageAdminController {

    @Autowired
    private ManageAdminService adminService;


    // TODO 如果超级管理员把普通管理员禁用了  不能做到让普通管理员直接下线


    @PostMapping("login")
    public R login(@RequestBody LoginAdminVo loginAdmin){
        // 登陆后 返回承载管理员信息的token
        try {
            String token = adminService.login(loginAdmin);
            return R.ok().data("token",token);
        } catch (KpopException e) {
            return R.error().message(e.getMsg());
        }
    }

    @GetMapping("info")
    public R info(HttpServletRequest request){
        // 从前端发来的token中 解析adminId
        String adminId = JwtUtils.getMemberIdByJwtToken(request);
        System.out.println(adminId);

        ManageAdmin admin = adminService.getById(adminId);
        String name = admin.getName();
        String avatar = admin.getAvatar();
        List<String> permission = new ArrayList<>(); //权限列表
        Integer type = admin.getType();
        if(type == 0){   // 普通管理员
            permission.add("normal_admin");
        }else if(type == 1){
            permission.add("super_admin");
        }

        return R.ok().data("name",name)
                .data("avatar",avatar)
                .data("roles", permission);
    }

    @PostMapping("logout")
    public R logout(){
        // 临时使用 不管发送了什么 直接返回
        return R.ok().message("管理员登出");
    }

    // =======================================================================================================
    // =======================================================================================================
    // =======================================================================================================
    // 管理员列表管理
    // 1.增加管理员  默认增加普通管理员
    @PostMapping("add")
    public R add(@RequestBody RegisterAdminVo registerAdmin){
        try{
            adminService.registerAdmin(registerAdmin);
            return R.ok();
        }catch (KpopException e){
            return R.error().message(e.getMsg());
        }
    }

    // 2. 根据管理员id修改用户权限  升级为超级管理员
    @GetMapping("upgrade/{adminId}")
    public R upgrade(@PathVariable String adminId){
        adminService.upgrade(adminId);
        return R.ok();
    }

    // 3. 根据管理员id修改用户权限  降级为普通管理员
    @GetMapping("downgrade/{adminId}")
    public R downgrade(@PathVariable String adminId){
        adminService.downgrade(adminId);
        return R.ok();
    }

    // 4. 根据管理员id禁用管理员
    @GetMapping("block/{adminId}")
    public R block(@PathVariable String adminId){
        try{
            adminService.block(adminId);
            return R.ok();
        }catch (KpopException e){
            return R.error().message(e.getMsg());
        }
    }

    // 5. 根据管理员id解封管理员
    @GetMapping("unblock/{adminId}")
    public R unblock(@PathVariable String adminId){
        adminService.unblock(adminId);
        return R.ok();

    }

    // 6. 后端返回所有管理员信息
    @GetMapping("getAll")
    public R getAll(){
        return R.ok().data("adminList",adminService.list(null));
    }
}
