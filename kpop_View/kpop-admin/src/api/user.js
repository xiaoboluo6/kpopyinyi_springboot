import request from '@/utils/request'

export function login(data) {
  return request({
    url: `/managecenter/admin/login`,
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: `/managecenter/admin/info`,
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: `/managecenter/admin/logout`,
    method: 'post'
  })
}
