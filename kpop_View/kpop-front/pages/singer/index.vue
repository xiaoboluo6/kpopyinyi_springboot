<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- 讲师列表 开始 -->
    <section class="container">
      <header class="comm-title all-teacher-title">
        <h2 class="fl tac">
          <span class="c-333">全部歌手</span>
        </h2>
        <section class="c-tab-title">
          <a id="reset" title="reset" href="#" @click.prevent="resetQuery()">重置选择</a>
          <!-- <c:forEach var="subject" items="${subjectList }">
                            <a id="${subject.subjectId}" title="${subject.subjectName }" href="javascript:void(0)" onclick="submitForm(${subject.subjectId})">${subject.subjectName }</a>
          </c:forEach>-->
        </section>
      </header>

      <section class="c-sort-box">
        <section class="c-s-dl">
          <dl>
            <dt>
              <span class="c-999 fsize14">歌手类别</span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li>
                  <a title="全部" href="#" @click="searchAll()">全部</a>
                </li>
                <!-- 歌手类别捏捏捏 -->
                <li v-for="(category,index) in categoryList" :key="category.id" :class="{active:chooseIndex==index}">
                  <a :title="category.name" href="#" @click="searchOneCategory(category.id,index)">{{category.name}}</a>
                </li>
                <li>
                    <aside class="h-r-search" style="padding-top:0px;margin:-2px 0 0 0">
                      <form action="#" method="post">
                        <label class="h-r-s-box">
                          <input type="text" placeholder="输入你想搜索的歌手" v-model="singerQuery.name">
                          <button type="button" class="s-btn" @click="searchNameLike()">
                            <em class="icon18">&nbsp;</em>
                          </button>
                        </label>
                      </form>
                    </aside>
                </li>
              </ul>
            </dd>
          </dl>

          <div class="clear"></div>

          <div>
            <!-- /无数据提示 开始-->
            <section class="no-data-wrap" v-if="total === 0">
              <em class="icon30 no-data-ico">&nbsp;</em>
              <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
            </section>
            <!-- 有数据提示 结束-->
            <article class="i-teacher-list" v-if="total>0">
              <ul class="of">
                <li v-for="singer in singerList" :key="singer.id">
                  <section class="i-teach-wrap">
                    <div class="i-teach-pic">
                      <a :href="'/singer/'+ singer.id" :title="singer.name" target="_blank">
                        <img :src="avatarpre + singer.avatar" :alt="singer.name">
                      </a>
                    </div>
                    <div class="mt10 hLh30 txtOf tac">
                      <a :href="'/singer/'+ singer.id" :title="singer.name" target="_blank" class="fsize18 c-666">{{singer.name}}</a>
                    </div>
                    <div class="mt15 i-q-txt">
                      <p class="c-999 f-fA">{{singer.description}}</p>
                    </div>
                  </section>
                </li>
              </ul>
              <div class="clear"></div>
            </article>
          </div>
          <!-- 公共分页 开始 -->
          <div class="block">
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page.sync=currentPage
              :page-sizes="[10,15,20]"
              :page-size=pageSize
              layout="sizes, prev, pager, next"
              :total=total
              style="margin: 0 auto; text-align: center;"
            >
            </el-pagination>
          </div>
          <!-- 公共分页 结束 -->

          </section>
      </section>
    </section>
    <!-- /讲师列表 结束 -->
  </div>
</template>


<script>
import categoryApi from "@/api/category";
import singerApi from "@/api/singer"

export default {

  data(){
    return{
      avatarpre: process.env.VUE_APP_AvatarLocationPre,  // 头像存储地址pre
      categoryList: [],   // 歌手类别种类
      chooseIndex: -1,   // 类别选择

      singerQuery:{},    // 歌手条件查询封装类

      singerList:[],     // 查询到的歌手列表
      total: 0,          // 查询到的歌手总数

      currentPage: 1,
      pageSize: 10,

    }
  },

  mounted(){
    this.init()
    this.getCategoryList()
    this.getSingerList()
  },

  methods:{
    // 首先检查一下路径中有没有搜索条件
    init(){
      if(this.$route.query && this.$route.query.searchName){
        this.singerQuery.name = this.$route.query.searchName
      }
    },
  
    // 获取歌手全部类别
    getCategoryList(){
      categoryApi.getAllCategory()
        .then((response) => {
          this.categoryList = response.data.data.items;
      });
    },

    // 重置选择
    resetQuery(){
      // 确保别的种类不被选中
      this.chooseIndex = -1 
      // 重置singerQuery 
      this.singerQuery = {}
      // 页面切换到第一页
      this.currentPage = 1
      // 进行查询
      this.getSingerList()
    },

    // 歌手种类  点击全部的情况
    searchAll(){
      // 确保别的种类不被选中
      this.chooseIndex = -1  
      // 将category这个条件删掉
      this.singerQuery.categoryId = ''
      this.getSingerList()
    },

    searchOneCategory(categoryId,index){
      // 把传递index赋值给chooseIndex 为了active样式生效
      this.chooseIndex = index   
      // 增加查询条件  歌手种类
      this.singerQuery.categoryId = categoryId
      // 进行查询
      this.getSingerList()
    },

    // 根据输入名称 进行模糊查询 
    searchNameLike(){
      this.getSingerList()
    },

    // 分页条件查询歌手方法
    getSingerList(){
      singerApi.getSingerPageQuery(this.currentPage,this.pageSize,this.singerQuery)
        .then(response =>{
          this.singerList = response.data.data.records
          this.total = response.data.data.total
          // console.log(this.singerList);
          // console.log(this.total);
        })
    },

    // 分页方法
    handleSizeChange(val) {
      this.pageSize = val
      this.getSingerList()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.getSingerList()
    }


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