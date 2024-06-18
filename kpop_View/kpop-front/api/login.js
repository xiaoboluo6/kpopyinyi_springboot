import request from '@/utils/request'

export default{

    // 登录
    submitLogin(userInfo){
        return request({
            url: `/usercenter/member/login`,
            method: 'post',
            data: userInfo
        })
    },


    // 根据token值获取用户信息
    getLoginMemberInfo(){
        return request({
            url: `/usercenter/member/getMemberInfo`,
            method: 'get'
        })
    }

}