<template>
  <div>
    <el-form ref="form" :model="singerInfo" label-width="80px" style="margin-top:20px;margin-left:20px">
      <el-form-item label="歌手名称">
        <el-input v-model="singerInfo.name" style="width: 200px;"></el-input>
      </el-form-item>
      <el-form-item label="歌手类别">
        <el-select v-model="singerInfo.categoryId" placeholder="请选择歌手分类">
          <el-option v-for="category in categoryList" :key="category.id" 
                :label="category.name" :value="category.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="详细描述">
        <el-input type="textarea" v-model="singerInfo.description" rows="7" style="width: 600px;"></el-input>
      </el-form-item>

      <el-form-item>
        <pan-thumb :image="image" />
        <el-button type="primary" icon="el-icon-upload" style="position: absolute;bottom: 15px;margin-left: 40px;" @click="imagecropperShow=true">
          更换歌手头像
        </el-button>

        <image-cropper
          v-show="imagecropperShow"
          :key="imagecropperKey"
          :width="300"
          :height="300"
          :url="BASE_API+'/kpopservice/singer/avatarUpload'"
          lang-type="en"
          field="file"
          @close="close"
          @crop-upload-success="cropSuccess"
        />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="saveorUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'
import categoryApi from '@/api/kpop/category'
import singerApi from '@/api/kpop/singer'

export default {
  components: { ImageCropper, PanThumb },

  data() {
      return {
        imagecropperShow: false,
        imagecropperKey: 0,
        image: process.env.VUE_APP_AvatarLocationPre+'default/default.jpg',   // 这个是默认值 即初始不上传图片的情况下

        singerInfo: {
          id:'',
          name: '',
          categoryId:'',
          description: '',
          avatar:'default/default.jpg'    // 初始默认值
        },

        categoryList: [],

        BASE_API: process.env.VUE_APP_BASE_API,
        avatarLocationPre: process.env.VUE_APP_AvatarLocationPre    // 用nginx做的本地模拟服务器 这个是avatar的访问地址
      }
    },

  created(){
    // 首先根据路径参数判断这是新增还是修改
    if(this.$route.params && this.$route.params.id){
      // 修改
      // 根据id搜索singer的信息 并更新image
      this.getSingerById(this.$route.params.id)
    }else{
      // 新增 什么也不用干
    }
    // 搜索类别列表
    this.getCategoryList()
  },



  methods: {
    // 修改前 根据id搜索singer信息 并更新image
    getSingerById(id){
      singerApi.getById(id)
        .then(response =>{
          this.singerInfo = response.data.item
          this.image = this.avatarLocationPre+this.singerInfo.avatar
        })
    },


    // 查找类别表 类别名和类别id
    getCategoryList(){
      categoryApi.getAllCategory()
        .then(response=>{
          this.categoryList = response.data.items
        })
    },
    
    // 这是头像操作
    cropSuccess(resData) {
      this.imagecropperShow = false
      this.imagecropperKey = this.imagecropperKey + 1
      this.image = this.avatarLocationPre + resData.avatarAddress

      this.singerInfo.avatar = resData.avatarAddress
    },

    close() {  // 就是点击cancel
      this.imagecropperShow = false
    },

    // 点击保存 进行提交
    // 先判断是新增还是修改
    saveorUpdate(){
      if(this.singerInfo.id){
        // 修改
        this.updatesinger()
      }else{
        // 新增
        this.addsinger()
      }
    },

    // 修改
    updatesinger(){
      singerApi.updateById(this.singerInfo)
        .then(response=>{
          this.$message({
            message: response.message,
            type: 'success'
          });
          this.$router.push({ path: '/singer/list' })
        })
    },

    // 新增
    addsinger() {
      singerApi.addSinger(this.singerInfo)
        .then(response=>{
          this.$message({
            message: response.message,
            type: 'success'
          });
          this.$router.push({ path: '/singer/list' })
        })
    },

  }
}
</script>


<style scoped>
  .avatar{
    width: 200px;
    height: 200px;
    border-radius: 50%;
  }
</style>