import request from '@/utils/request'

export default {

  // 1. 分页条件查询  
  getMemberList(currentPage,pageSize,memberQuery){
    return request({
      url: `/usercenter/memberAdmin/getList/${currentPage}/${pageSize}`,
      method: 'post',
      data: memberQuery
    })
  },

  // 2. 根据id禁用用户
  block(memberId){
    return request({
      url: `/usercenter/memberAdmin/block/${memberId}`,
      method: 'get'
    })
  },

  // 3. 根据id解封用户
  unblock(memberId){
    return request({
      url: `/usercenter/memberAdmin/unblock/${memberId}`,
      method: 'get'
    })
  },
  
}