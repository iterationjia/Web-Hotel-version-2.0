import { axios } from '@/utils/request'
const api = {
    hotelPre: '/api/hotel'
}
export function getHotelsAPI(userid) {
    return axios({
        url: `${api.hotelPre}/all`,
        method: 'get',
        params: {
            userid: userid
        }
    })
}
export function getHotelByIdAPI(param) {
    return axios({
        url: `${api.hotelPre}/${param.hotelId}/detail`,
        method: 'GET',
    })
}
export function getHotelListBySearchAPI(data,userid){
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
            userid: userid
        }
    })
}
export function getHotelCommentsAPI(data) {
    return axios({
        url: `${api.hotelPre}/${data.hotelId}/comments`,
        method: 'GET'
    })
}
