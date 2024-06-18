<template>
  <div>
    <div style="max-width: 1000px; margin: 10px auto;">
      <el-steps :active="3" process-status="wait" align-center style="margin: 20px;">
        <el-step title="输入歌曲基本信息"/>
        <el-step title="编写音译内容"/>
        <el-step title="最终确认"/>
      </el-steps>
    </div>

    <div class="singerInfo" v-for="singer in songPublish.singerList" :key="singer.id">
      <img class="img" :src="avatarLocationPre + singer.avatar">
      <div class="main">
        <h2>{{ singer.name }}</h2>
        <p><span>歌手简介: {{ singer.description }}</span></p>
      </div>
    </div>

    <div class="songInfo">
      <img class="img" :src="songCoverLocationPre + songPublish.cover">
      <div class="main">
        <h2>歌名： {{ songPublish.songName }}</h2>
      </div>
    </div>

    <div style="margin-left:auto;margin-right: auto;max-width: 1000px;">
      <section class="transliteration-body">
        <div v-html="songPublish.transliteration"></div>
      </section>
      <div style="display: flex; align-items: center;margin-top:40px;margin-bottom:40px "> 
        <el-button @click="previous" style="font-size: 16px; padding: 12px 20px;">返回修改</el-button>
        <el-button type="primary" @click="publish" style="font-size: 16px; padding: 12px 20px;margin-left: auto;">最终确认</el-button>
      </div>
    </div>


    
  </div>
</template>

<script>
import songApi from '@/api/kpop/song'

export default {
  data() {
    return {
      avatarLocationPre: process.env.VUE_APP_AvatarLocationPre,
      songCoverLocationPre: process.env.VUE_APP_SongCoverLocationPre,
      songId:'',   // 路径上的 歌曲id
      songPublish:{}  // 全部信息
      
    }
  },

  created(){
    // 先获取路径上的id值
    if(this.$route.params && this.$route.params.id){
      this.songId = this.$route.params.id
    }
    this.getSongPublish()
    
  },

  methods: {
    getSongPublish(){
      songApi.getSongPublish(this.songId)
        .then(response =>{
          this.songPublish = response.data.songPublish  // 这里面有 歌曲基本信息 歌手列表 音译文件
        })
    },

    // 返回上一页的编辑
    previous(){
      this.$router.push({ path: '/song/transliteration/'+ this.songId })
    },
    
    // 最终确认
    publish(){
      songApi.finalpublish(this.songId)
        .then(response =>{
          this.$message({
            message: response.message,
            type: 'success'
          });
          this.$router.push({ path: '/song/list'})
        })
    }
  }
}
</script>




<style scoped>
.singerInfo {
    background: #f5f5f5;
    padding: 20px;
    overflow: hidden;
    border: 1px dashed #DDD;
    margin-bottom: 20px;
    position: relative;
    max-width: 1000px;  /* 设置最大宽度，根据需要调整 */
    width: 100%;  /* 使其宽度占据父容器的百分之百 */
    margin-left: auto;  /* 居中 */
    margin-right: auto; /* 居中 */
}

.singerInfo .img {
    background: #d6d6d6;
    width: 100px;
    height: 100px;
    display: block;
    float: left;
    border: none;
}

.singerInfo .main {
    margin-left: 130px;
}

.singerInfo .main h2 {
    font-size: 24px;
    margin-top: 15px;
    margin-bottom: 15px;
    line-height: 1;
    font-weight: normal;
}
.singerInfo .main p {
    font-size: 14px;
    margin-bottom: 8px;
    word-wrap: break-word;
    line-height: 22px;
    max-height: 48px;
    overflow: hidden;
}






.songInfo {
    background: #f5f5f5;
    padding: 20px;
    overflow: hidden;
    border: 1px dashed #DDD;
    margin-bottom: 40px;
    position: relative;
    max-width: 1000px;  /* 设置最大宽度，根据需要调整 */
    width: 100%;  /* 使其宽度占据父容器的百分之百 */
    margin-left: auto;  /* 居中 */
    margin-right: auto; /* 居中 */
}
.songInfo .img {
    background: #d6d6d6;
    width: 100px;
    height: 100px;
    display: block;
    float: left;
    border: none;
}
.songInfo .main {
    margin-left: 130px;
    align-items: center; /* 垂直居中 */
}

.songInfo .main h2 {
    font-size: 24px;
    margin-top: 40px;
    line-height: 1;
    font-weight: normal;
}



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



</style>