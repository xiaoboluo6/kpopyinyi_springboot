<template>
  <div class="main">
    <div class="title">
      <a class="active" href="/login">登录</a>
      <span>·</span>
      <a href="/register">注册</a>
    </div>

    <div class="sign-up-container">
      <el-form ref="userForm" :model="user">

        <el-form-item class="input-prepend restyle" prop="mobile" :rules="[{ required: true, message: '请输入手机号码', trigger: 'blur' },{validator: checkMobile, trigger: 'blur'}]">
          <div>
            <el-input type="text" placeholder="手机号" v-model="user.mobile"/>
            <i class="iconfont icon-phone" />
          </div>
        </el-form-item>

        <el-form-item class="input-prepend" prop="password" :rules="[{ required: true, message: '请输入密码', trigger: 'blur' }]">
          <div>
            <el-input type="password" placeholder="密码" v-model="user.password"/>
            <i class="iconfont icon-password"/>
          </div>
        </el-form-item>

        <div class="btn">
          <input type="button" class="sign-in-button" value="登录" @click="submitLogin()">
        </div>
      </el-form>
      <!-- 更多登录方式 -->
      <div class="more-sign">
        <h6>社交帐号登录</h6>
        <ul>
          <li><a id="weixin" class="weixin" target="_blank" href="http://qy.free.idcfengye.com/api/ucenter/weixinLogin/login"><i class="iconfont icon-weixin"/></a></li>
          <li><a id="qq" class="qq" target="_blank" href="#"><i class="iconfont icon-qq"/></a></li>
        </ul>
      </div>
    </div>

  </div>
</template>

<script>
  import '~/assets/css/sign.css'
  import '~/assets/css/iconfont.css'
  import cookie from 'js-cookie'

  import loginApi from '@/api/login'

  export default {
    layout: 'sign',

    data () {
      return {
        // 封装登录的手机号和密码
        user:{
          mobile:'',
          password:''
        },

        // 封装查询到的用户的完整信息
        loginInfo:{

        }
      }
    },

    methods: {
      // 登录的方法
      submitLogin(){
        // 第一步 调用接口进行登录 返回token字符串
        loginApi.submitLogin(this.user)
          .then(response =>{
            if(response.data.success){     // 登陆成功
              // 第二步 获取用户token字符串放到cookie里面
              //        第一个参数cookie名称，第二个是参数值，第三个是参数  作用范围
              cookie.set('kpop_token',response.data.data.token,{domain: 'localhost'})
                        
              // 第四步 调用接口 接口会经过拦截器 这时候会把cookie里面的kpop_token放入request里面的header中
              // 根据token获取用户信息  
              // 这时发送的请求中 header里面包含了token
              loginApi.getLoginMemberInfo()
                .then(response =>{
                  // 获取返回的用户信息，放到cookie里面
                  this.loginInfo = response.data.data.memberInfo
                  cookie.set('kpop_ucenter',JSON.stringify(this.loginInfo),{domain: 'localhost'})
                  // 跳转页面
                  window.location.href = "/"
                })

            }else{   // 登陆失败
              this.$message.error(response.data.message);
              // 清空
              this.user = {}
            }
            
          })

      },

      checkMobile (rule, value, callback) {
        //debugger
        if (!(/^1[34578]\d{9}$/.test(value))) {
          return callback(new Error('手机号码格式不正确'))
        }
        return callback()
      }
    }
  }
</script>
<style>
   .el-form-item__error{
    z-index: 9999999;
  }
</style>