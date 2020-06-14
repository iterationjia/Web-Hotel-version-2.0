import { axios } from '@/utils/request'
const api = {
    hotelPre: '/api/hotel'
}
export function addRoomAPI(data) {
    return axios({
        url: `${api.hotelPre}/roomInfo`,
        method: 'POST',
        data,
    })
}
export function getManagerHotelsAPI(param) {
    return axios({
        url: `${api.hotelPre}/${param.managerId}/managerHotels`,
        method: 'GET'
    })
}
export function addHotelAPI(data) {
    return axios({
        url: `${api.hotelPre}/addHotel`,
        method: 'POST',
        data,
    })


}
export function editHotelAPI(data) {
    return axios({
        url: `${api.hotelPre}/editHotel`,
        method: 'POST',
        data,
    })
}
export function updateHotelImgAPI(hotelId,data) {
    return axios({
        url: `${api.hotelPre}/${hotelId}/updateHotelImg`,
        method: 'POST',
        data
    })
}
export function editRoomPriceAPI(roomId,val) {
    return axios({
        url: `${api.hotelPre}/editRoomPrice`,
        method: 'GET',
        params: {
            roomId:roomId,
            val:val
        }
    })
}
export function editRoomTotalAPI(roomId,val) {
    return axios({
        url: `${api.hotelPre}/editRoomTotal`,
        method: 'GET',
        params: {
            roomId:roomId,
            val:val
        }
    })
}
export function editRoomCurNumAPI(roomId,val) {
    return axios({
        url: `${api.hotelPre}/editRoomCurNum`,
        method: 'GET',
        params: {
            roomId:roomId,
            val:val
        }
    })
}
export function deleteRoomAPI(roomId) {
    return axios({
        url: `${api.hotelPre}/${roomId}/deleteRoom`,
        method: 'POST'
    })
}