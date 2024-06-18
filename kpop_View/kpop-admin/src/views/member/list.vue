<template>
  <div>
    <el-alert
      title="禁用用户的同时会封禁该用户的留言  即使解封也不予恢复"
      type="warning"
      >
    </el-alert>

    <!-- 条件搜索 -->
    <el-form :inline="true" :model="memberQuery" class="demo-form-inline" style="margin-left: 30px;margin-top: 20px;">
        <el-form-item label="账号">
            <el-input v-model="memberQuery.mobile" placeholder="账号精准搜索"></el-input>
        </el-form-item>
        <el-form-item label="用户昵称">
            <el-input v-model="memberQuery.nickname" placeholder="昵称"></el-input>
        </el-form-item>
        <el-form-item label="状态">
            <el-select v-model="memberQuery.isDisabled" placeholder="状态" >
                <!-- value：表示选项的实际值  label：表示用户看到的内容 -->
                <el-option label="违规封禁" value="1"></el-option>
                <el-option label="正常使用" value="0"></el-option>
            </el-select>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="searchList">查询</el-button>
            <el-button type="default" @click="resetSearch">恢复默认</el-button>
        </el-form-item>
    </el-form>

    <el-table
      :data="memberList"
      style="width: 100%;margin-left: 30px">
      <el-table-column
          label="序号" width="50" align="center">
          <template slot-scope="scope">
            {{ (currentPage-1)*pageSize+scope.$index+1 }}
          </template>       
        </el-table-column>
      <el-table-column prop="nickname" label="用户名称" align="center" width="150">
      </el-table-column>
      <el-table-column prop="mobile" label="账号/电话" align="center" width="150">
      </el-table-column>
      <el-table-column label="签名" width="500" align="center">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <div slot="reference" class="name-wrapper" style="max-height: 50px; overflow: hidden;">
              {{ scope.row.sign || '暂无签名' }}
            </div>
          </el-popover>
        </template>
      </el-table-column>

      <el-table-column label="状态" width="100" align="center">
        <template slot-scope="scope">
          <div style="display: flex; align-items: center;" v-if="scope.row.isDisabled === 0">
            <img :src="require('@/assets/success.png')" alt="image" style="width: 20px; height: 20px; margin-right: 5px;">
            <span style="color: seagreen;text-decoration: underline;">正常</span>
          </div>
          <div style="display: flex; align-items: center;" v-if="scope.row.isDisabled !== 0">
            <img :src="require('@/assets/warning.png')" alt="image" style="width: 20px; height: 20px; margin-right: 5px;">
            <span style="color: orange;text-decoration: underline;">封禁中</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="100" align="center">
        <template slot-scope="scope">
          <div v-if="scope.row.isDisabled === 0">
            <el-button 
            size="mini"
            type="danger" 
            @click="block(scope.row.id)">封禁</el-button>
          </div>
          <div v-if="scope.row.isDisabled !== 0">
            <el-button 
            size="mini"
            type="danger" 
            @click="unblock(scope.row.id)">解封</el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>


    <!-- 分页 -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="[5, 10, 20]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      align="center">
    </el-pagination>
  </div>
</template>

<script>
import memberApi from '@/api/member'

export default {
  data() {
    return {
      currentPage: 1,
      pageSize: 10,
      total: 0 ,
      memberQuery:{
        isDisabled: "0"
      },
      memberList:[]
    }
  },

  created(){
    this.getMemberList()
  },


  methods:{

    // 按条件获取全部message信息
    getMemberList(){
      memberApi.getMemberList(this.currentPage,this.pageSize,this.memberQuery)
        .then(response =>{
          this.memberList = response.data.records
          this.total = response.data.total
        })
    },


    // 条件搜索
    searchList(){
      this.getMemberList()
    },
    // 恢复默认搜索方式
    resetSearch(){   
      this.memberQuery.nickname = ""
      this.memberQuery.mobile = ""
      this.memberQuery.isDisabled = "0"
      this.getMemberList()
    },


    // 禁用用户
    block(memberId){
      this.$confirm('此操作将禁用该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        memberApi.block(memberId)
          .then(rensponse=>{
            this.$message({
              type: 'success',
              message: "已禁用该用户"
            });
            this.getMemberList()
          })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        });          
      });
    },

    // 解封
    unblock(memberId){
      this.$confirm('此操作将解封该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        memberApi.unblock(memberId)
          .then(rensponse=>{
            this.$message({
              type: 'success',
              message: "已解封该用户"
            });
            this.getMemberList()
          })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        });          
      });
    },



    // 分页选择
    handleSizeChange(val) {
      this.pageSize = val
      this.getMemberList()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.getMemberList()
    },
  }
}
</script>
