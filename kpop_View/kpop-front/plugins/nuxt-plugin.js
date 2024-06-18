import Vue from 'vue'
import VueAwesomeSwiper from 'vue-awesome-swiper/dist/ssr'
import VueClipBoard from 'vue-clipboard2'   // 一键复制组件  好像没有使用捏
import ElementUI from 'element-ui' //element-ui的全部组件
import 'element-ui/lib/theme-chalk/index.css'//element-ui的css
Vue.use(ElementUI) //使用elementUI
Vue.use(VueAwesomeSwiper)
Vue.use(VueClipBoard)