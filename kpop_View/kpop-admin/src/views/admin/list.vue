<template>
  <div>
    <el-button type="primary" @click="dialogVisible = true" style="margin-left: 30px;margin-top: 10px">新增管理员</el-button>

    <el-dialog
      title="新增管理员"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">

      <el-form ref="registerAdminInfo" :model="registerAdminInfo" label-width="100px">
        <el-form-item label="名称">
          <el-input v-model="registerAdminInfo.name"></el-input>
        </el-form-item>
        <el-form-item label="电话/账户" prop="mobile" :rules="mobileRules">
          <el-input v-model="registerAdminInfo.mobile"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="registerAdminInfo.password"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="quitDialog()">取 消</el-button>
        <el-button type="primary" @click="addAdmin()">确 定</el-button>
      </span>
    </el-dialog>


    <el-table :data="adminList" style="width: 100%;">
        <el-table-column label="序号" type="index" width="100" align="center">
        </el-table-column>
        <el-table-column prop="name" label="名称" align="center"></el-table-column>
        <el-table-column prop="mobile" label="电话 / 账号" align="center"></el-table-column>
        <el-table-column label="级别" align="center">
            <template slot-scope="scope">
                {{ scope.row.type === 0 ? '普通管理员' : (scope.row.type === 1 ? '超级管理员' : '') }}
            </template>  
        </el-table-column>
        <el-table-column label="状态" align="center" width="120">
            <template slot-scope="scope">
                <div style="display: flex; align-items: center;" v-if="scope.row.isDisabled === 0">
                  <img :src="require('@/assets/success.png')" alt="image" style="width: 20px; height: 20px; margin-right: 5px;">
                  <span>正常使用</span> 
                </div>
                <div style="display: flex; align-items: center;" v-if="scope.row.isDisabled === 1">
                  <img :src="require('@/assets/warning.png')" alt="image" style="width: 20px; height: 20px; margin-right: 5px;">
                  <span>封禁</span>
                </div>
            </template>  
        </el-table-column>        
        <el-table-column label="操作" align="center">
            <template slot-scope="scope">
                <el-button
                  size="mini"
                  v-if="scope.row.isDisabled===0"
                  @click="block(scope.row.id)">封禁</el-button>
                
                <el-button
                  size="mini"
                  v-if="scope.row.isDisabled===1"
                  @click="unblock(scope.row.id)">解封</el-button>

            </template>
        </el-table-column>
    </el-table>
  </div>
</template>


<script>
import adminApi from '@/api/adminManage'

export default {
  data(){
    return{
      adminList: [],
      dialogVisible: false,
      registerAdminInfo:{},

      mobileRules: [
        { required: true, message: '请输入手机号码', trigger: 'blur' },
        { validator: this.checkMobile, trigger: 'blur' }
      ]
    }
  },

  created(){
      this.init()
  },

  methods:{
    // admin列表初始化
    init(){
      adminApi.getAllAdmin()
        .then(response=>{
          this.adminList = response.data.adminList
        })
    },

    // 封禁账号
    block(adminId){
      this.$confirm('此操作将禁用该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        adminApi.block(adminId)
          .then(rensponse=>{
            this.$message({
              type: 'success',
              message: "已禁用该用户"
            });
            this.init()
          })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        });          
      });
    },
    // 解封账号
    unblock(adminId){
      this.$confirm('此操作将解封该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        adminApi.unblock(adminId)
          .then(rensponse=>{
            this.$message({
              type: 'success',
              message: "已解封该用户"
            });
            this.init()
          })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        });          
      });
    },

    // 关闭对话框
    handleClose(){
      this.dialogVisible = false
      this.registerAdminInfo = {}
    },
    // 关闭对话框
    quitDialog(){
      this.dialogVisible = false
      this.registerAdminInfo = {}
    },

    addAdmin(){
      adminApi.add(this.registerAdminInfo)
        .then(response=>{
          this.dialogVisible = false
          this.init()
        })
        .catch(error => {
          console.error('添加管理员失败:', error);
          this.$message.error('添加管理员失败: ' + error.message);
        });
    },


    checkMobile(rule, value, callback) {
      if (!(/^1[34578]\d{9}$/.test(value))) {
        callback(new Error('手机号码格式不正确'))
      } else {
        callback()
      }
    }



  }
}
</script>