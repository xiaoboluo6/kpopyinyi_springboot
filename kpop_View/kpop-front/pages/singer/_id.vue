<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- 歌手介绍 开始 -->
    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">歌手介绍</span>
        </h2>
      </header>
      <div class="t-infor-wrap">
        <!-- 歌手基本信息 -->
        <section class="fl t-infor-box c-desc-content">
          <div class="mt20 ml20" style="">
            <section class="t-infor-pic">
              <img :src="avatarpre + singerInfo.avatar" :alt="singerInfo.name">
            </section>
            <h3 class="hLh30">
              <div style="display: flex; align-items: center;">
                <span class="fsize24 c-333">{{singerInfo.name}}</span>
                <span>&nbsp;&nbsp;&nbsp;</span> <!-- 插入空格 -->
                <img :src="require('@/assets/love.png')" alt="image" style="width: 20px; height: 20px;">
                <span>{{ singerInfo.popularity }}</span>
              </div>
            </h3>
            <section class="t-infor-txt">
              <p class="mt20"
              >{{singerInfo.description}}</p>
            </section>
            <div class="clear"></div>
          </div>
        </section>
        <div class="clear"></div>
      </div>
      <section class="mt30">
        <div>
          <header class="comm-title all-teacher-title c-course-content">
            <h2 class="fl tac">
              <span class="c-333">主要作品</span>
            </h2>
            <section class="c-tab-title">
              <a href="javascript: void(0)">&nbsp;</a>
            </section>
          </header>


          <!-- /无数据提示 开始-->
          <section class="no-data-wrap" v-if="songList.length === 0">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- /无数据提示 结束-->

          <!-- /无数据提示 开始-->
          <template v-if="songList.length > 0">
            <el-table
              :data="songList"
              :stripe="false"
              style="width: 100%;"
              @row-click="handleRowClick">
              <el-table-column
                type="index"
                width="'10%"
                align="center">
              </el-table-column>
              <el-table-column  prop="name" label="歌曲名称" width="'20%'">
              </el-table-column>
              <el-table-column  prop="singerName" label="歌手" width="'20%'">
              </el-table-column>
              <el-table-column label="发行时间" width="200">
                <template slot-scope="scope">
                  {{ formatDate(scope.row.releaseTime) }}
                </template>
              </el-table-column>
              <el-table-column label="热度" width="100">
                  <template slot-scope="scope">
                    <div style="display: flex; align-items: center;">
                      <img src="~/assets/img/MyIcon/fire.png" alt="image" style="width: 20px; height: 20px; margin-right: 5px;">
                      <span>{{ scope.row.popularity }}</span>
                    </div>
                  </template>
                </el-table-column>
            </el-table>
          </template>
          <!-- /有数据提示 结束-->

        </div>
      </section>
    </section>
    <!-- /讲师介绍 结束 -->
  </div>
</template>


<script>
import singerApi from "@/api/singer"

export default {
  data(){
    return{
      avatarpre: process.env.VUE_APP_AvatarLocationPre,  // 头像存储地址pre
      singerId:'',     // 路径上的歌手id
      singerInfo: {},  // 歌手信息
      songList: [],    // 歌手对应的歌曲列表
    }
  },

  mounted(){
    if(this.$route.params && this.$route.params.id){
      this.singerId = this.$route.params.id
    }
    this.getSingerDetailInfo()
  },

  methods:{
    getSingerDetailInfo(){
      singerApi.getSingerDetailInfo(this.singerId)
        .then(response =>{
          this.singerInfo = response.data.data.singerInfo
          this.songList = response.data.data.songList
        })
    },

    // 时间样式修改
    formatDate(dateTimeString) {
      const date = new Date(dateTimeString);
      const year = date.getFullYear();
      const month = (date.getMonth() + 1).toString().padStart(2, '0');
      const day = date.getDate().toString().padStart(2, '0');
      return `${year}-${month}-${day}`;
    },

    // 点击任意一行进行页面跳转
    handleRowClick(row){
      this.$router.push({path: '/song/' + row.id})
    },

  }

};
</script>





