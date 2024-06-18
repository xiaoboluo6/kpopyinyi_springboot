<template>
  <div>
    <!-- 条件搜索 -->
    <el-form :inline="true" :model="songQuery" class="demo-form-inline" style="margin-left: 30px;margin-top: 10px;">
        <el-form-item label="歌曲名称">
            <el-input v-model="songQuery.songName" placeholder="歌曲名称"></el-input>
        </el-form-item>
        <el-form-item label="歌手名称">
            <el-input v-model="songQuery.singerName" placeholder="歌手名称"></el-input>
        </el-form-item>
        <el-form-item label="状态">
            <el-select v-model="songQuery.status" placeholder="状态">
                <!-- value：表示选项的实际值  label：表示用户看到的内容 -->
                <el-option label="已发布" value="1"></el-option>
                <el-option label="未发布" value="0"></el-option>
            </el-select>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="getList">查询</el-button>
            <el-button type="default" @click="resetQuery">清空</el-button>
        </el-form-item>
    </el-form>

    <!-- 歌手列表 -->
    <el-table
      :data="songList"
      style="width: 100%">
      <el-table-column
        label="序号" width="100" align="center">
        <template slot-scope="scope">
          {{ (currentpage-1)*limit+scope.$index+1 }}
        </template>       
      </el-table-column>
      <el-table-column label="歌曲封面" width="120" align="center">
        <template slot-scope="scope">
          <img :src="songCoverLocationPre + scope.row.cover" :alt="scope.row.name"
            style="width: 100px; height: 100px;">
        </template>
      </el-table-column>
      <el-table-column
        prop="name"
        label="歌曲名称"
        width="250" align="center">
      </el-table-column>
      <el-table-column
        prop="singerName"
        label="歌手名称"
        width="250" align="center">
      </el-table-column>
      <el-table-column label="热度" width="100">
        <template slot-scope="scope">
          <div style="display: flex; align-items: center;">
            <img :src="require('@/assets/fire.png')" alt="image" style="width: 20px; height: 20px; margin-right: 5px;">
            <span>{{ scope.row.popularity }}</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column
        property="releaseTime"
        label="歌曲发行日期"
        width="120"
        align="center">
        <template slot-scope="scope">
          {{ scope.row.releaseTime.slice(0, 10) }}
        </template>
      </el-table-column>
      <el-table-column label="音译状态" width="100" align="center">
        <template slot-scope="scope">
          <div style="display: flex; align-items: center;" v-if="scope.row.status === 1">
            <img :src="require('@/assets/success.png')" alt="image" style="width: 20px; height: 20px; margin-right: 5px;">
            <span style="color: seagreen;text-decoration: underline;">已发布</span>
          </div>
          <div style="display: flex; align-items: center;" v-if="scope.row.status !== 1">
            <img :src="require('@/assets/warning.png')" alt="image" style="width: 20px; height: 20px; margin-right: 5px;">
            <span style="color: orange;text-decoration: underline;">未发布</span>
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
import songApi from '@/api/kpop/song'

export default {
  data(){
    return {
      avatarLocationPre: process.env.VUE_APP_AvatarLocationPre,
      songCoverLocationPre: process.env.VUE_APP_SongCoverLocationPre,

      songQuery: {},  // 歌曲查询条件
      songList:[],  // 分页条件查询 得到的结果     这里对应的是后端SongListVo这个类
      total: 0,   // 条件查询下的总数
      limit: 10, // 每页数量
      currentpage: 1,  // 当前页数
    }
  },

  created(){
    this.getSongList()
  },

  methods:{
    getSongList(){
      songApi.getSongList(this.currentpage,this.limit,this.songQuery)
        .then(response=>{
          this.songList = response.data.records
          this.total = response.data.total
          // console.log(this.songList);
          // console.log(this.total);
        })
    },


    // 编辑删除
    handleEdit(id) {
      //console.log(id);
      this.$router.push({path:'/song/baseInfo/'+id})   //带着songId进行跳转
    },
    handleDelete(id) {
      this.$confirm('此操作将永久删除该类型, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          songApi.deleteSong(id)
            .then(response=>{
              this.$message({
                type: 'success',
                message: response.message
              });
              this.getSongList()
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
      this.getSongList()
    },
    handleCurrentChange(val) {
      this.currentpage = val
      this.getSongList()
    },


    // 查询条件
    getList(){
      this.getSongList()
    },
    resetQuery(){
      this.songQuery = {}
      this.getSongList()
    }

    
  },

  
}
</script>