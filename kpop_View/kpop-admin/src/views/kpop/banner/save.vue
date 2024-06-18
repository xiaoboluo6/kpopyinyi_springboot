<template>
  <div>
    <el-dialog
      title="歌曲选择"
      :visible.sync="dialogVisible"
      width="900px"
      :before-close="handleClose">
      <div>
        <el-form :inline="true" :model="songQuery" class="demo-form-inline" style="margin-left: 30px;margin-top: -10px;">
            <el-form-item label="歌曲名称">
                <el-input v-model="songQuery.songName" placeholder="歌曲名称"></el-input>
            </el-form-item>
            <el-form-item label="歌手名称">
                <el-input v-model="songQuery.singerName" placeholder="歌手名称"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="getList()">模糊查询</el-button>
                <el-button type="default" @click="resetQuery()">清空条件</el-button>
            </el-form-item>
        </el-form>
      </div>

      <template>
        <el-table
          v-if="songQueryList.length>0"
          :data="songQueryList"
          style="width: 100%"
          height="500">
          <el-table-column label="歌曲封面" width="120" >
              <template slot-scope="scope">
                <img :src="songCoverLocationPre + scope.row.cover" :alt="scope.row.songName"
                  style="width: 50px; height: 50px;">
              </template>
            </el-table-column>
            <el-table-column
              prop="singerName"
              label="歌手名称"
              width="150" align="center">
            </el-table-column>
            <el-table-column
              prop="name"
              label="歌曲名称"
              width="150" align="center">
            </el-table-column>
            <el-table-column label="操作" width="200" align="center">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  @click="chooseSong(scope.row.id)">选择链接</el-button>
              </template>
            </el-table-column>
        </el-table>
      </template>
    </el-dialog>




    <el-form ref="form" :model="bannerInfo" label-width="100px" style="max-width: 1200px;" >
      <el-form-item label="轮播图名称" style="width:500px;margin-top:10px">
        <el-input v-model="bannerInfo.title"></el-input>
      </el-form-item>

      

      <el-form-item label="超链接歌曲" style="width:1000px;">
        <el-button type="text" @click="dialogVisible = true">点击选择歌曲</el-button>
        <!-- 如果已经添加  显示歌曲信息 -->
        <div v-if="relatedSong.id">
          <div class="song-info-container">
            <img :src="songCoverLocationPre + relatedSong.cover" class="song-cover" alt="Song Cover">
            <div class="song-details">
              <div class="song-name">{{ relatedSong.name }}</div>
              <div class="singer-name">{{ relatedSong.singerName }}</div>
            </div>
          </div>
        </div>
        <div v-else>
          暂未添加歌曲数据
        </div>
      </el-form-item>



      <el-form-item label="前端展示">
        <el-switch v-model="bannerInfo.isShow"
        :active-value="1" :inactive-value="0"></el-switch>
      </el-form-item>


      <el-form-item label="轮播图封面">
        <img :src="image" alt="Banner Cover" style="width: 350px; height: 150px;" />
        <el-button type="primary" icon="el-icon-upload" style="position: absolute;bottom: 50%;margin-left: 40px;" @click="imagecropperShow=true">
          选择轮播图
        </el-button>

        <image-cropper
          v-show="imagecropperShow"
          :key="imagecropperKey"
          :width="700"
          :height="300"
          :url="BASE_API+'/kpopservice/banner/bannerPictureSave'"
          lang-type="en"
          field="file"
          @close="close"
          @crop-upload-success="cropSuccess"
        />
      </el-form-item>


      
      <el-form-item style="text-align:right;width:1000px">
        <el-button type="primary" @click="saveorUpdate()" >保存轮播图</el-button>
      </el-form-item>

    </el-form>

  </div>
</template>


<script>
import ImageCropper from '@/components/ImageCropper'
import bannerApi from '@/api/kpop/banner'

