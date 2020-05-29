import { axios } from '@/utils/request'
const api = {
    orderPre: '/api/order'
}
export function reserveHotelAPI(data) {
    return axios({
        url: `${api.orderPre}/addOrder`,
        method: 'POST',
        data,
    })
}
export function getAllOrdersAPI() {
    return axios({
        url: `${api.orderPre}/getAllOrders`,
        method: 'GET',
    })
}
export function getManagerOrdersAPI(param) {
    return axios({
        url: `${api.orderPre}/${param.managerId}/getManagerOrders`,
        method: 'GET',
    })
}
export function getUserOrdersAPI(data) {
    return axios({
        url: `${api.orderPre}/${data.userId}/getUserOrders`,
        method: 'GET',
    })
}
export function cancelOrderAPI(orderId) {
    return axios({
        url: `${api.orderPre}/${orderId}/annulOrder`,
        method: 'GET',
    })
}

export function deleteOrderAPI(data) {
    return axios({
        url: `${api.orderPre}/deleteOrder`,
        method: 'POST',
        data,
    })
}

// export function updateUserOrderCommentAPI(data){
//     return axios({
//         url: `${api.orderPre}/${data.orderId}/updateOrderComment`,
//         method: 'GET',
//     })
// }