import request from '@/utils/request'

export default{

    // 1. 根据歌曲id 查询歌曲详细信息 包括 歌曲基本信息、歌手列表、歌曲音译
    getFrontSongTrans(songId){
      return request({
          url: `/kpopservice/songfront/getFrontSongTrans/${songId}`,
          method: 'get',
      })
    },

    // 2. 前端 根据搜索条件进行歌曲列表查询
    getFrontSongList(currentPage,pageSize,songFrontQuery){
        return request({
            url: `/kpopservice/songfront/songFrontList/${currentPage}/${pageSize}`,
            method: 'post',
            data: songFrontQuery
        })
    },

}