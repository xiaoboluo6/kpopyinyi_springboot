import request from '@/utils/request'

export default {
  // 1.增加管理员  普通管理员/超级管理员
  add(registerAdmin) {
    return request({
      url: `/managecenter/admin/add`,
      method: 'post',
      data: registerAdmin
    })
  },
  // 2. 根据管理员id修改用户权限  升级为超级管理员
  upgrade(adminId) {
    return request({
      url: `/managecenter/admin/upgrade/${adminId}`,
      method: 'get'
    })
  },
  // 3. 根据管理员id修改用户权限  降级为普通管理员
  downgrade(adminId) {
    return request({
      url: `/managecenter/admin/downgrade/${adminId}`,
      method: 'get'
    })
  },
  // 4. 根据管理员id禁用管理员
  block(adminId) {
    return request({
      url: `/managecenter/admin/block/${adminId}`,
      method: 'get'
    })
  },
  // 5. 根据管理员id解封管理员
  unblock(adminId) {
    return request({
      url: `/managecenter/admin/unblock/${adminId}`,
      method: 'get'
    })
  },
  // 6. 后端返回所有管理员信息
  getAllAdmin() {
    return request({
      url: `/managecenter/admin/getAll`,
      method: 'get'
    })
  }
}
