<template>
  <div>
    <div style="max-width: 1000px; margin: 10px auto;">
      <el-steps :active="2" process-status="wait" align-center style="margin: 20px;">
        <el-step title="输入歌曲基本信息"/>
        <el-step title="编写音译内容"/>
        <el-step title="最终确认"/>
      </el-steps>
    </div>


    <el-form :inline="true" :model="songSimpleInfo" class="demo-form-inline" style="display: flex; align-items: center; margin-top: 20px; margin-left: auto; margin-right: auto; max-width: 1200px;">
      <el-form-item :label-width="'0'">
        <img :src='songCoverLocationPre + songSimpleInfo.cover' alt="Song Cover" style="width: 80px; height: 80px" />
      </el-form-item>
      <el-form-item :label-width="'0'">
        <span style="font-size: 20px; color: #333; margin-left: 10px;">歌曲名称：{{ songSimpleInfo.name }}</span>
      </el-form-item>

      <el-form-item style="margin-left: auto;"> <!-- 使用 margin-left: auto; 将按钮容器推到右侧 -->
        <div>
          <el-button type="primary" @click="saveorupdate">保存音译</el-button>
          <el-button type="primary" @click="pre">上一步</el-button>
          <el-button type="primary" @click="next">下一步</el-button>
        </div>
      </el-form-item>
    </el-form>


    <el-form :model="transInfo" style="margin-left:auto;margin-right: auto;max-width: 1200px">
      <el-form-item :label-width="'0'">
        <tinymce :height="500" :width="1200" v-model="transInfo.transliteration" />
      </el-form-item>
    </el-form>
      

  </div>
</template>

<script>
import songApi from '@/api/kpop/song'
import transApi from '@/api/kpop/songTransliteration'

import Tinymce from '@/components/Tinymce'
import { ElDescriptions, ElDescriptionsItem } from 'element-ui';


export default {
  components: { Tinymce,ElDescriptions, ElDescriptionsItem },

  data(){
    return{
      img:'',
      songName:'',

      songSimpleInfo:{},
      songCoverLocationPre: process.env.VUE_APP_SongCoverLocationPre,
      songId:'',

      transInfo: {}

    }
  },

  created(){
    // 搜索歌曲信息 显示部分信息   这里只需要查询song表
    this.getSongSimpleInfo()
    // 查询歌曲音译
    this.getTransBySongId()

  },

  methods:{

    // 查询歌曲音译
    getTransBySongId(){
      transApi.getTransBySongId(this.songId)
        .then(response=>{
          if(response.data.count === 1){  // 没找到的话count=0
            this.transInfo = response.data.item
          }
        })
    },

    // 搜索歌曲信息 显示部分信息
    getSongSimpleInfo(){
      //if(this.$route.params && this.$route.params.id){
      //  this.songId = this.$route.params.id
      //}
      // 懒得判断了 直接执行
      this.songId = this.$route.params.id
      songApi.getSongSimpleInfoById(this.songId)
        .then(response=>{
          this.songSimpleInfo = response.data.songSimpleInfo
        })
    },

    // 点击保存的时候 要判断是新增音译文件还是修改音译文件
    saveorupdate(){
      if(this.transInfo && this.transInfo.id){
        this.updateTrans()
      }else{
        this.addTrans()
      }
    },

    // 新增音译
    addTrans(){
      this.transInfo.songId = this.songId
      if(this.transInfo.transliteration !== undefined && this.transInfo.transliteration !== null && this.transInfo.transliteration.trim() !== ""){
        transApi.addSongTrans(this.transInfo)
        .then(response=>{
          this.$message({
            message: response.message,
            type: 'success'
          });
          this.getTransBySongId()
        })
      }else{
        this.$message({
            message: "请编辑音译内容",
            type: 'warning'
        });
      }
    },

    // 修改音译
    updateTrans(){
      if(this.transInfo.transliteration !== undefined && this.transInfo.transliteration !== null && this.transInfo.transliteration.trim() !== ""){
        this.transInfo.songId = this.songId
          transApi.updateSongTrans(this.transInfo)
            .then(response=>{
              this.$message({
                message: response.message,
                type: 'success'
              });
              this.getTransBySongId()
            })
      }else{
        this.$message({
            message: "请编辑音译内容",
            type: 'warning'
        });
      }
    },

    // 返回上一页 下一页
    pre(){
      this.saveorupdate()   // 切换上下页的时候默认进行保存
      this.$router.push({ path: '/song/baseInfo/'+ this.songId })
    },
    next(){
      this.saveorupdate()   // 切换上下页的时候默认进行保存
      if(this.transInfo.transliteration !== undefined && this.transInfo.transliteration !== null && this.transInfo.transliteration.trim() !== ""){
        // 如果音译内容不为空 点击下一步 则进行跳转
        this.$router.push({ path: '/song/publish/'+ this.songId })
      }else{
        // 如果音译内容为空 点击下一步的话 没有任何反应
      }
    }
  }
}
</script>