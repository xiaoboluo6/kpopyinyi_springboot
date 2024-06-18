import request from '@/utils/request'

export default {

  // 1. 新增歌手
  addSinger(singer){
    return request({
      url: `/kpopservice/singer/add`,
      method: 'post',
      data: singer
    })
  },

  // 2. 歌手列表 分页!!条件查询!!
  pageQuerySinger(page,limit,singerQuery){
    return request({
      url: `/kpopservice/singer/${page}/${limit}`,
      method: 'post',
      data: singerQuery
    })
  },

  // 3. 根据id查询歌手信息
  getById(id){
    return request({
      url: `/kpopservice/singer/getById/${id}`,
      method: 'get',
    })
  },

  // 4. 修改歌手信息
  updateById(singer){
    return request({
      url: `/kpopservice/singer/update`,
      method: 'post',
      data: singer
    })
  },

  // 5. 根据id删除指定歌手
  delete(deleteId){
    return request({
      url: `/kpopservice/singer/${deleteId}`,
      method: 'delete',
    })
  },

  // 6. 歌手列表 只做条件查询!! 这是添加歌曲时候的歌手选择器
  querySinger(singerQuery){
    return request({
      url: `/kpopservice/singer/queryList`,
      method: 'post',
      data: singerQuery
    })
  },



}
