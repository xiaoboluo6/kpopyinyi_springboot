import request from '@/utils/request'

export default {

  // 1. 新增歌曲基本信息 baseInfo 此时还没有提交 所以status=0
  addSongBaseInfo(song){
    return request({
      url: `/kpopservice/song/addSongBaseInfo`,
      method: 'post',
      data: song
    })
  },

  // 2. 根据id返回歌曲信息
  getSongBaseInfoById(songid){
    return request({
      url: `/kpopservice/song/getSongBaseInfoById/${songid}`,
      method: 'get',
    })
  },

  // 3. 修改歌曲基本信息 baseInfo 此时还没有提交 所以status=0
  updateSongBaseInfo(song){
    return request({
      url: `/kpopservice/song/updateSongBaseInfo`,
      method: 'post',
      data: song
    })
  },

  // 4. 根据歌曲id获取歌曲基本信息 不需要歌手列表 用于编写transliteration时候的回显
  getSongSimpleInfoById(songid){
    return request({
      url: `/kpopservice/song/getSongSimpleInfoById/${songid}`,
      method: 'get',
    })
  },

  // 5. 根据songId 获取歌曲完整信息 包括歌手歌曲音译 即SongPublish
  getSongPublish(songid){
    return request({
      url: `/kpopservice/song/getSongPublish/${songid}`,
      method: 'get',
    })
  },


  // 6. 根据songId 完成最后的发布和提交
  finalpublish(songid){
    return request({
      url: `/kpopservice/song/finalPublish/${songid}`,
      method: 'get',
    })
  },

  // 7. 分页!!条件!!查询 获得song/list页面的展示内容  SongPublishVo
  getSongList(currentPage,pageSize,songListQuery){
    return request({
      url: `/kpopservice/song/songListPageQuery/${currentPage}/${pageSize}`,
      method: 'post',
      data: songListQuery
    })
  },

  // 8. 根据歌曲id 删除歌曲 同时删除歌曲音译transliteration
  deleteSong(songid){
    return request({
      url: `/kpopservice/song/deleteSong/${songid}`,
      method: 'delete',
    })
  },


}
