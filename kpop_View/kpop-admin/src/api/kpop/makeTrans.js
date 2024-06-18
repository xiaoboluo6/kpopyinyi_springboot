import request from '@/utils/request'

export default {

  // 1. 根据formData  制作带时间戳的音译文本
  // formData包括file1歌词lrc和file2的RomaLrc
  getTransWithTime(formData){
    return request({
      url: `/kpopservice/transMaker/makeRomaEnglishWithTime`,
      method: 'post',
      data: formData
    })
  },

  // 1. 根据formData  制作不带时间戳的音译文本
  // formData包括file1歌词lrc和file2的RomaLrc
  getTransWithoutTime(formData){
    return request({
      url: `/kpopservice/transMaker/makeRomaEnglishWithoutTime`,
      method: 'post',
      data: formData
    })
  },
  
}
