import { axios } from '@/utils/request'
const api = {
    hotelPre: '/api/hotel'
}
export function getHotelsAPI() {
    return axios({
        url: `${api.hotelPre}/all`,
        method: 'get',
    })
}
export function getHotelByIdAPI(param) {
    return axios({
        url: `${api.hotelPre}/${param.hotelId}/detail`,
        method: 'GET',
    })
}
export function getHotelListBySearchAPI(data){
    return axios({
        url: `${api.hotelPre}/search`,
        method: 'GET',
        params: {
            region: data.region,
            address: data.address,
            name: data.name,
            star: data.star,
            rate0: data.rate[0],
            rate1: data.rate[1],
        }
    })
}
export function changeHotelTotalMoneyAPI(hotelId,price) {
    return axios({
        url: `${api.hotelPre}/${hotelId}/${price}/changeHotelTotalMoney`,
        method: 'POST',
    })
}
export function deleteHotelAPI(hotelid) {
    return axios({
        url: `${api.hotelPre}/${hotelid}/deleteHotel`,
        method: 'POST',

    })
}
