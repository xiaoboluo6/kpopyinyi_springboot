<template>
  <div id="aCoursesList" class="bg-fa of">
    <section class="container">
      <!-- /课程封面介绍 -->
      <div class="mt20 c-infor-box">
        <article class="fl col-7">
          <section class="mr30">
            <div class="i-box">
              <div>
                <section id="c-i-tabTitle" class="c-infor-tabTitle c-tab-title">
                  <a name="c-i" class="current" title="留言板">留言板</a>
                </section>
              </div>
              <article class="ml10 mr10 pt10">

                <div class="commentHtml">
                  <h6 class="c-c-content c-infor-title" id="i-art-comment">
                      <a href="#" :style="{ color: type === 0 ? 'black' : '#aaa' }" @click="typeAllMessage()">全部留言</a>
                      <a class="separator"> | </a>
                      <a href="#" :style="{ color: type === 1 ? 'black' : '#aaa' }" @click="typeMyMessage()">我的留言</a>
                  </h6>
                  <section class="lh-bj-list pr mt20 replyhtml">
                    <ul>
                      <li class="unBr">
                        <aside class="noter-pic">
                          <img width="50" height="50" class="picImg" src="~/assets/img/avatar-boy.gif">
                          </aside>
                        <div class="of">
                          <section class="n-reply-wrap">
                            <fieldset>
                              <textarea name="" v-model="message.content" placeholder="输入您要评论的文字" id="commentContent"></textarea>
                            </fieldset>
                            <p class="of mt5 tar pl10 pr10">
                              <span class="fl "><tt class="c-red commentContentmeg" style="display: none;"></tt></span>
                              <input type="button" @click="addMessage()" value="回复" class="lh-reply-btn">
                            </p>
                          </section>
                        </div>
                      </li>
                    </ul>
                  </section>

                  <section class="">
                    <section class="question-list lh-bj-list pr">
                      <ul class="pr10">
                        <li v-for="(message,index) in messageList" v-bind:key="index">
                            <aside class="noter-pic">
                              <img width="50" height="50" class="picImg" :src="memberAvatarpre + message.avatar">
                            </aside>
                            <div class="of">
                              <span class="fl"> 
                              <font class="fsize12 c-blue"> 
                                {{message.nickname}}</font>
                              <font class="fsize12 c-999 ml5">留言：</font></span>
                            </div>
                            <div class="noter-txt mt5">
                              <p>{{message.content}}</p>
                            </div>

                            <div class="of" v-if="message.reply!=null">
                              <span class="fl"> 
                              <font class="fsize12 c-blue"> 
                                后台回复: </font>
                              <font class="noter-txt mt5">{{message.reply}}</font></span>
                            </div>

                            <div class="of mt5">
                              <span class="fr"><font class="fsize12 c-999 ml5">{{message.gmtCreate}}</font></span>
                            </div>
                        </li>
                      </ul>
                      <!-- /无留言提示 开始-->
                      <section class="no-data-wrap" v-if="messageList.length === 0">
                        <em class="icon30 no-data-ico">&nbsp;</em>
                        <span class="c-666 fsize14 ml10 vam">没有相关数据，期待用户留言哦！！   欢迎写下需要音译的歌手歌曲，或者给我们留下宝贵的建议哦！！</span><br>
                      </section>
                    </section>
                    <!-- 分页开始 -->
                    <el-pagination
                      background
                      @current-change="handleCurrentChange"
                      :current-page="currentPage"
                      layout="total, prev, pager, next, jumper"
                      :total="total"
                      style="text-align: center;margin-top: 20px;">
                    </el-pagination>
                    <!-- 分页结束 -->
                  </section>
                </div>    
              </article>
            </div>
          </section>
        </article>
        <aside class="fl col-3">
          <div class="i-box">
            <div>
              <section class="c-infor-tabTitle c-tab-title">
                <a title href="javascript:void(0)">站长力荐歌手</a>
              </section>
              <section class="stud-act-list">
                <ul style="height: auto;">
                  <li v-for="singer in singerFavouiteList" :key="singer.id">
                    <div class="u-face">
                      <a :href="'/singer/'+ singer.singerId">
                        <img :src="singerAvatarpre + singer.avatar" :alt="singer.singerName" width="50" height="50"> 
                      </a>
                    </div>
                    <section class="hLh30 txtOf">
                      <a class="c-333 fsize16 fl" :href="'/singer/'+ singer.singerId">{{singer.singerName}}</a>
                    </section>
                  </li>
                </ul>
              </section>
            </div>
          </div>
        </aside>
        <div class="clear"></div>
      </div>
    <!-- /课程详情 结束 -->

    </section>
      

  </div>
