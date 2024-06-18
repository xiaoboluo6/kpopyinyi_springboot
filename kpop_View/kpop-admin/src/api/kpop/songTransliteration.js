import request from '@/utils/request'

export default {

  // 1. 新增歌曲音译
  addSongTrans(songTrans){
    return request({
      url: `/kpopservice/songtransliteration/addTrans`,
      method: 'post',
      data: songTrans
    })
  },

  // 2. 根据songid获取音译文本
  getTransBySongId(id){
    return request({
      url: `/kpopservice/songtransliteration/getTransBySongId/${id}`,
      method: 'get',
    })
  },

  // 3. 修改音译文本
  updateSongTrans(songTrans){
    return request({
      url: `/kpopservice/songtransliteration/updateTrans`,
      method: 'post',
      data: songTrans
    })
  },
}
