package com.zhangmingjian.messageservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangmingjian.commonutils.R;
import com.zhangmingjian.commonutils.vo.RefreshMemberInfo;
import com.zhangmingjian.messageservice.entity.InterMessage;
import com.zhangmingjian.messageservice.entity.vo.MessageQuery;
import com.zhangmingjian.messageservice.service.InterMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/interaction/message")
public class InterMessageController {

    @Autowired
    private InterMessageService messageService;

    // 后台分页查询message
    @PostMapping("getAllMessage/{currentPage}/{pageSize}")
    public R getAllMessage(@PathVariable int currentPage, @PathVariable int pageSize,
                           @RequestBody MessageQuery messageQuery){
        Page<InterMessage> messagePage = new Page<>(currentPage,pageSize);

        LambdaQueryWrapper<InterMessage> lqw = new LambdaQueryWrapper<>();
        lqw.eq(InterMessage::getIsDeleted,0); // 只显示 正常状态下的留言
        // 这里messageQuery的status必须给值
        // 如果查询的是未处理message 即status==0 从用按时间升序
        // 如果查询的是已经处理的message 即status==1 从用按时间降序
        if(messageQuery.getStatus()==0){ // 未处理 时间升序
            lqw.orderByAsc(InterMessage::getGmtCreate);
        }else{    // 已经处理 时间降序
            lqw.orderByDesc(InterMessage::getGmtCreate);
        }
        lqw.eq(InterMessage::getStatus,messageQuery.getStatus());
        lqw.like(messageQuery.getNickname()!=null && messageQuery.getNickname()!="",
                InterMessage::getNickname,messageQuery.getNickname());

        // 分页和条件查询
        messageService.page(messagePage,lqw);
        // 总数和各条记录
        long total = messagePage.getTotal();
        List<InterMessage> records = messagePage.getRecords();

        return R.ok().data("records",records).data("total",total);
    }

    // 根据messageId获取message
    @GetMapping("getMessageById/{messageId}")
    public R getMessageById(@PathVariable String messageId){
        return R.ok().data("item",messageService.getById(messageId));
    }

    // 编辑用户message 更新status为1 添加reply内容
    @PostMapping("updateMessage")
    public R updateMessage(@RequestBody InterMessage message){
        message.setStatus(1);   // 设置为已经回复
        messageService.updateById(message);
        return R.ok().message("留言回复成功");
    }

    // 一键快速回复
    @GetMapping("quickReply/{messageId}")
    public R quickReply(@PathVariable String messageId){
        messageService.quickReply(messageId);
        return R.ok().message("快速回复成功");
    }

    // 根据messageId删除message
    @DeleteMapping("deleteMessageById/{messageId}")
    public R deleteMessageById(@PathVariable String messageId){
        messageService.removeById(messageId);
        return R.ok().message("删除留言成功");
    }


    // feign feign feign
    // 每条message存储了用户的名称和头像
    // 修改用户信息后  根据用户id修改message里面存储的信息
    @PostMapping("refreshMessageByMemberInfo")
    public void refreshMessageByMemberInfo(@RequestBody RefreshMemberInfo refreshMemberInfo){
        messageService.refreshMessage(refreshMemberInfo);
    }

    // feign feign feign
    // 禁用用户的同时 封禁用户的全部留言
    // 根据memberId 封禁用户的全部留言
    @GetMapping("disableMessageByMemberId/{memberId}")
    public void disableMessageByMemberId(@PathVariable String memberId){
        messageService.disableMessageByMemberId(memberId);
    }


}
