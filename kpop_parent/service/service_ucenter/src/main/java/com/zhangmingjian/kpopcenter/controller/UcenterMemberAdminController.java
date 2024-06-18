package com.zhangmingjian.kpopcenter.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangmingjian.commonutils.R;
import com.zhangmingjian.kpopcenter.client.MessageClient;
import com.zhangmingjian.kpopcenter.entity.UcenterMember;
import com.zhangmingjian.kpopcenter.entity.query.MemberQuery;
import com.zhangmingjian.kpopcenter.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/usercenter/memberAdmin")
public class UcenterMemberAdminController {

    @Autowired
    private UcenterMemberService memberService;

    @Autowired
    private MessageClient messageClient;

    // 1. 后台分页条件查询
    @PostMapping("getList/{currentPage}/{pageSize}")
    public R getList(@PathVariable int currentPage, @PathVariable int pageSize,
                     @RequestBody MemberQuery memberQuery){
        Page<UcenterMember> memberPage = new Page<>(currentPage,pageSize);

        LambdaQueryWrapper<UcenterMember> lqw = new LambdaQueryWrapper<>();
        lqw.eq(memberQuery.getMobile()!=null && memberQuery.getMobile().trim() != "",
                UcenterMember::getMobile,memberQuery.getMobile());
        lqw.like(memberQuery.getNickname()!=null && memberQuery.getNickname().trim() != "",
                UcenterMember::getNickname,memberQuery.getNickname());
        if(memberQuery.getIsDisabled()!=null){
            lqw.eq(UcenterMember::getIsDisabled,memberQuery.getIsDisabled());
        }else{  // 默认搜索没有被禁用的用户
            lqw.eq(UcenterMember::getIsDisabled,0);
        }

        memberService.page(memberPage,lqw);
        return R.ok().data("total",memberPage.getTotal()).data("records",memberPage.getRecords());
    }

    // 2. 根据id禁用用户
    @GetMapping("block/{memberId}")
    public R block(@PathVariable String memberId){
        // 禁用用户
        memberService.block(memberId);
        // 封禁用户的留言信息
        messageClient.disableMessageByMemberId(memberId);
        return R.ok().message("已禁用用户并封禁其留言");
    }

    // 3. 根据id解封用户
    @GetMapping("unblock/{memberId}")
    public R unblock(@PathVariable String memberId){
        // 解封用户
        memberService.unblock(memberId);
        // 之前的信息保持封禁状态
        return R.ok().message("已解封用户");
    }

}