</template>

<script>
import messageApi from '@/api/message'
import singerFavouriteApi from '@/api/singerFavourite'

export default {


  data() {
    return {
      type: 0,    // 0表示全部消息 1表示用户消息  默认为0
      messageList:[], // 消息列表
      currentPage:1,
      pageSize:10,
      total:0,

      message:{},  // 用户添加的留言 包含content

      singerFavouiteList:[],  // 站长推荐歌手列表
      singerAvatarpre: process.env.VUE_APP_AvatarLocationPre,  // 歌手头像存储地址pre
      memberAvatarpre: process.env.VUE_APP_MemberAvatarpre,  // 用户头像存储地址pre
       
    }
  },

  mounted() {
    this.getMessageList()
    this.getFavouriteSingerList()
  },


  methods:{
    // 获取站长推荐歌手
    getFavouriteSingerList(){
      singerFavouriteApi.getFavouriteSingerList()
        .then(response =>{
          this.singerFavouiteList = response.data.data.singerFavouriteList
        })
    },

    // 添加留言方法
    addMessage(){
      messageApi.sendMessage(this.message).then(response => {
          if(response.data.success){   //留言成功
            // 返回留言成功的弹窗
            this.$message({
              message: '添加留言成功',
              type: 'success'
            });
            // 清空留言板
            this.message = {}
            // 刷新页面
            if(this.type === 0){   // 如果本身是全部留言 则刷新全部留言
              this.getMessageList()
            }else if(this.type === 1){  // 如果本身是我的留言 则刷新我的留言
              this.getMyMessageList()
            }
          }
          
          else{   
            // 如果留言过多  后台会返回 return R.error().message("每位用户每日最多留下两条评论哦");
            this.$message({
              message: response.data.message,
              type: 'warning'
            });

            // 如果没有登录的话  response拦截器会跳转到login页面
            // return R.error().code(28004).message("用户信息过期，请先登录");
          }
      })
    },

    // 获取全部留言
    getMessageList(){
      messageApi.getFrontMessage(this.currentPage,this.pageSize)
        .then(response =>{
          this.messageList = response.data.data.records
          this.total = response.data.data.total
        })
    },

    // 获取用户自己的留言
    getMyMessageList(){
      messageApi.getFrontMemberMessage(this.currentPage,this.pageSize)
        .then(response=>{
          this.messageList = response.data.data.records
          this.total = response.data.data.total
        })
    },

    // 点击全部留言
    typeAllMessage(){
      this.type = 0     // 控制全部留言显示样式
      this.currentPage = 1   // 刷新所在页数
      this.getMessageList()
    },

    // 点击我的留言
    typeMyMessage(){
      this.type = 1     // 控制我的留言显示样式
      this.currentPage = 1   // 刷新所在页数
      this.getMyMessageList()
    },


    // 切换分页的时候 首先要判断进行什么搜索
    handleCurrentChange(val) {
      this.currentPage = val
      if(this.type == 0){   // 查询全部留言
        this.getMessageList()
      }else{      // 查询我的留言
        this.getMyMessageList()
      }
    }
  }

};
</script>


<style scoped>

</style>


