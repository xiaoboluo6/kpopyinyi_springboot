import axios from 'axios'
import cookie from 'js-cookie'
import { MessageBox, Message } from 'element-ui'

//创建axios实例
const service = axios.create({
    baseURL: 'http://localhost:9002', // api的base_url
    timeout: 500000 // 请求超时时间  20s=20000
});

// 第三步 创建拦截器 http request 拦截器
service.interceptors.request.use(
    config => {
      // debugger
      // 判断cookie里面是否有名称是kpop_token数据
      if (cookie.get('kpop_token')) {
        // 有的话就把cookie值放入header里面
        config.headers['token'] = cookie.get('kpop_token');
      }
      return config
    },
    err => {
    return Promise.reject(err);
});

// http response 拦截器
service.interceptors.response.use(
  response => {
    //debugger
    if (response.data.code == 28004) {
        console.log("response.data.resultCode是28004")
        // 返回 错误代码-1 清除ticket信息并跳转到登录页面       
        Message({
          message: '您未登录或登录状态已过期，请重新登录',
          type: 'warning',
          duration: 0.5 * 1000
        });
        // 延迟页面跳转，确保消息提示显示完整
        setTimeout(() => {
          window.location.href = "/login";
        }, 500);  // 500毫秒即0.5秒，与Message的duration一致
        return
    }else{
      return response;
    }
  },
  error => {
    return Promise.reject(error.response)   // 返回接口返回的错误信息
});

export default service