export default {
  components: { ImageCropper},

  data() {
    return {
      bannerInfo:{
        isShow: 0,
        imageUrl: "default/default.jpg",
      },       // 轮播图信息

      songQuery: {},       // 歌曲查询
      songQueryList: [],   // 歌曲查询结果列表
      relatedSong: {},     // banner对应的song

      dialogVisible: false,    // 对话框控制

      BASE_API: process.env.VUE_APP_BASE_API,
      songCoverLocationPre: process.env.VUE_APP_SongCoverLocationPre,
      bannerLocationPre: process.env.VUE_APP_BannerLocationPre,

      imagecropperShow: false,
      imagecropperKey: 0,
      image: process.env.VUE_APP_BannerLocationPre + 'default/default.jpg',   // 这个是默认值 即初始不上传图片的情况下
    }
  },

  created(){
    this.init()
  },

  methods:{
    // 初始化方法 判断路径中有没有bannerId
    init(){
      if(this.$route.params && this.$route.params.id){
        // console.log("编辑");
        bannerApi.getBannerById(this.$route.params.id)
          .then(response =>{
            // bannerInfo回显
            this.bannerInfo = response.data.item
            // banner对应的song回显
            this.getRelatedSong(this.bannerInfo.songId)
          })
      }else{
        // console.log("新增");
      }
    },

    // 点击保存 先判断是新增还是修改
    saveorUpdate(){
      if(this.bannerInfo.id){
        console.log("修改banner");
        this.updateBannerInfo()  // 修改轮播图
        
      }else{
        console.log("新增banner");
        this.saveBannerInfo()   // 新增轮播图
      }
      
    },

    // 新增banner
    saveBannerInfo(){
      if(this.bannerInfo.title && this.bannerInfo.songId){  // 要求填写必要条件
        bannerApi.saveBanner(this.bannerInfo)
        .then(response =>{
          this.$message({
              message: response.message,
              type: 'success'
          });
          // 返回到banner list页面
          this.$router.push({ path: '/banner/list'})
        })
      }else{
        this.$message({
            message: "信息填写不全",
            type: 'warning'
        });
      }
    },

    // 修改banner
    updateBannerInfo(){
      if(this.bannerInfo.title && this.bannerInfo.songId){  // 要求填写必要条件
        bannerApi.updateBanner(this.bannerInfo)
        .then(response =>{
          this.$message({
              message: response.message,
              type: 'success'
          });
          // 返回到banner list页面
          this.$router.push({ path: '/banner/list'})
        })
      }else{
        this.$message({
            message: "信息填写不全",
            type: 'warning'
        });
      }
    },


    // 歌曲模糊查询
    getList(){
      bannerApi.queryBannerSong(this.songQuery)
        .then(response =>{
          this.songQueryList = response.data.bannerSongQueryList
          // 如果搜索不到 返回提示
          if (this.songQueryList.length === 0) {
            this.$message({
              message: '该条件下搜索不到相应歌曲，请检查是否存在该歌曲和歌曲发布状态',
              type: 'warning'
            });
          }
        })
    },
    // 歌曲模糊清空
    resetQuery(){
      this.songQuery = {}        // 清空
      this.songQueryList = []    // 清空
    },
    // 点击选择的歌曲
    chooseSong(songId){
      this.bannerInfo.songId = songId
      this.dialogVisible = false   // 对话框不可见
      this.songQuery = {}        // 清空
      this.songQueryList = []    // 清空
      // 搜索对应的歌曲 回显信息
      this.getRelatedSong(this.bannerInfo.songId)
    },


    // 根据songId获取对应的歌曲信息
    getRelatedSong(songId){
      bannerApi.getBannerRelatedSong(songId)
        .then(response =>{
          this.relatedSong = response.data.relatedSong
        })
    },



    // 这是头像操作
    cropSuccess(resData) {   // 这里resData其实等于response.data  所以不需要.data
      this.imagecropperShow = false
      this.imagecropperKey = this.imagecropperKey + 1
      this.image = this.bannerLocationPre + resData.bannerLocation
      this.bannerInfo.imageUrl = resData.bannerLocation
    },
    close() {  // 就是点击cancel
      this.imagecropperShow = false
    },

    handleClose(done) {
      this.songQuery = {}        // 清空
      this.songQueryList = []    // 清空
      done();

    }
  }
}
</script>


<style scoped>
.song-info-container {
  display: flex;
  align-items: center;
  width: 1000px;
}

.song-cover {
  width: 100px; /* Set your desired width */
  height: 100px; /* Set your desired height */
  margin-right: 20px; /* Adjust spacing between cover and details */
}

.song-details {
  flex-grow: 1;
}

.song-name {
  font-size: 18px; /* Set your desired font size */
  font-weight: bold;
}

.singer-name {
  font-size: 16px; /* Set your desired font size */
  color: #555; /* Set your desired color */
}
</style>

