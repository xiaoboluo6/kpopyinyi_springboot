<template>
  
  <div>
    <!-- 幻灯片 开始 -->
    <div v-swiper:mySwiper="swiperOption" v-if="bannerList.length">
        <div class="swiper-wrapper">
            <div class="swiper-slide" v-for="banner in bannerList" :key="banner.id">
                <a target="_blank" :href="'/song/'+ banner.songId">
                    <img :src="bannerpre + banner.imageUrl"  :alt="banner.title">
                </a>
            </div>
        </div>
        <div class="swiper-pagination swiper-pagination-white" slot="pagination"></div>
        <div class="swiper-button-prev " slot="button-prev"></div>
        <div class="swiper-button-next " slot="button-next"></div>
    </div>
    <!-- 幻灯片 结束 -->
    
    <div id="0002">
      <!-- 网校课程 开始 -->
      <div>
        <section class="container">
          <header class="comm-title">
            <h2 class="tac">
              <a :class="{ 'c-choose': isPopularitySong, 'c-nochoose': !isPopularitySong }" @click="selectPopularitySong()">热门歌曲</a> 
              <span style="fontsize:26px"> | </span>
              <a :class="{ 'c-choose': !isPopularitySong, 'c-nochoose': isPopularitySong }" @click="selectNewSong()">最新歌曲</a>
            </h2>
          </header>

          <!-- /无数据提示 开始-->
          <section class="no-data-wrap" v-if="songList.length === 0">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- /无数据提示 结束-->

          <div>
            <article class="comm-course-list">
              <ul class="of" id="bna">
                <li v-for="song in songList" :key="song.id">
                  <div class="cc-l-wrap">
                    <section class="course-img">
                      <img
                        :src="songcoverpre + song.cover"
                        class="img-responsive"
                        :alt="song.name"
                      >
                      <div class="cc-mask">
                        <a :href="'/song/'+ song.id" :title="song.name" class="comm-btn c-btn-1">音译文本</a>
                      </div>
                    </section>
                    <h3 class="hLh30 txtOf mt5">
                      <a :href="'/song/'+ song.id" :title="song.name" class="course-title fsize18 c-333">{{song.name}}</a>
                    </h3>
                    <section class="mt5 hLh20 of">
                      <span class="fr jgTag bg-green">
                        <i class="c-fff fsize12 f-fA">访问量 {{song.popularity}}</i>
                      </span>
                      <span class="fl jgAttr c-ccc f-fA">
                        <i class="c-999 f-fA">{{song.singerName}}</i>
                      </span>
                    </section>
                  </div>
                </li>
              </ul>
              <div class="clear"></div>
            </article>
            <section class="tac pt5 pb20">
              <a href="/song" title="全部歌曲" class="comm-btn c-btn-2">全部歌曲</a>
            </section>
          </div>
        </section>
      </div>
      <!-- /网校课程 结束 -->
      <!-- 网校名师 开始 -->
      <div>
        <section class="container">
          <header class="comm-title">
            <h2 class="tac">
              <a :class="{ 'c-choose': isPopularitySinger, 'c-nochoose': !isPopularitySinger }" @click="selectPopularitySinger()">热门歌手</a> 
              <span style="fontsize:26px"> | </span>
              <a :class="{ 'c-choose': !isPopularitySinger, 'c-nochoose': isPopularitySinger }" @click="selectNewSinger()">站长推荐</a>
            </h2>
          </header>

          <!-- /无数据提示 开始-->
          <section class="no-data-wrap" v-if="singerList.length === 0">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- /无数据提示 结束-->

          <div>
            <article class="i-teacher-list">
              <ul class="of">
                <li v-for="singer in singerList" :key="singer.id">
                  <section class="i-teach-wrap">
                    <div class="i-teach-pic">
                      <a :href='"/singer/"+singer.id' :title="singer.name">
                        <img :alt="singer.name" :src="avatarpre + singer.avatar">
                      </a>
                    </div>
                    <div class="mt10 hLh30 txtOf tac">
                      <a :href='"/singer/"+singer.id' :title="singer.name" class="fsize18 c-666">{{singer.name}}</a>
                    </div>
                    <div class="mt10 i-q-txt">
                      <p
                        class="c-999 f-fA"
                      >{{singer.description}}</p>
                    </div>
                  </section>
                </li>
              </ul>
              <div class="clear"></div>
            </article>
            <section class="tac pt5 pb20">
              <a href="/singer" title="全部歌手" class="comm-btn c-btn-2">全部歌手</a>
            </section>
          </div>
        </section>
      </div>
      <!-- /网校名师 结束 -->
    </div>
  </div>
</template>

<script>
import indexApi from '@/api/index'

export default {
  data () {
    return {
      swiperOption: {
          //配置分页
          pagination: {
            el: '.swiper-pagination'//分页的dom节点
          },
          //配置导航
          navigation: {
            nextEl: '.swiper-button-next',//下一页dom节点
            prevEl: '.swiper-button-prev'//前一页dom节点
          },
          // 自动轮播配置
          autoplay: {
              delay: 3000, // 轮播间隔时间，单位为毫秒
              disableOnInteraction: false, // 用户操作后是否停止自动轮播，默认为 true
          },
          // 循环轮播配置
          loop: true,
      },

      songList:[],
      singerList:[],
      bannerList:[],
      avatarpre: process.env.VUE_APP_AvatarLocationPre,
      songcoverpre: process.env.VUE_APP_SongCoverLocationPre,
      bannerpre: process.env.VUE_APP_BannerLocationPre,

      isPopularitySong: true, // 默认选中热门歌曲还是最新歌曲

      isPopularitySinger: true // 默认选中热门歌手还是站长推荐
    }
  },

  created(){
    this.getIndexInfo()
  },

  methods:{
    getIndexInfo(){
      indexApi.getIndexData()
        .then(response =>{
          this.songList = response.data.data.songList
          this.singerList = response.data.data.singerList
          this.bannerList = response.data.data.bannerList
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


    // 选择热门歌曲
    selectPopularitySong() {
      this.isPopularitySong = true;  // 表示选择了热门歌曲  控制显示样式
      indexApi.getSongPopularityTOP()
        .then(response =>{
          this.songList = response.data.data.songPopularityList
        })
    },
    // 选择最新歌曲
    selectNewSong() {
      this.isPopularitySong = false;  // 表示选择了最新歌曲  控制显示样式
      indexApi.getSongNewTOP()
        .then(response =>{
          this.songList = response.data.data.songNewList
        })
    },


    // 选择热门歌手
    selectPopularitySinger() {
      this.isPopularitySinger = true;  // 表示选择了热门歌手  控制显示样式
      indexApi.getSingerPopularityTOP()
        .then(response =>{
          this.singerList = response.data.data.singerPopularityList
        })
    },
    // 选择站长推荐歌手
    selectNewSinger() {
      this.isPopularitySinger = false;  // 表示选择了站长推荐歌手  控制显示样式
      indexApi.getSingerFavouriteFront()
        .then(response =>{
          this.singerList = response.data.data.singerFavouriteList
        })
    }
  }
}
</script>


<style scoped>
.c-choose {
  font-size: 26px;
  font-weight: normal;
  color: black;
}

.c-nochoose {
  font-size: 18px;
  color: grey;
}
</style>

