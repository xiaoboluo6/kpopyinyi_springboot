<template>
  <div id="aCoursesList" class="bg-fa of" style="margin-top:30px">
    <section class="container">
      <el-form ref="form" :model="loginInfo" label-width="80px" >
      <el-form-item label="昵称" style="width: 295px;">
        <el-input v-model="loginInfo.nickname"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-select v-model="loginInfo.sex" placeholder="请选择性别">
          <el-option label="男" value="2"></el-option>
          <el-option label="女" value="1"></el-option>
          <el-option label="秘密" value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="年龄" >
        <el-input v-model="loginInfo.age" style="width: 50px;"></el-input>
      </el-form-item>
      <el-form-item label="签名">
        <el-input type="textarea" v-model="loginInfo.sign"
        :rows="5" autosize style="width: 800px;"></el-input>
      </el-form-item>
      <!-- <el-form-item label="头像">
        <el-upload
          class="avatar-uploader"
          action="https://jsonplaceholder.typicode.com/posts/"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload">
          <img v-if="imageUrl" :src="imageUrl" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item> -->
      
      <el-form-item>
        <el-button type="primary" @click="onSubmit">保存个人信息</el-button>
      </el-form-item>
    </el-form>
    </section>
  </div>

</template>


<script>
import cookie from 'js-cookie'
import memberInfoApi from '@/api/memberInfo'
import loginApi from '@/api/login'


export default {

  data(){
    return {
      loginInfo: {
        nickname: '',
        sex: '',
        age: '',
        sign: '',
        avatar: 'default/default.jpg'
      },  // 记录用户登录后查询到的 完整信息

      imageUrl: '', // 图像回显

    }
  },

  mounted(){
    this.showInfo() // 
  },

  methods:{
    // 回显用户信息  但最好采用request请求的方式  这里处理的比较简单
    showInfo(){
      var userStr = cookie.get('kpop_ucenter')
      // cookie.get获得是字符串类型  比如"{'id':'11','age':'18',}"
      // 所以需要将字符串形式变成json对象
      if(userStr){
        this.loginInfo = JSON.parse(userStr)
        console.log(this.loginInfo);
      }
    },

    onSubmit(){
      memberInfoApi.updateMember(this.loginInfo)
        .then(response=>{
          // 具体逻辑看login.vue
          if(response.data.success){     // 修改成功
              // 将新的token到cookie
              cookie.set('kpop_token',response.data.data.token,{domain: 'localhost'})
                        
              // 调用接口 接口会经过拦截器 这时候会把cookie里面的新的kpop_token放入request里面的header中
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


    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    }

  }

};
</script>



<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>