<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /课程详情 开始 -->
    <section class="container">
      <section class="path-wrap txtOf hLh30">
        <a href="/" title class="c-999 fsize14">首页</a>
        \
        <span class="c-999 fsize14">{{songTransInfo.singerName}}</span>
        \
        <span class="c-333 fsize14">{{ songTransInfo.songName }}</span>
      </section>

      <div class="songInfo">
          <div class="left">
              <img class="img" :src="songcoverpre + songTransInfo.cover">
              <h2>歌名：{{ songTransInfo.songName }}</h2>
          </div>
          <div class="right">
              <span>浏览量&nbsp;&nbsp;&nbsp;</span>
              <img :src="require('@/assets/love.png')" alt="image" style="width: 20px; height: 20px;">
              <span>{{ songTransInfo.popularity }}</span>
          </div>
      </div>


      <!-- /课程封面介绍 -->
      <div class="mt10 c-infor-box">
        <article class="fl col-7">
          <section class="mr30">
            <div class="i-box">
              <div>
                <section id="c-i-tabTitle" class="c-infor-tabTitle c-tab-title">
                  <a name="c-i" class="current" title="课程详情">歌曲详情</a>
                </section>
              </div>
              <article class="ml10 mr10 pt10">
                <div>
                  <h6 class="c-i-content c-infor-title">
                    <span>音译文本</span>
                    <button @click="seccendCopy()" class="ml20">一键复制</button>
                  </h6>
                  <div class="course-txt-body-wrap">
                    <section class="transliteration-body">
                      <div v-html="songTransInfo.transliteration"></div>
                    </section>
                  </div>
                </div>

                <!-- /课程介绍 -->
                <div class="mt50">
                  <h6 class="c-g-content c-infor-title">
                    <span>待添加</span>
                  </h6>
                  <section class="mt20">
                  </section>
                </div>
                <!-- /课程大纲 -->
              </article>
            </div>
          </section>
        </article>
        <aside class="fl col-3">
          <div class="i-box">
            <div>
              <section class="c-infor-tabTitle c-tab-title">
                <a title href="javascript:void(0)">歌手</a>
              </section>
              <section class="stud-act-list">
                <ul style="height: auto;">
                  <li v-for="singer in songTransInfo.singerList" :key="singer.id">
                    <div class="u-face">
                      <a href="#">
                        <img :src="avatarpre + singer.avatar" width="50" height="50" alt>
                      </a>
                    </div>
                    <section class="hLh30 txtOf">
                      <a class="c-333 fsize16 fl" href="#">{{singer.name}}</a>
                    </section>
                  </li>
                </ul>
              </section>
            </div>

          </div>
        </aside>
        <div class="clear"></div>
      </div>
    </section>
    <!-- /课程详情 结束 -->
  </div>
</template>

<script>
import songApi from "@/api/song"

export default {

  data(){
    return {
      avatarpre: process.env.VUE_APP_AvatarLocationPre,
      songcoverpre: process.env.VUE_APP_SongCoverLocationPre,
      songTransInfo:{},  // 里面包含了歌曲的基本信息 歌曲音译文件 和 歌手列表

    }
  },

  mounted(){
    this.getSongTransInfo()
  },


  methods:{

    // 辅助函数，将带有HTML标记的文本转换为纯文本
    convertToPlainText(htmlContent) {
      // 去除 <span> 标签和内部的样式
      let plainTextContent = htmlContent.replace(/<span[^>]*>(.*?)<\/span>/g, '$1');
      // 使用正则表达式替换 <br> 和 <br /> 标签为换行符
      plainTextContent = plainTextContent.replace(/<br\s*\/?>/g, '\n');
      // 使用正则表达式替换 <p></p> 标签为空字符串
      plainTextContent = plainTextContent.replace(/<p\s*\/?>|<\/p>/g, '');
      // 使用正则表达式替换 &nbsp; 为空格
      plainTextContent = plainTextContent.replace(/&nbsp;/g, ' ');
      return plainTextContent;
    },

    // 一键复制 将html内容转换成txt类型
    seccendCopy() {
      this.$copyText(this.convertToPlainText(this.songTransInfo.transliteration)).then(
        function(e) {
          console.log("copy arguments e:", e);
          alert("复制成功!");
        },
        function(e) {
          console.log("copy arguments e:", e);
          alert("复制失败!");
        }
      );
    },

    getSongTransInfo(){
      songApi.getFrontSongTrans(this.$route.params.id)
        .then(response =>{
            this.songTransInfo = response.data.data.songFrontTransInfo
        })
        .catch(error => {
          console.error('Error fetching data:', error);
        });
    }
  }

};
</script>


<style>
.transliteration-body{
  overflow:hidden;
  color: #888;
  font-size:14px;
  line-height:30px;
  white-space:normal;
  word-break:break-all;
  margin-top:20px;
  padding:0 10px;
  text-align:justify}

.songInfo {
    background: #f5f5f5;
    padding: 10px;
    overflow: hidden;
    border: 1px dashed #DDD;
    margin-bottom: 10px;
    position: relative;
    max-width: 1000px;
    width: 100%;
    margin-left: auto;
    margin-right: auto;
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.songInfo .left {
    display: flex;
    align-items: center;
}

.songInfo .left .img {
    width: 100px;
    height: 100px;
    margin-right: 10px;
}

.songInfo .left h2 {
    font-size: 24px;
    margin-top: 0;
    margin-bottom: 0;
    margin-left: 30px;
    line-height: 1;
    font-weight: normal;
}

.songInfo .right {
    display: flex;
    align-items: center;
    font-size:16px;
    margin-right: 50px;
}

.songInfo .right img {
    width: 30px;
    height: 30px;
    margin-right: 5px;
}









</style>