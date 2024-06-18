import request from '@/utils/request'

export default{
    // 1. 前端用户发送留言
    sendMessage(message){
      return request({
          url: `/interaction/frontmessage/send`,
          method: 'post',
          data: message
      })
    },

    // 2. 前端用户获取全部留言
    getFrontMessage(currentPage,pageSize){
      return request({
        url: `/interaction/frontmessage/getFrontMessage/${currentPage}/${pageSize}`,
        method: 'get'
      })
    },

    // 3. 前端用户获取自己发布的的留言
    getFrontMemberMessage(currentPage,pageSize){
      return request({
        url: `/interaction/frontmessage/getFrontMemberMessage/${currentPage}/${pageSize}`,
        method: 'get'
      })
    },



}