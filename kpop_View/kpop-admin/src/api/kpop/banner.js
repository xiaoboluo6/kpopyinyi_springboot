import request from '@/utils/request'

export default {

  // 1. 获取全部banner
  getAllBanner(){
    return request({
      url: `/kpopservice/banner/getAllBanner`,
      method: 'get',
    })
  },
 
  // 2. 根据id获取banner
  getBannerById(bannerId){
    return request({
      url: `/kpopservice/banner/getBannerById/${bannerId}`,
      method: 'get',
    })
  },

  // 添加banner的时候 需要选择对应的歌曲 采用SongBannerQuery进行查询 有两个条件 是对歌手名字和歌曲名字的模糊查询
  queryBannerSong(songBannerQuery){
    return request({
      url: `/kpopservice/banner/queryBannerSong`,
      method: 'post',
      data: songBannerQuery
    })
  },

  // 添加banner的时候 banner选择了对应的song之后,根据songId返回song的基本信息
  getBannerRelatedSong(songId){
    return request({
      url: `/kpopservice/banner/getBannerRelatedSong/${songId}`,
      method: 'get',
    })
  },

  
  // 3. 新增banner
  saveBanner(bannerInfo){
    return request({
      url: `/kpopservice/banner/saveBanner`,
      method: 'post',
      data: bannerInfo
    })
  },

  // 4. 修改banner
  updateBanner(bannerInfo){
    return request({
      url: `/kpopservice/banner/updateBanner`,
      method: 'post',
      data: bannerInfo
    })
  },

  // 5. 删除banner
  deleteBanner(bannerId){
    return request({
      url: `/kpopservice/banner/delete/${bannerId}`,
      method: 'delete',
    })
  },


  // 6. turnoff 将banner的isShow变成0
  turnOffById(bannerId){
    return request({
      url: `/kpopservice/banner/turnOffById/${bannerId}`,
      method: 'get',
    })
  },

  // 7. turnon  将banner的isShow变成1
  turnOnById(bannerId){
    return request({
      url: `/kpopservice/banner/turnOnById/${bannerId}`,
      method: 'get',
    })
  },


  

  
}
