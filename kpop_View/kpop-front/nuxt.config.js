module.exports = {

  // some nuxt config...
  plugins: [
    { src: '~/plugins/nuxt-plugin.js', ssr: false }
  ],

  css: [
    'swiper/dist/css/swiper.css',
    'vue-image-crop-upload/upload.css'
  ],

  env: {
    // 阿里云服务器  47.100.73.241
    // 要使用的话 前面加上  如process.env.VUE_APP_AvatarLocationPre
    VUE_APP_AvatarLocationPre : "http://localhost:9002/singerAvatar/",
    VUE_APP_SongCoverLocationPre : "http://localhost:9002/songCover/",
    VUE_APP_BannerLocationPre : "http://localhost:9002/bannerPicture/",
    VUE_APP_MemberAvatarpre:"http://localhost:9002/memberAvatar/"
  },

  /*
  ** Headers of the page
  */
  head: {
    title: '菠萝kpop音译捏',
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      { hid: 'keywords', name: 'keywords', content: '菠萝kpop音译捏-哼歌小助手' },
      { hid: 'description', name: 'description', content: '菠萝kpop音译捏-哼歌小助手' }
    ],
    link: [
      { rel: 'icon', type: 'image/x-icon', href: '/logo.ico' }
    ]
  },
  /*
  ** Customize the progress bar color
  */
  loading: { color: '#3B8070' },
  /*
  ** Build configuration
  */
  build: {
    /*
    ** Run ESLint on save
    */
    extend (config, { isDev, isClient }) {
      // 0111关闭 不想使用eslint
      //if (isDev && isClient) {
      //  config.module.rules.push({
      //    enforce: 'pre',
      //    test: /\.(js|vue)$/,
      //    loader: 'eslint-loader',
      //    exclude: /(node_modules)/
      //  })
      //}
    }
  }
}

