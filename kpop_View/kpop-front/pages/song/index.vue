<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /课程列表 开始 -->
    <section class="container">
      <header class="comm-title all-teacher-title">
        <h2 class="fl tac">
          <span class="c-333">
            {{ songQuery.songName === '' ? '全部音译歌曲' : '查询内容：' + songQuery.songName }}
          </span>
        </h2>
        <section class="c-tab-title">
          <a id="reset" title="reset" href="#" @click.prevent="resetsearchName()">重置条件</a>
        </section>


      </header>


      <section class="c-sort-box">
        <section class="c-s-dl">
          <dl>
            <dt>
              <span class="c-999 fsize14">搜索结果</span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li>
                  <a title="相关歌曲" href="#" class="active">相关歌曲</a>
                </li>
                <li>
                  <a title="相关歌手" href="#" @click="relatedSinger()">相关歌手</a>
                </li>
                
              </ul>
            </dd>
          </dl>
          <div class="clear"></div>
        </section>
        <div class="js-wrap">
          <section class="fr">
            <span class="c-ccc">
              <i class="c-master f-fM">1</i>/
              <i class="c-666 f-fM">1</i>
            </span>
          </section>
          <section class="fl">
            <ol class="js-tap clearfix">
              <li :class="{'current bg-orange':songQuery.releaseTimeSort!=''}">
                <a title="最新" href="javascript:void(0);" @click="sortByReleaseTime()">最新
                  <span :class="{hide:songQuery.releaseTimeSort==''}">↓</span>
                </a>
              </li>
              <li :class="{'current bg-orange':songQuery.popularitySort!=''}">
                <a title="价格" href="javascript:void(0);" @click="sortByPopularity()">热度排序&nbsp;
                  <span :class="{hide:songQuery.idSort==''}">↓</span>
                </a>
              </li>
              <li>
                <a title="TODO" href="javascript:void(0);" @click="sortByTODO()">TODO&nbsp;
                </a>
              </li>
            </ol>
          </section>
        </div>



        <div class="mt5" >
          <!-- /无数据提示 开始-->
          <section class="no-data-wrap" v-if="songList.length === 0">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- /无数据提示 结束-->
          <article class="comm-course-list" v-if="songList.length > 0">
            <template>
              <el-table
                :data="songList"
                style="width: 100%"
                @row-click="handleRowClick">
                <el-table-column label="" type="index" width="'10%'">
                    <template slot-scope="scope">
                        <span>{{ (currentPage - 1) * pageSize + scope.$index + 1 }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="封面" width="120">
                  <template slot-scope="scope">
                    <div style="display: flex; align-items: center;">
                      <img :src="songcoverpre + scope.row.cover" 
                          class="img-responsive" :alt="scope.row.songName"
                          style="width: 75px; height: 75px;"
                      >
                    </div>
                  </template>
                </el-table-column>

                <!-- <el-table-column  width="'10%'" align="center">
                    <div style="display: flex; align-items: center; justify-content: center;">
                      <img src="~/assets/img/MyIcon/play1.png" 
                          style="width: 40px; height: 30px; margin-left: 10px;"
                      >
                    </div>
                </el-table-column> -->

                <el-table-column
                  prop="name"
                  label="歌曲名"
                  width="'35%'">
                </el-table-column>
                <el-table-column
                  prop="singerName"
                  label="歌手"
                  width="'35%'" >
                </el-table-column>
                <el-table-column
                  label="发行日期"
                  width="'10%'" >
                  <template slot-scope="scope" >
                    <i class="el-icon-time"></i>
                    <span style="margin-left: 10px">{{ formatDate(scope.row.releaseTime) }}</span>
                  </template>
                </el-table-column>
                <el-table-column label="热度" width="100">
                  <template slot-scope="scope">
                    <div style="display: flex; align-items: center;">
                      <img src="~/assets/img/MyIcon/fire.png" alt="image" style="width: 20px; height: 20px; margin-right: 5px;">
                      <span>{{ scope.row.popularity }}</span>
                    </div>
                  </template>
                </el-table-column>
              </el-table>
            </template>
            
            <div class="clear"></div>
          </article>

          <!-- 公共分页 开始 -->
          <div>
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :page-sizes="[5, 10, 20]"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total"
              align="center"
              style="margin-top: 10px">
            </el-pagination>
          </div>
          <!-- 公共分页 结束 -->
        </div>

        
      </section>
    </section>
    <!-- /课程列表 结束 -->
  </div>
</template>


<script>
import songApi from "@/api/song"

export default {

  data(){
    return{
      songcoverpre: process.env.VUE_APP_SongCoverLocationPre,   // 歌曲封面存储

      songQuery:{
        songName:'',
        popularitySort:'',
        releaseTimeSort:'1'  // 默认按照发布时间进行排序
      },   // 歌曲查询条件

      relatedSingerName:'', // 歌手跳转  其实和songQuery.songName是一样的

      currentPage: 1,  // 当前页数
      pageSize: 10, // 每页多少条歌手
      total: 0, 

      songList:[], // 查询到的歌曲列表

    }
  },


  watch: {  //监听
    // 可能出现连续搜索的情况 vue为了节省资源 可能不进行变更 需要通过监听的方式重新定义修改内容
    $route(to, from) {   // 路由变化方式  路由发生变化 方法就会执行
      console.log('路由发生变化')
      this.init()
      this.getSongList()
    }
  },

  mounted(){
    this.init()
    this.getSongList()
  },

  methods:{

    // 初始化 检查路径上是否有针对songName的查询条件
    init(){  
      if(this.$route.query && this.$route.query.searchName){
        // 如果路径上有查询条件
        this.relatedSingerName = this.$route.query.searchName  // 用于页面跳转
        // console.log(this.$route.query.searchName);
        this.songQuery.songName = this.$route.query.searchName  // 赋值给songQuery
      }else{
        // console.log("meiyou");
        // 啥也不干
      }
    },

    // 清空模糊查询的条件
    resetsearchName(){
      if(this.songQuery.songName != ''){
        this.songQuery.songName = ''
        this.getSongList()
      }else{
        this.$message({
        type: 'warning',
        message: "暂未输入查询条件"
      })
      }
      
    },


    // 根据songQuery进行分页查询
    getSongList(){
      songApi.getFrontSongList(this.currentPage,this.pageSize,this.songQuery)
        .then(response=>{
          // console.log(this.songQuery)
          this.songList = response.data.data.records
          this.total = response.data.data.total
        })
    },
   

    // 点击按发布时间排序 后端会根据字符串为空 进行条件选择
    sortByReleaseTime(){
      this.songQuery.releaseTimeSort='1'
      this.songQuery.popularitySort=''
      this.getSongList()
    },

    // 点击按热度进行降序排序 后端会根据字符串为空 进行条件选择
    sortByPopularity(){
      this.songQuery.releaseTimeSort=''
      this.songQuery.popularitySort='1'
      this.getSongList()
    },

    // 后续可以增添新的字段 进行排序
    sortByTODO(){
      this.$message({
        type: 'warning',
        message: "期待后续添加新的字段和排序方式"
      })
    },


    // 时间样式修改
    formatDate(dateTimeString) {
      const date = new Date(dateTimeString);
      const year = date.getFullYear();
      const month = (date.getMonth() + 1).toString().padStart(2, '0');
      const day = date.getDate().toString().padStart(2, '0');
      return `${year}-${month}-${day}`;
    },

    // 分页选择
    handleSizeChange(val) {
      this.pageSize = val
      this.getSongList()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.getSongList()
    },

    // 点击任意一行进行页面跳转
    handleRowClick(row){
      this.$router.push({path: '/song/' + row.id})
    },


    // 点击相关歌手 带着查询条件进行
    relatedSinger(){
      console.log(this.relatedSingerName);
      // 如果搜索条件为空 直接跳转    (避免卡顿情况)
      if(this.relatedSingerName==''){
        this.$router.replace({ path: '/singer'})
      }else{
        this.$router.replace({ path: '/singer', query: { searchName: this.relatedSingerName } });
      }
    },

  }

};
</script>



<style scoped>
  .active {
    background: #bdbdbd;
  }
  .hide {
    display: none;
  }
  .show {
    display: block;
  }

</style>