package com.zhangmingjian.kpopcenter.controller;


import com.zhangmingjian.commonutils.JwtUtils;
import com.zhangmingjian.commonutils.R;
import com.zhangmingjian.commonutils.vo.RefreshMemberInfo;
import com.zhangmingjian.kpopcenter.client.MessageClient;
import com.zhangmingjian.kpopcenter.entity.UcenterMember;
import com.zhangmingjian.kpopcenter.entity.vo.LoginInfoVo;
import com.zhangmingjian.kpopcenter.entity.vo.LoginVo;
import com.zhangmingjian.kpopcenter.entity.vo.RegisterVo;
import com.zhangmingjian.kpopcenter.service.UcenterMemberService;
import com.zhangmingjian.servicebase.exceptionHandler.KpopException;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/usercenter/member")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    @Autowired
    private MessageClient messageClient;

    // 登录接口
    @PostMapping("login")
    public R login(@RequestBody LoginVo loginVo){
        // 登陆后 返回承载用户信息的token
        try {
            String token = memberService.login(loginVo);
            return R.ok().data("token",token);
        } catch (KpopException e) {
            return R.error().message(e.getMsg());
        }
    }

    // 注册接口
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        try{
            memberService.register(registerVo);
            return R.ok();
        }catch (KpopException e){
            return R.error().message(e.getMsg());
        }

    }

    // 获取完整的用户信息 根据前端发送的request
    // 我的理解是这样的 通过请求里面的header获得token 由token解析出memberId 再查询获得完整用户信息
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = memberService.getById(memberId);
        // 复制到返回类里面去 这样避免密码等信息的错误传递
        LoginInfoVo loginInfo = new LoginInfoVo();
        BeanUtils.copyProperties(member,loginInfo);
        return R.ok().data("memberInfo",loginInfo);
    }


    // 修改用户的信息  并返回对应的token
    @PostMapping("updateMemberInfo")
    public R getMemberInfo(@RequestBody UcenterMember member,HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        member.setId(memberId);
        // 更新信息
        memberService.updateById(member);
        // 返回更新之后的token值，利用jwt工具类生成
        String token = JwtUtils.getJwtToken(member.getId(),member.getNickname());

        // 这里需要对数据库的消息列表进行修改 包括nickname和avatar
        RefreshMemberInfo refreshMember = new RefreshMemberInfo();
        refreshMember.setMemberId(memberId);
        refreshMember.setNickname(member.getNickname());
        messageClient.refreshMessageByMemberInfo(refreshMember);

        // 最后返回修改后的token
        return R.ok().data("token",token);
    }





    // feign feign feign feign feign
    // 给feign调用 根据memberId返回member信息
    @GetMapping("getMemberInfoById/{memberId}")
    public Map<String,String> getMemberMapInfoById(@PathVariable String memberId){
        UcenterMember member = memberService.getById(memberId);
        Map<String,String> membermap = new HashMap<>();
        membermap.put("memberId",member.getId());
        membermap.put("nickname",member.getNickname());
        membermap.put("avatar",member.getAvatar());
        return membermap;
    }

    // feign feign feign feign feign
    // 给feign调用 根据memberId返回member是否被封禁
    @GetMapping("getMemberIsDisabled/{memberId}")
    public Object getMemberIsDisabled(@PathVariable String memberId){
        UcenterMember member = memberService.getById(memberId);
        Integer isDisabled = member.getIsDisabled();
        if(isDisabled == 0){
            return false;   // 没有封禁
        }else{
            return true;   // 已经封禁
        }

    }



}
