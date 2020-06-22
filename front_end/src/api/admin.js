import { axios } from '@/utils/request'
const api = {
    adminPre: '/api/admin'
}
export function getManagerListAPI(){
    return axios({
        url: `${api.adminPre}/getAllManagers`,
        method: 'GET'
    })
}
export function editUserInfoAPI(data,userid){
    return axios({
        url: `${api.adminPre}/${userid}/editUserInfo`,
        method: 'POST',
        data
    })
}
export function getUserListAPI(){
    return axios({
        url: `${api.adminPre}/getAllUsers`,
        method: 'GET'
    })
}
export function getVipListAPI(){
    return axios({
        url: `${api.adminPre}/getVip`,
        method: 'GET'
    })
}
export function addManagerAPI(data) {
    return axios({
        url: `${api.adminPre}/addManager`,
        method: 'POST',
        data
    })
}
export function deleteUserAPI(userid) {
    return axios({
        url: `${api.adminPre}/${userid}/deleteUser`,
        method: 'POST',

    })
}