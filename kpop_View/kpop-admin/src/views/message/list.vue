<template>
  <div>
    <el-alert
      title="一键回复就是快速评论用户消息:后台小编已经收到啦!"
      type="success"
      >
    </el-alert>
    <el-alert
      title="发现恶意留言 直接删除  情节严重的情况下可以直接封禁用户"
      type="warning"
      >
    </el-alert>

    <!-- 编辑弹框 -->
    <el-dialog
      title="编辑消息回复"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <el-form ref="form" :model="messageItem" label-width="80px">
        <el-form-item label="回复内容">
          <el-input v-model="messageItem.reply"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="quitUpdate">取 消</el-button>
        <el-button type="primary" @click="confirmUpdate">确 定</el-button>
      </span>
    </el-dialog>


    <!-- 条件搜索 -->
    <el-form :inline="true" :model="messageQuery" class="demo-form-inline" style="margin-left: 30px;margin-top: 10px;">
        <el-form-item label="用户名称">
            <el-input v-model="messageQuery.nickname" placeholder="用户名称"></el-input>
        </el-form-item>
        <el-form-item label="状态">
            <el-select v-model="messageQuery.status" placeholder="状态">
                <!-- value：表示选项的实际值  label：表示用户看到的内容 -->
                <el-option label="已处理" value="1"></el-option>
                <el-option label="未处理" value="0"></el-option>
            </el-select>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="searchList">查询</el-button>
            <el-button type="default" @click="resetSearch">恢复默认</el-button>
        </el-form-item>
    </el-form>

    <el-table
      :data="messageList"
      style="width: 100%;margin-left: 30px">
      <el-table-column
          label="序号" width="50" align="center">
          <template slot-scope="scope">
            {{ (currentPage-1)*pageSize+scope.$index+1 }}
          </template>       
        </el-table-column>
      <el-table-column prop="nickname" label="用户名称" align="center" width="150">
      </el-table-column>
      <el-table-column label="用户留言" width="280" align="center">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>留言内容: {{ scope.row.content }}</p>
            <div slot="reference" class="name-wrapper" style="max-height: 50px; overflow: hidden;">
              {{ scope.row.content }}
            </div>
          </el-popover>
        </template>
      </el-table-column>

      <el-table-column
        label="留言日期"
        width="150"
        align="center">
        <template slot-scope="scope">
          {{ scope.row.gmtCreate.slice(0, 10) }}
        </template>
      </el-table-column>
      <el-table-column label="状态" width="100" align="center">
        <template slot-scope="scope">
          <div style="display: flex; align-items: center;" v-if="scope.row.status === 1">
            <img :src="require('@/assets/success.png')" alt="image" style="width: 20px; height: 20px; margin-right: 5px;">
            <span style="color: seagreen;text-decoration: underline;">已处理</span>
          </div>
          <div style="display: flex; align-items: center;" v-if="scope.row.status !== 1">
            <img :src="require('@/assets/warning.png')" alt="image" style="width: 20px; height: 20px; margin-right: 5px;">
            <span style="color: orange;text-decoration: underline;">未处理</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="回复内容" width="280" align="center">
        <template slot-scope="scope">
          <div class="comment-content" style="max-height: 50px; overflow: hidden;">
            {{ scope.row.reply ? scope.row.reply : '暂无回复' }}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="300" align="center">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="quickReply(scope.row.id)">快速回复</el-button>
          <el-button
            size="mini"
            @click="editReply(scope.row.id)">回复</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="deleteMessage(scope.row.id)">删除</el-button>
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
import messageApi from '@/api/message'

export default {
  data() {
    return {
      dialogVisible: false,
      currentPage: 1,
      pageSize: 10,
      messageQuery:{
        status: "0"
      },
      
      messageList: [],
      total: 0,

      messageItem:{},   // 表示选中的编辑消息

      

    }
  },

  created(){
    this.getMessageList()
  },

  methods:{
    // 按条件获取全部message信息
    getMessageList(){
      messageApi.getAllMessage(this.currentPage,this.pageSize,this.messageQuery)
        .then(response =>{
          this.messageList = response.data.records
          this.total = response.data.total
        })
    },


    // 条件搜索
    searchList(){
      this.getMessageList()
    },
    // 恢复默认搜索方式
    resetSearch(){   
      this.messageQuery.nickname = ""
      this.messageQuery.status = "0"
      this.getMessageList()
    },


    // 分页选择
    handleSizeChange(val) {
      this.pageSize = val
      this.getMessageList()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.getMessageList()
    },

    // 一键快速回复
    quickReply(messageId){
      messageApi.quickReply(messageId)
        .then(response=>{
          this.getMessageList(); // 重新搜索
          this.$message({
              type: 'success',
              message: response.message
            });
        })
    },


    // 编辑回复内容
    editReply(messageId){
      // 查询message
      messageApi.getMessageById(messageId)
        .then(response =>{
          this.messageItem = response.data.item 
          this.dialogVisible = true 
        })
    },


    // 删除留言
    deleteMessage(messageId){
      this.$confirm('此操作将永久删除该留言, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        messageApi.deleteMessageById(messageId)
          .then(response=>{
            this.$message({
              type: 'success',
              message: response.message
            });
            this.getMessageList()
          })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: response.message
        });          
      });
    },


    // 点击弹窗的 x
    handleClose(){
      this.messageItem = {} // 清空
      this.dialogVisible = false 
    },
    // 弹窗 点击确认
    confirmUpdate(){
      if (this.messageItem.reply !== undefined && this.messageItem.reply !== null && this.messageItem.reply.trim() !== "") {
        messageApi.updateMessageById(this.messageItem)
          .then(response => {
            this.messageItem = {}; // 清空
            this.dialogVisible = false;
            this.getMessageList(); // 重新搜索
          });
      } else {
        // 提示回复为空
        this.$message({
          type: 'warning',
          message: '回复不可为空'
        }) 
      }
    },
    // 弹窗 点击取消
    quitUpdate(){
      this.messageItem = {} // 清空
      this.dialogVisible = false 
    },






  }
}
</script>
