<template>
  <div>
    <el-dialog
      title="歌曲选择"
      :visible.sync="dialogVisible"
      width="900px"
      :before-close="handleClose">

      <!-- 条件搜索 -->
      <el-form :inline="true" :model="singerQuery" class="demo-form-inline">
          <el-form-item label="歌手名称">
              <el-input v-model="singerQuery.name" placeholder="可选择输入"></el-input>
          </el-form-item>
          <el-form-item label="歌手类别">
              <el-select v-model="singerQuery.categoryId" placeholder="可选择类别">
                  <!-- value：表示选项的实际值  label：表示用户看到的内容 -->
                  <el-option v-for="category in categoryList" :key="category.id"
                    :value="category.id" :label="category.name" ></el-option>
              </el-select>
          </el-form-item>
          <el-form-item>
              <el-button type="primary" @click="getList()">查询</el-button>
              <el-button type="default" @click="resetQuery()">清空</el-button>
          </el-form-item>
      </el-form>
      <!-- 条件搜索 -->

      <!-- 搜索结果展示 -->
      <el-table 
          v-if="singerQueryList.length>0"
          :data="singerQueryList"
          highlight-current-row
          height="400"  
          :show-header="false" 
          style="width: 100%;margin-top: 10px"> <!--show-header隐藏表头  height展示滚轮页面-->
            <el-table-column label="歌手头像" width="120" >
              <template slot-scope="scope">
                <img :src="avatarLocationPre + scope.row.avatar" :alt="scope.row.name"
                  style="width: 50px; height: 50px;">
              </template>
            </el-table-column>
            <el-table-column
              prop="name"
              label="歌手名称"
              width="150" align="center">
            </el-table-column>
            <el-table-column
              label="歌手简介"
              width="300" align="center">
              <template slot-scope="scope">
                {{ truncatedDescription(scope.row.description) }}
              </template>
            </el-table-column>
            <el-table-column label="操作" width="200" align="center">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  @click="addRelatedSinger(scope.row.id)">添加歌手</el-button>
              </template>
            </el-table-column>
          </el-table>
    </el-dialog>

  
    <!-- 进度条 -->
    <div style="max-width: 1000px; margin: 10px auto;">
      <el-steps :active="1" process-status="wait" align-center style="margin: 20px;">
        <el-step title="输入歌曲基本信息"/>
        <el-step title="编写音译内容"/>
        <el-step title="最终确认"/>
      </el-steps>
    </div>

    
    <section class="no-data-wrap"  style="margin-left:auto;margin-right: auto;max-width: 1200px;">
      <el-alert
        title="请先通过辅助搜索 确认歌手 因为歌手过多,越详细越好"
        type="warning">
      </el-alert>
    </section>


    <el-form ref="form" :model="songBaseInfo.song" label-width="100px" style="margin-left:auto;margin-right: auto;max-width: 1200px;" >
      <el-form-item label="歌曲名称" style="width:500px;margin-top:10px">
        <el-input v-model="songBaseInfo.song.name"></el-input>
      </el-form-item>
      <el-form-item label="热度" style="width:200px;margin-top:10px">
        <el-input v-model="songBaseInfo.song.popularity"></el-input>
      </el-form-item>
      <!-- 歌手选择 因为歌手较多 所以提供选择方案 -->
      <el-form-item label="歌手选择器" style="width:1000px;">  
        <el-button type="text" @click="dialogVisible = true">点击添加歌手</el-button>
        <!-- 如果已经添加  显示歌曲列表信息 -->
        <div v-if="songBaseInfo.singerList">
          <div v-for="singer in songBaseInfo.singerList" :key="singer.id">
            <div class="singer-info-container">
              <img :src="avatarLocationPre + singer.avatar" class="singer-cover" alt="Singer Avatar">
              <div class="singer-details">
                <div class="singer-name">{{ singer.name }}</div>
                <el-button type="text" @click="deleteRelatedSinger(singer.id)">移除</el-button>
              </div>
            </div>
          </div>
        </div>
        <div v-else>
          暂未选择歌手信息
        </div>
      </el-form-item>


      <el-form-item label="发布时间">
          <el-date-picker v-model="songBaseInfo.song.releaseTime" type="datetime" placeholder="请选择" format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd hh:mm:ss">
          </el-date-picker>
      </el-form-item>

      <el-form-item label="歌曲封面">
        <img :src="image" alt="Song Cover" style="width: 200px; height: 200px;" />
        <el-button type="primary" icon="el-icon-upload" style="position: absolute;bottom: 50%;margin-left: 40px;" @click="imagecropperShow=true">
          Change SongCover
        </el-button>

        <image-cropper
          v-show="imagecropperShow"
          :key="imagecropperKey"
          :width="300"
          :height="300"
          :url="BASE_API+'/kpopservice/song/songCoverUpload'"
          lang-type="en"
          field="file"
          @close="close"
          @crop-upload-success="cropSuccess"
        />
      </el-form-item>

      <el-form-item style="text-align:right;width:1000px">
        <el-button type="primary" @click="saveorUpdate()" >保存歌曲基本信息</el-button>
      </el-form-item>

    </el-form>

  </div>
</template>


<script>
import singerApi from '@/api/kpop/singer'
import songApi from '@/api/kpop/song'
import categoryApi from '@/api/kpop/category'

import ImageCropper from '@/components/ImageCropper'

