import request from '@/utils/request'

export default{

    // 1. 分页 条件查询 歌手信息
    getSingerPageQuery(current,pageSize,pageQuery){
        return request({
            url: `/kpopservice/singer/${current}/${pageSize}`,
            method: 'post',
            data: pageQuery
        })
    },

    // 2. 根据歌手id查询 歌手完整信息(包括歌手基本信息和歌手演唱的歌曲)
    getSingerDetailInfo(singerId){
      return request({
        url: `/kpopservice/singerfront/getSingerDetailInfo/${singerId}`,
        method: 'get',
      })
    },
}