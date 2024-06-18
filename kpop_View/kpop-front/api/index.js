import request from '@/utils/request'

export default{

    // 1. 查询热门歌手和歌曲 以及 轮播图
    getIndexData(){
        return request({
            url: `/kpopservice/homepage/index`,
            method: 'get'
        })
    },

    // 2. 首页展示热度最高的10首歌曲
    getSongPopularityTOP(){
        return request({
            url: `/kpopservice/homepage/getSongPopularityTOP`,
            method: 'get',
        })
    },

    // 3. 首页展示最新的10首歌曲
    getSongNewTOP(){
        return request({
            url: `/kpopservice/homepage/getSongNewTOP`,
            method: 'get',
        })
    },

    // 4. 首页展示热度最高的10位歌手
    getSingerPopularityTOP(){
        return request({
            url: `/kpopservice/homepage/getSingerPopularityTOP`,
            method: 'get',
        })
    },

    // 5. 首页展示 站长推荐10位歌手 这里返回KpopSinger的list
    getSingerFavouriteFront(){
        return request({
            url: `/kpopservice/homepage/getSingerFavouriteFront`,
            method: 'get',
        })
    },
}