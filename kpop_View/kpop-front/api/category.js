import request from '@/utils/request'

export default{

    // 1. 查询所有的类别种类 其实也就4个
    getAllCategory(){
        return request({
            url: `/kpopservice/category/getAll`,
            method: 'get'
        })
    }
}