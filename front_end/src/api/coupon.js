import { axios } from '@/utils/request'
const api = {
    couponPre: '/api/coupon'
}
export function hotelTargetMoneyAPI(data) {
    return axios({
        url: `${api.couponPre}/hotelTargetMoney`,
        method: 'POST',
        data,
    })
}

export function hotelAllCouponsAPI(hotelId) {
    return axios({
        url: `${api.couponPre}/hotelAllCoupons`,
        method: 'GET',
        params: {hotelId: hotelId},
    })
}

export function orderMatchCouponsAPI(params) {
    return axios({
        url: `${api.couponPre}/orderMatchCoupons`,
        method: 'GET',
        params,
    })
}

export function getCouponListAPI(){
    return axios({
        url: `${api.couponPre}/getCouponList`,
        method: 'GET'
    })
}

export function deleteCouponAPI(couponid) {
    return axios({
        url: `${api.couponPre}/${couponid}/deleteCoupon`,
        method: 'POST',

    })
}