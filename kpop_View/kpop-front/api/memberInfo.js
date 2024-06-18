import request from '@/utils/request'

export default{

    // 修改用户信息 并返回token
    updateMember(formItem){
        return request({
            url: `/usercenter/member/updateMemberInfo`,
            method: 'post',
            data: formItem
        })
    }
}