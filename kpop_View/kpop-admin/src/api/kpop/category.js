import request from '@/utils/request'

export default {

  // 1. 查询所有歌手种类
  getAllCategory(){
    return request({
      url: `/kpopservice/category/getAll`,
      method: 'get',
    })
  },

  // 2. 新增歌手种类
  addCategory(categoryInfo){
    return request({
      url: `/kpopservice/category/add`,
      method: 'post',
      data: categoryInfo
    })
  },

  // 3. 删除歌手种类
  deleteCategory(deleteId){
    return request({
      url: `/kpopservice/category/${deleteId}`,
      method: 'delete',
    })
  },

  // 4. 修改歌手种类
  updateCategory(categoryInfo){
    return request({
      url: `/kpopservice/category/update`,
      method: 'post',
      data: categoryInfo
    })
  },


  // 5. 根据id查询种类信息
  getCategoryById(id){
    return request({
      url: `/kpopservice/category/getById/${id}`,
      method: 'get',
    })
  },
  
}
