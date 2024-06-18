package com.zhangmingjian.kpopcenter.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangmingjian.commonutils.JwtUtils;
import com.zhangmingjian.commonutils.MD5;
import com.zhangmingjian.kpopcenter.entity.UcenterMember;
import com.zhangmingjian.kpopcenter.entity.vo.LoginVo;
import com.zhangmingjian.kpopcenter.entity.vo.RegisterVo;
import com.zhangmingjian.kpopcenter.mapper.UcenterMemberMapper;
import com.zhangmingjian.kpopcenter.service.UcenterMemberService;
import com.zhangmingjian.servicebase.exceptionHandler.KpopException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public String login(LoginVo loginVo) {
        // 前端传过来的登录信息 包含手机和密码
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        // 先判断是否为空 其实也可以交给前端去做
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new KpopException(20001,"登录失败");
        }

        LambdaQueryWrapper<UcenterMember> lqw = new LambdaQueryWrapper<>();
        lqw.eq(UcenterMember::getMobile,mobile);
        UcenterMember mobileMember = baseMapper.selectOne(lqw);
        // 判断查询对象是否为空
        if(mobileMember == null){
            throw new KpopException(20001,"没有此账号");
        }

        // 判断密码是否正确
        // 存储密码的时候回进行md5加密，在数据库中存储的密码也都是经过加密，且md5不可以解密
        // 我们可以将 输入的密码进行加密后 再和数据库中的进行比较
        if(!MD5.encrypt(password).equals(mobileMember.getPassword())){
            throw new KpopException(20001,"密码错误，登录失败");
        }

        // 判断 is_deleted标签
        if(mobileMember.getIsDisabled() == 1){
            throw new KpopException(20001,"账户已经被封禁");
        }

        // 如果经过了上面的判断 则表示登录成功
        // 接下来制作登录用户的token值，利用jwt工具类生成
        String token = JwtUtils.getJwtToken(mobileMember.getId(),mobileMember.getNickname());

        return token;

    }

    @Override
    public void register(RegisterVo registerVo) {
        //获取注册信息，进行校验
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        if(StringUtils.isEmpty(nickname) || StringUtils.isEmpty(mobile) ||StringUtils.isEmpty(password) ||StringUtils.isEmpty(code)){
            throw new KpopException(20001,"注册失败");
        }

        // 判断输入的验证码和redis里面的验证码是否一样
        // String redisCode = redisTemplate.opsForValue().get(mobile);
        // 这里为了绕过验证码 默认rediscode为1379
        String redisCode = "1379";
        if(!code.equals(redisCode)){
            throw new KpopException(20001,"验证码错误，注册失败");
        }

        //判断手机号是否重复
        LambdaQueryWrapper<UcenterMember> lqw = new LambdaQueryWrapper<>();
        lqw.eq(UcenterMember::getMobile,mobile);
        Integer count = baseMapper.selectCount(lqw);
        if(count>0){
            throw new KpopException(20001,"手机号重复，注册失败");
        }

        // 注册成功 将数据添加到数据库中
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setSex(0);  // 0表示默认 或者 秘密
        member.setIsDisabled(0);
        member.setAvatar("default/default.jpg");
        this.save(member);
    }


    // 禁用用户
    @Override
    public void block(String memberId) {
        baseMapper.block(memberId);
    }

    // 解封用户
    @Override
    public void unblock(String memberId) {
        baseMapper.unblock(memberId);
    }
}
