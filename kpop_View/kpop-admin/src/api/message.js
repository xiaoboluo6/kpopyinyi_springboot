import request from '@/utils/request'

export default {

  // 1. 查询所有消息
  getAllMessage(currentPage,pageSize,messageQuery){
    return request({
      url: `/interaction/message/getAllMessage/${currentPage}/${pageSize}`,
      method: 'post',
      data: messageQuery
    })
  },

  // 2. 根据messageId 查询对应的message
  getMessageById(messageId){
    return request({
      url: `/interaction/message/getMessageById/${messageId}`,
      method: 'get',
    })
  },

  // 3. 修改message 更新status状态
  updateMessageById(message){
    return request({
      url: `/interaction/message/updateMessage`,
      method: 'post',
      data: message
    })
  },

  // 4. 根据messageId 删除对应的message
  deleteMessageById(messageId){
    return request({
      url: `/interaction/message/deleteMessageById/${messageId}`,
      method: 'delete',
    })
  },

  // 5. 一键回复
  quickReply(messageId){
    return request({
      url: `/interaction/message/quickReply/${messageId}`,
      method: 'get',
    })
  },



  
}