export default {
  components: { ImageCropper},

  data() {
    return {
      dialogVisible: false,

      BASE_API: process.env.VUE_APP_BASE_API,
      avatarLocationPre: process.env.VUE_APP_AvatarLocationPre,
      songCoverLocationPre: process.env.VUE_APP_SongCoverLocationPre,

      imagecropperShow: false,
      imagecropperKey: 0,
      image: process.env.VUE_APP_SongCoverLocationPre+'default/default.jpg',   // 这个是默认值 即初始不上传图片的情况下

      
      singerQuery: {},   // 歌手查询条件
      categoryList: [],  // 存放类别列表
      singerQueryList: [],  // 可供选择的singerQueryList列表

      songBaseInfo:{
        song:{
          cover:'default/default.jpg',  // 默认值
          popularity: 0 
        }, 
        singerList: [],   // 选中的歌手列表
      },  // 传递给后端的内容
    };
  },

  created(){
    // 如果路径有id 需要回显歌曲信息
    if(this.$route.params && this.$route.params.id){
      this.getSongInfo(this.$route.params.id)
    }
    this.getAllCategory()
  },

  methods:{
    // descripiton约束 显示前80个字
    truncatedDescription(description) {
      // console.log(description)
      // 使用 substr 方法截取前80个字符
      return description.length > 50 ? description.substr(0, 80) + '...' : description;
    },

    // 如果路径有id的话 根据songid查询歌曲基本信息
    getSongInfo(songId){
      // 歌曲信息
      songApi.getSongBaseInfoById(songId)
        .then(response=>{
          this.songBaseInfo = response.data.songBaseInfo
          console.log(this.songBaseInfo);
          this.image = this.songCoverLocationPre + this.songBaseInfo.song.cover
        })
    },

    // 点击保存歌曲基本信息的时候 先判断是add还是update
    saveorUpdate(){
      if(this.songBaseInfo.song.id){
        // 修改
        this.updateSongBaseInfo()
      }else{
        // 新增
        this.addSongBaseInfo()
      }
    },

    // 添加歌曲基本信息
    addSongBaseInfo(){
      if(this.songBaseInfo.song.name && this.songBaseInfo.song.releaseTime && this.songBaseInfo.song.cover
        && this.songBaseInfo.singerList!=[]){
          songApi.addSongBaseInfo(this.songBaseInfo)
            .then(response =>{
              const songId = response.data.songId
              this.$router.push({ path: '/song/transliteration/'+songId })
          })
      }else{
        this.$message({
          message: '请完整填写信息',
          type: 'warning'
        });
      }

      
    },

    // 修改歌曲基本信息
    updateSongBaseInfo(){
      if(this.songBaseInfo.song.name && this.songBaseInfo.song.releaseTime && this.songBaseInfo.song.cover
        && this.songBaseInfo.singerList!=[]){
          songApi.updateSongBaseInfo(this.songBaseInfo)
            .then(response =>{
              const songId = response.data.songId
              this.$router.push({ path: '/song/transliteration/'+songId })
            })
      }else{
        this.$message({
          message: '请完整填写信息',
          type: 'warning'
        });
      }
    },

    getAllCategory(){
      // 查询所有的类别
      categoryApi.getAllCategory()
        .then(response =>{
          this.categoryList = response.data.items
        })
    },

    // 这是头像操作
    cropSuccess(resData) {   // 这里resData其实等于response.data  所以不需要.data
      this.imagecropperShow = false
      this.imagecropperKey = this.imagecropperKey + 1
      this.image = this.songCoverLocationPre + resData.songCoverAddress
      this.songBaseInfo.song.cover = resData.songCoverAddress
    },
    close() {  // 就是点击cancel
      this.imagecropperShow = false
    },


    // 歌手查询和清空
    // 查询条件
    getList(){
      singerApi.querySinger(this.singerQuery)
        .then(response=>{
          this.singerQueryList = response.data.items
          // 如果搜索不到 返回提示
          if (this.singerQueryList.length === 0) {
            this.$message({
              message: '该条件下搜索不到相应歌手，请重新搜索或检查是否添加了该歌手',
              type: 'warning'
            });
          }
        })
    },
    resetQuery(){
      this.singerQuery = {}
      this.singerQueryList = []
    },


    // 点击添加选择的歌手后
    addRelatedSinger(singerId) {
      this.dialogVisible = false;   // 对话框不可见
      this.singerQuery = {};        // 清空
      this.singerQueryList = [];    // 清空

      // 检查是否已经存在相同的歌手
      const existingSinger = this.songBaseInfo.singerList.find(singer => singer.id === singerId);

      if (!existingSinger) {
        // 如果不存在相同的歌手，执行搜索对应的歌曲回显信息
        singerApi.getById(singerId)
          .then(response => {
            // 将搜索到的单个歌手信息直接 push 到 singerList 中
            this.songBaseInfo.singerList.push(response.data.item);
            // console.log(this.songBaseInfo);
          });
      } else {
        this.$message({
          message: '不要重复添加相同歌手',
          type: 'warning'
        });
      }
    },

    handleClose(done) {
      this.singerQuery = {};        // 清空
      this.singerQueryList = [];    // 清空
      done();
    },


    // 移除已经添加的歌手
    deleteRelatedSinger(singerId) {
      // 使用 findIndex 方法查找歌手在数组中的索引
      const index = this.songBaseInfo.singerList.findIndex(singer => singer.id === singerId);
      
      // 如果找到了对应的歌手，就从数组中移除
      if (index !== -1) {
        this.songBaseInfo.singerList.splice(index, 1);
        // console.log(this.songBaseInfo);
      }
    }

  }
}
</script>

<style scoped>
.singer-info-container {
  display: flex;
  align-items: center;
  width: 1000px;
  margin-bottom: 10px;
}

.singer-cover {
  width: 100px; /* Set your desired width */
  height: 100px; /* Set your desired height */
  margin-right: 20px; /* Adjust spacing between cover and details */
}

.singer-details {
  flex-grow: 1;
}

.singer-name {
  font-size: 18px; /* Set your desired font size */
  font-weight: bold;
}

</style>