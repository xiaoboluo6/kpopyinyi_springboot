<template>
  <div>
    <el-button type="primary" @click="dialogVisible = true" style="margin-left: 30px;margin-top: 10px">新增推荐歌手</el-button>

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
                  @click="addRelatedSinger(scope.row.id)">推荐歌手</el-button>
              </template>
            </el-table-column>
          </el-table>
    </el-dialog>

    <!-- 歌手列表 -->
    <el-table
      :data="singerFavouiteList"
      style="width: 100%">
      <el-table-column label="序号" type="index" width="100" align="center"></el-table-column>
      <el-table-column label="歌手头像" width="120" >
        <template slot-scope="scope">
          <img :src="avatarLocationPre + scope.row.avatar" :alt="scope.row.name"
            style="width: 80px; height: 80px;">
        </template>
      </el-table-column>
      <el-table-column
        prop="singerName"
        label="歌手名称"
        width="200" align="center">
      </el-table-column>
      <el-table-column
        label="歌手简介"
        width="500" align="center">
        <template slot-scope="scope">
          {{ truncatedDescription(scope.row.description) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.row.id)">移除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>


<script>
import singerFavouriteApi from '@/api/kpop/singerFavourite'
import singerApi from '@/api/kpop/singer'
import categoryApi from '@/api/kpop/category'

export default {
  data() {
    return {
      avatarLocationPre: 'http://localhost:9002/singerAvatar/',    // 图片存储地址
      singerFavouiteList: [],    // 推荐喜爱歌手列表

      dialogVisible: false,
      singerQuery: {},   // 歌手查询条件
      categoryList: [],  // 存放类别列表
      singerQueryList: [],  // 可供选择的singerQueryList列表


    }
  },


  created(){
    this.getFavouriteSingerList()
    this.getAllCategory()
  },

  methods: {
    truncatedDescription(description) {
      // console.log(description)
      // 使用 substr 方法截取前80个字符
      return description.length > 50 ? description.substr(0, 80) + '...' : description;
    },

    // 获取推荐歌手列表
    getFavouriteSingerList(){
      singerFavouriteApi.getFavouriteSingerList()
        .then(response =>{
          this.singerFavouiteList = response.data.singerFavouriteList
        })
    },


    // 移除推荐歌手
    handleDelete(singerFavouiteId){
      this.$confirm('此操作将移除推荐歌手, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        singerFavouriteApi.deleteFavouriteSinger(singerFavouiteId)
          .then(response=>{
            this.$message({
              type: 'success',
              message: response.message
            });
            this.getFavouriteSingerList()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });          
      });
    },


    // 后面是歌手选择器的方法

    // 获取全部歌手种类
    getAllCategory(){
      // 查询所有的类别
      categoryApi.getAllCategory()
        .then(response =>{
          this.categoryList = response.data.items
        })
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
    // 重置
    resetQuery(){
      this.singerQuery = {}
      this.singerQueryList = []
    },

    // 点击添加选择的歌手后
    addRelatedSinger(singerId) {
      this.dialogVisible = false;   // 对话框不可见
      this.singerQuery = {};        // 清空
      this.singerQueryList = [];    // 清空
      singerFavouriteApi.addFavouriteSinger(singerId)
        .then(response =>{
          this.$message({
            type: 'success',
            message: response.message
          });
          this.getFavouriteSingerList()  // 初始化
        })
    },

    // 歌手选择器点击关闭
    handleClose(done) {
      this.singerQuery = {};        // 清空
      this.singerQueryList = [];    // 清空
      done();
    },


  }
}

</script>