<template>
  <div>
    <!-- 警告文案 -->
    <el-alert
      title="正常情况下 不允许对歌手类别进行操作"
      type="warning">
    </el-alert>

    <!-- 新增按钮以及对话框弹出 -->
    <el-button type="primary" @click="dialogVisible = true" style="margin-left: 30px;margin-top: 10px">新增歌手类别</el-button>

    <el-dialog
      title="歌手类别信息"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">

      <el-form ref="categoryInfo" :model="categoryInfo" label-width="80px">
        <el-form-item label="类别名称">
          <el-input v-model="categoryInfo.name"></el-input>
        </el-form-item>
        <el-form-item label="sort">
          <el-input v-model="categoryInfo.sort"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="quitDialog()">取 消</el-button>
        <el-button type="primary" @click="saveorupdate()">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 展示页面 -->
    <el-table :data="categoryList" style="width: 70%">
      <el-table-column label="创建日期" width="auto" align="center">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.gmtCreate }}</span>
        </template>
      </el-table-column>
      <el-table-column label="歌手类别" width="auto" align="center">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">{{ scope.row.name }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="auto" align="center">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.row.id)"
            >编辑</el-button
          >
<!--           <el-button size="mini" type="danger" @click="handleDelete(scope.row.id)"
            >删除</el-button
          > -->
        </template>
      </el-table-column>
    </el-table>
  </div>

  
</template>

<script>
import categoryApi from "@/api/kpop/category";

export default {
  data() {
    return {
      categoryList: [],   // 全部歌手类别信息
      categoryInfo: {},   // 新增或修改歌手类别
      dialogVisible: false,   // 对话框控制

    };
  },

  created() {
    this.init();
  },

  methods: {
    // 点击编辑 先查询类别信息 并跳显示对话框
    handleEdit(id) {
      categoryApi.getCategoryById(id)
        .then(response =>{
          this.categoryInfo = response.data.item
      })
      this.dialogVisible = true
    },

    // 点击删除
    handleDelete(id) {
      this.$confirm('此操作将永久删除该类型, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          categoryApi.deleteCategory(id)
            .then(response=>{
              this.$message({
                type: 'success',
                message: response.message
              });
              this.init()
          })
          
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
    },


    // 点击确定前  先判断 是新增还是删除
    saveorupdate(){
      if(this.categoryInfo.id){
        this.updatecategory()
      }else{
        this.addcategory()
      }
    },


    // 新增类别
    addcategory(){
      categoryApi.addCategory(this.categoryInfo)
        .then(response=>{
          this.dialogVisible=false
          this.categoryInfo={} // 清空
          this.$message({
            message: response.message,
            type: 'success'
          });
          this.init()
        })
    },


    // 修改类别
    updatecategory(){
      categoryApi.updateCategory(this.categoryInfo)
        .then(response=>{
          this.dialogVisible=false
          this.categoryInfo={} // 清空
          this.$message({
            message: response.message,
            type: 'success'
          });
          this.init()
        })
    },

    // 点击对话框的x
    handleClose(done) {  
      this.dialogVisible=false
      this.categoryInfo={} // 清空
    },

    // 取消对话框
    quitDialog(){
      this.dialogVisible=false
      this.categoryInfo={} // 清空
    },

    init() {    // 页面初始化
      categoryApi.getAllCategory().then((response) => {
        this.categoryList = response.data.items;
      });
    },
  },
};
</script>
