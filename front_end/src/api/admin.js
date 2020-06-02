import { axios } from '@/utils/request'
const api = {
    adminPre: '/api/admin'
}
export function getManagerListAPI(){
    return axios({
        url: `${api.adminPre}/getAllManagers`,
        method: 'POST'
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
        method: 'POST'
    })
}
export function addManagerAPI(data) {
    return axios({
        url: `${api.adminPre}/addManager`,
        method: 'POST',
        data
    })
}
//
export function getHotelsAPI() {
    return axios({
        url: `${api.adminPre}/getHotels`,
        method: 'GET'
    })
}
export function addHotelAPI(data) {
    return axios({
        url: `${api.adminPre}/addHotel`,
        method: 'POST',
        data,
    })
}
export function setHotelManagerAPI(hotelid,managerid) {
   // console.log(hotelid,managerid)
    return axios({
        url: `${api.adminPre}/${hotelid}/${managerid}/setHotelManager`,
        method: 'POST',

    })
}
export function deleteUserAPI(userid) {
    return axios({
        url: `${api.adminPre}/${userid}/deleteUser`,
        method: 'POST',

    })
}
export function deleteHotelAPI(hotelid) {
    return axios({
        url: `${api.adminPre}/${hotelid}/deleteHotel`,
        method: 'POST',

    })
}