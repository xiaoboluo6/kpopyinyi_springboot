import request from '@/utils/request'

export default {

  // 1. 添加 喜爱歌手
  addFavouriteSinger(singerId){
    return request({
      url: `/kpopservice/singerFavourite/add/${singerId}`,
      method: 'get'
    })
  },
  // 2. 移除 选中的喜爱歌手
  deleteFavouriteSinger(singerFavouriteId){
    return request({
      url: `/kpopservice/singerFavourite/delete/${singerFavouriteId}`,
      method: 'delete'
    })
  },
  // 3. 获取喜爱歌手列表(里面包含喜爱歌手表的id)  封装到SingerFavouriteListVo
  getFavouriteSingerList(){
    return request({
      url: `/kpopservice/singerFavourite/getFavouriteSingerList`,
      method: 'get'
    })
  },

}
