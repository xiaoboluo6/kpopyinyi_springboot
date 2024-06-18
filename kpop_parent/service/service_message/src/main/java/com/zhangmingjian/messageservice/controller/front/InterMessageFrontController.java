package com.zhangmingjian.messageservice.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.util.StringUtils;
import com.zhangmingjian.commonutils.JwtUtils;
import com.zhangmingjian.commonutils.R;
import com.zhangmingjian.messageservice.client.MemberClient;
import com.zhangmingjian.messageservice.entity.InterMessage;
import com.zhangmingjian.messageservice.service.InterMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/interaction/frontmessage")
public class InterMessageFrontController {

    @Autowired
    private InterMessageService messageService;

    @Autowired
    private MemberClient memberClient;

    // 1. 前端用户新增message
    @PostMapping("send")
    public R sendMessage(@RequestBody InterMessage message, HttpServletRequest request){
        // 首先利用headers里面的token值获取用户id，然后获得用户信息
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        // 如果没有memberId 直接提示请先登录
        if(StringUtils.isNullOrEmpty(memberId)){
            return R.error().code(28004).message("请先登录");
        }

        // 检查用户是否被封禁
        if(memberClient.getMemberIsDisabled(memberId) == true){
            return R.error().code(28004).message("用户信息过期，请先登录");
        }

        // 限制每个用户每天只能进行n次留言
        LocalDate currentDate = LocalDate.now();
        LambdaQueryWrapper<InterMessage> lqw = new LambdaQueryWrapper<>();
        lqw.eq(InterMessage::getMemberId,memberId)
                .ge(InterMessage::getGmtCreate, currentDate.atStartOfDay())  // 留言时间大于等于当天零点
                .lt(InterMessage::getGmtCreate, currentDate.plusDays(1).atStartOfDay());  // 留言时间小于明天零点
        int count = messageService.count(lqw);
        if(count>=3){
            return R.error().message("每位用户每日最多留下三条评论哦");
        }

        // 添加信息
        // 然后用feign调用memberClient里面的方法  获取该用户的信息
        Map<String, String> memberMap = memberClient.getMemberMapInfoById(memberId);

        // 再将前端用户id存入message中
        message.setMemberId(memberId);
        message.setNickname(memberMap.get("nickname"));
        message.setAvatar(memberMap.get("avatar"));
        message.setStatus(0);  // 表示 暂无回复
        messageService.save(message);
        return R.ok().message("成功发布");
    }

    // 2. 获取全部的message信息
    @GetMapping("getFrontMessage/{currentPage}/{pageSize}")
    public R getFrontMessage(@PathVariable int currentPage,@PathVariable int pageSize){
        Page<InterMessage> messagePage = new Page<>(currentPage,pageSize);

        LambdaQueryWrapper<InterMessage> lqw = new LambdaQueryWrapper<>();
        lqw.orderByDesc(InterMessage::getGmtCreate);
        // 分页和条件查询
        messageService.page(messagePage,lqw);
        // 总数和各条记录
        long total = messagePage.getTotal();
        List<InterMessage> records = messagePage.getRecords();

        return R.ok().data("records",records).data("total",total);
    }


    // 3. 查询当前登录的用户发表的message 如果用户没有登陆 则返回登录界面
    @GetMapping("getFrontMemberMessage/{currentPage}/{pageSize}")
    public R getFrontMemberMessage(@PathVariable int currentPage,@PathVariable int pageSize,
                                   HttpServletRequest request){
        // 首先利用headers里面的token值获取用户id  如果id不存在 就返回登录界面
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isNullOrEmpty(memberId)){
            return R.error().code(28004).message("请先登录");
        }

        // 如果用户已经登录 则根据memberId进行查找
        LambdaQueryWrapper<InterMessage> lqw = new LambdaQueryWrapper<>();
        lqw.eq(InterMessage::getMemberId,memberId);
        lqw.orderByDesc(InterMessage::getGmtCreate);

        Page<InterMessage> messagePage = new Page<>(currentPage,pageSize);
        messageService.page(messagePage,lqw);
        // 总数和各条记录
        long total = messagePage.getTotal();
        List<InterMessage> records = messagePage.getRecords();

        return R.ok().data("records",records).data("total",total);
    }



}
