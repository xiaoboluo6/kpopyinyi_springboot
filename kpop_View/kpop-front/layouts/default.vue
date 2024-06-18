<template>
  <div class="in-wrap">
    <!-- 公共头引入 -->
    <header id="header">
      <section class="container">
        <h1 id="logo">
          <a href="#" title="谷粒学院">
            <!-- <img src="~/assets/img/logo.png" width="100%" alt="谷粒学院"> -->
            <img src="~/assets/img/MyIcon/logo1.png" alt="image" style="width: 260px; height: 75px;">
          </a>
        </h1>
        <div class="h-r-nsl">
          <ul class="nav">
            <router-link to="/" tag="li" active-class="current" exact>
              <a>首页</a>
            </router-link>
            <router-link to="/singer" tag="li" active-class="current">
              <a>歌手</a>
            </router-link>
            <router-link to="/song" tag="li" active-class="current">
              <a>歌曲</a>
            </router-link>
            <router-link to="/message" tag="li" active-class="current">
              <a>留言板</a>
            </router-link>

            <!-- <router-link to="/qa" tag="li" active-class="current">
              <a>问答</a>
            </router-link> -->
          </ul>
          <!-- / nav -->
          <ul class="h-r-login">
            <li v-if="!loginInfo.id" id="no-login">   <!-- 一定要明白 在javascript中 空字符串''表示false -->
                <a href="/login" title="登录">
                    <em class="icon18 login-icon">&nbsp;</em>
                    <span class="vam ml5">登录</span>
                </a>
                |
                <a href="/register" title="注册">
                    <span class="vam ml5">注册</span>
                </a>
            </li>
            <li v-if="loginInfo.id" class="mr10" id="is-login-one">
              <a href="#" title="消息" id="headerMsgCountId">
                <em class="icon18 news-icon">&nbsp;</em>
              </a>
              <q class="red-point" style="display: none">&nbsp;</q>
            </li>
            <li v-if="loginInfo.id" class="h-r-user" id="is-login-two" >
              <a :href="'/memberInfo/' + loginInfo.id" title>
                <img
                  :src="memberAvatarpre + loginInfo.avatar"
                  width="30"
                  height="30"
                  class="vam picImg"
                  alt
                >
                <span class="vam disIb" id="userName">{{ loginInfo.nickname }}</span>
              </a>
              <a href="javascript:void(0)" title="退出" @click="logout()" class="ml5">退出</a>
            </li>
            <!-- /未登录显示第1 li；登录后显示第2，3 li -->
          </ul>
          <aside class="h-r-search">
            <form @submit.prevent="likeSearch">
              <label class="h-r-s-box">
                <input type="text" placeholder="输入歌名或者歌手名" v-model="searchName">
                <button type="submit" class="s-btn">
                  <em class="icon18">&nbsp;</em>
                </button>
              </label>
            </form>
          </aside>

        </div>
        <aside class="mw-nav-btn">
          <div class="mw-nav-icon"></div>
        </aside>
        <div class="clear"></div>
      </section>
    </header>
    <!-- /公共头引入 -->
      
    <nuxt/>

    <!-- 公共底引入 -->
    <footer id="footer">
      <section class="container">
        <div class>
          <h4 class="hLh10">
            <span class="fsize18 f-fM c-999">友情链接</span>
          </h4>
          <ul class="of flink-list">
            <li>
              <a href="https://www.melon.com//" title="melon榜单" target="_blank">melon榜单</a>
            </li>
          </ul>
          <div class="clear"></div>
        </div>
        <div class="b-foot">
          <section class="fl col-7">
            <section class="mr20">
              <section class="b-f-link">
                <a href="https://github.com/xiaoboluo6" title="Github" target="_blank">Github链接</a>|
                <a href="https://blog.csdn.net/qilaixiaoboluo" title="csdn" target="_blank">csdn链接</a>|
                <a href="https://space.bilibili.com/14120363" title="bilibili" target="_blank">bilibili链接</a>|
                <span>安智煐！！</span>
                <span>作者Email：1770367610@qq.com</span>
              </section>
              <section class="b-f-link mt10">
                <span>欢迎热爱kpop、韩剧的朋友们，如果有需要的歌手或歌曲，请联系我们哦！</span>
              </section>
            </section>
          </section>
          <aside class="fl col-3 tac mt15">
            <section class="gf-tx">
              <span>
                <img src="~/assets/img/wx-icon.png" alt>
              </span>
            </section>
            <section class="gf-tx">
              <span>
                <img src="~/assets/img/wb-icon.png" alt>
              </span>
            </section>
          </aside>
          <div class="clear"></div>
        </div>
      </section>
    </footer>
    <!-- /公共底引入 -->
  </div>
</template>
<script>
import "~/assets/css/reset.css";
import "~/assets/css/theme.css";
import "~/assets/css/global.css";
import "~/assets/css/web.css";
import cookie from 'js-cookie'

export default {

  data(){
    return {
      loginInfo: {
        id: '',
        age: '',
        avatar: '',
        mobile: '',
        nickname: '',
        sex: ''
      },  // 记录用户登录后查询到的 完整信息
      searchName: '' ,   // 歌手歌曲名模糊查询
      memberAvatarpre: process.env.VUE_APP_MemberAvatarpre,  // 用户头像存储地址pre
    }
  },

  created(){
    if(this.$route.query){
          console.log(this.$route.query.singerName);
      }
    // 1 检验wx登录方式
    // TODO

    // 2 检验普通登录方式
    this.showInfo()

    // 如果都不执行 就是游客模式
  },

  methods:{

    // 右上角根据歌手名称或者歌曲名称 进行模糊查询
    likeSearch(){
      //console.log(this.searchQuery.likeName);

      // 提示一下 输入长度最小为2
      // 如果输入的字符长度小于2，则提示用户
      if (this.searchName.length < 2) {
        this.$message({
            type: 'warning',
            message: "检索长度必须大于等于2"
        })
        return; // 终止函数执行
      }
      // 如果用push  并且连续进行两次搜索的话  会出现问题 改用replace
      this.$router.replace({ path: '/song', query: { searchName: this.searchName } });
      // 然后清空框里面的内容
      this.searchName = ''
    },
    
    
    // 普通登录方式 即检查cookie中是否有用户信息
    showInfo(){
      var userStr = cookie.get('kpop_ucenter')
      // cookie.get获得是字符串类型  比如"{'id':'11','age':'18',}"
      // 所以需要将字符串形式变成json对象
      if(userStr){
        this.loginInfo = JSON.parse(userStr)
      }
    },

    // 点击用户退出
    logout(){
      // 清空cookie
      cookie.set('kpop_token','',{domain: 'localhost'})
      cookie.set('kpop_ucenter','',{domain: 'localhost'})   // 清空cookie里面的用户信息
      // 回到首页面
      window.location.href="/"
    }


  },



};
</script>