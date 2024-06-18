<template>
    <div class="app-container">
        <el-table :data="bannerList" style="width: 100%;margin-top:20px;">
            <el-table-column label="轮播图" align="center">
                <template slot-scope="scope">    
                    <img :src="bannerLocationPre + scope.row.imageUrl" :alt="scope.row.title" style="height: 75px; width: 175px;" />            
                </template>  
            </el-table-column>
            <el-table-column prop="title" label="标题" align="center"></el-table-column>
            <el-table-column label="跳转地址" align="center">
                <template slot-scope="scope">    
                    {{scope.row.songName}} - {{scope.row.singerName}}           
                </template>  
            </el-table-column>
            <el-table-column label="展示状态" align="center">
                <template slot-scope="scope">    
                    <el-button type="success" v-if="scope.row.isShow == 1" @click="turnOff(scope.row.id)">展示中</el-button>
                    <el-button type="info" v-if="scope.row.isShow == 0" @click="turnOn(scope.row.id)">未展示</el-button>       
                </template>  
            </el-table-column>
            <el-table-column label="操作" align="center">
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

    </div>
</template>

<script>
import bannerApi from '@/api/kpop/banner'

export default {
    data(){
        return{

            bannerList: [],   // 用于banner列表展示
            bannerLocationPre: process.env.VUE_APP_BannerLocationPre,  // banner访问地址前缀
            
        }
    },

    created(){
        this.init()
    },

    methods:{

        // banner列表初始化
        init(){
            bannerApi.getAllBanner()
                .then(response=>{
                    this.bannerList = response.data.items
                })
        },

        // 删除按钮
        handleDelete(id){
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                bannerApi.deleteBanner(id)
                    .then(response=>{
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
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

        // 编辑按钮
        handleEdit(id){     
            // 跳转页面
            this.$router.push({ path: '/banner/save/' + id})
        },


        // 切换成 不展示
        // 根据id  将banner的isShow变成0
        turnOff(bannerId){
            this.$confirm('此操作将切换前端轮播图展示状态, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                bannerApi.turnOffById(bannerId)
                    .then(response =>{
                        this.$message({
                            message: "展示状态修改完成",
                            type: 'success'
                        });
                        this.init() // 初始化
                    })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消切换'
                });          
            });
        },

        // 切换成 展示
        // 根据id  将banner的isShow变成1
        turnOn(bannerId){
            this.$confirm('此操作将切换前端轮播图展示状态, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                bannerApi.turnOnById(bannerId)
                    .then(response =>{
                        this.$message({
                            message: "展示状态修改完成",
                            type: 'success'
                        });
                        this.init() // 初始化
                    })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消切换'
                });          
            });
        },
        
        

    


    }



}
</script>