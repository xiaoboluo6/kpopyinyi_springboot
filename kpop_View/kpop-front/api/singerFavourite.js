import request from '@/utils/request'

export default{

    // 1. 获取超级管理员的推荐歌手
    getFavouriteSingerList(){
        return request({
            url: `/kpopservice/singerFavourite/getFavouriteSingerList`,
            method: 'get'
        })
    },

}