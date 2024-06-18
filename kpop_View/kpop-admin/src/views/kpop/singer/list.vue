<template>
  <div>
    <!-- 条件搜索 -->
    <el-form :inline="true" :model="singerQuery" class="demo-form-inline" style="margin-left: 30px;margin-top: 10px;">
        <el-form-item label="歌手名称">
            <el-input v-model="singerQuery.name" placeholder="歌手名称"></el-input>
        </el-form-item>
        <el-form-item label="歌手类别">
            <el-select v-model="singerQuery.categoryId" placeholder="歌手类别">
                <!-- value：表示选项的实际值  label：表示用户看到的内容 -->
                <el-option v-for="category in categoryList" :key="category.id"
                  :value="category.id" :label="category.name" ></el-option>
            </el-select>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="getList">查询</el-button>
            <el-button type="default" @click="resetQuery">清空</el-button>
        </el-form-item>
    </el-form>


    <!-- 歌手列表 -->
    <el-table
      :data="singerList"
      style="width: 100%">
      <el-table-column
        label="序号" width="100" align="center">
        <template slot-scope="scope">
          {{ (currentpage-1)*limit+scope.$index+1 }}
        </template>       
      </el-table-column>
      <el-table-column label="歌手头像" width="120" >
        <template slot-scope="scope">
          <img :src="avatarLocationPre + scope.row.avatar" :alt="scope.row.name"
            style="width: 100px; height: 100px;">
        </template>
      </el-table-column>
      <el-table-column
        prop="name"
        label="歌手名称"
        width="200" align="center">
      </el-table-column>
      <el-table-column label="类别" width="100" align="center">
        <template slot-scope="scope">
          {{ getCategoryNameById(scope.row.categoryId) }}
        </template>
      </el-table-column>
      <el-table-column
        label="歌手简介"
        width="500" align="center">
        <template slot-scope="scope">
          {{ truncatedDescription(scope.row.description) }}
        </template>
      </el-table-column>
      <el-table-column label="热度" width="100">
        <template slot-scope="scope">
          <div style="display: flex; align-items: center;">
            <img :src="require('@/assets/fire.png')" alt="image" style="width: 20px; height: 20px; margin-right: 5px;">
            <span>{{ scope.row.popularity }}</span>
          </div>
        </template>
      </el-table-column>
      

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleEdit(scope.row.id)">编辑</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentpage"
      :page-sizes="[5, 10, 20]"
      :page-size="limit"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      align="center">
    </el-pagination>
  </div>
</template>


<script>
import singerApi from '@/api/kpop/singer'
import categoryApi from '@/api/kpop/category'

export default {
  data() {
    return {
      avatarLocationPre: process.env.VUE_APP_AvatarLocationPre,
      categoryList: [],  // 存放类别列表
      singerQuery: {}, // 查询条件

      singerList: [],  // 分页条件查询结果
      total: 0, // 总数
      limit: 10, // 每页数量
      currentpage: 1,  // 当前页数

    }
  },


  created(){
    this.init()
  },

  methods: {
    // descripiton约束 显示前80个字
    truncatedDescription(description) {
      // console.log(description)
      // 使用 substr 方法截取前80个字符
      return description.length > 50 ? description.substr(0, 80) + '...' : description;
    },

    init(){
      // 查询所有的类别
      categoryApi.getAllCategory()
        .then(response =>{
          this.categoryList = response.data.items
        })
      this.pageQuery()
    },

    // 类别id和类别名称的转化
    getCategoryNameById(categoryId){
      const category = this.categoryList.find(item => item.id===categoryId)
      return category.name
    },

    // 分页条件查询
    pageQuery(){
      singerApi.pageQuerySinger(this.currentpage,this.limit,this.singerQuery)
        .then(response=>{
          this.singerList = response.data.records
          this.total = response.data.total
        })
    },

    // 编辑删除
    handleEdit(id) {
      this.$router.push({path:'/singer/edit/'+id})   //带着singerId进行跳转
    },
    handleDelete(id) {
      this.$confirm('此操作将永久删除该类型, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          singerApi.delete(id)
            .then(response=>{
              if(response.success){ // 成功删除
                this.$message({
                  type: 'success',
                  message: response.message
                });
                this.pageQuery()
              }else{   // 因为还有改歌手的歌曲 所以无法删除
                this.$message({
                  type: 'warning',
                  message: response.message
                });
                this.pageQuery()
              }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
    },

    // 分页选择
    handleSizeChange(val) {
      this.limit = val
      this.pageQuery()
    },
    handleCurrentChange(val) {
      this.currentpage = val
      this.pageQuery()
    },

    // 查询条件
    getList(){
      this.pageQuery()
    },
    resetQuery(){
      this.singerQuery= {}
      this.pageQuery()
    }


  }
}

  </script>