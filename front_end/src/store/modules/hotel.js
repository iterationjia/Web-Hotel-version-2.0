import { message } from 'ant-design-vue'
import {
    getHotelsAPI,
    getHotelByIdAPI,
    getHotelListBySearchAPI,
    deleteHotelAPI,
} from '@/api/hotel'
import {
    reserveHotelAPI,
    getUserHotelOrdersAPI,
    getCommentsAPI,
} from '@/api/order'
import {
    orderMatchCouponsAPI,
} from '@/api/coupon'

const hotel = {
    state: {
        hotelList: [
            
        ],
        hotelListParams: {
            pageNo: 0,
            pageSize: 12
        },
        hotelListLoading: true,
        currentHotelId: '',
        currentHotelInfo: {

        },
        orderModalVisible: false,
        searchModalVisible: false,
        currentOrderRoom: {

        },
        userHotelOrderList:[],
        hotelComments:[],
        orderMatchCouponList: [

        ]
    },
    mutations: {
        set_hotelList: function(state, data) {
            state.hotelList = data
        },
        set_hotelListSortedByRate: function(state) {
            state.hotelList.sort(function(a,b){
                return b.rate-a.rate
            })
        },
        set_hotelListSortedByPrice: function(state) {
            state.hotelList.sort(function (a,b) {
                return a.minPrice-b.minPrice
            })
        },
        set_hotelListSortedByStar: function(state) {
            state.hotelList.sort(function (a,b) {
                if (a.hotelStar>b.hotelStar) {
                    return 1
                } else if (a.hotelStar<b.hotelStar){
                    return -1
                } else {
                    return 0
                }
            })
        },
        set_hotelListParams: function(state, data) {
            state.hotelListParams = {
                ...state.hotelListParams,
                ...data,
            }
        },
        set_hotelListLoading: function(state, data) {
            state.hotelListLoading = data
        },
        set_currentHotelId: function(state, data) {
            state.currentHotelId = data
        },
        set_currentHotelInfo: function(state, data) {
            state.currentHotelInfo = {
                ...state.currentHotelInfo,
                ...data,
            }
        },
        set_userHotelOrderList: function(state,data) {
            state.userHotelOrderList = data
        },
        set_orderModalVisible: function(state, data) {
            state.orderModalVisible = data
        },
        set_searchModalVisible: function(state, data) {
            state.searchModalVisible = data
        },
        set_currentOrderRoom: function(state, data) {
            state.currentOrderRoom = {
                ...state.currentOrderRoom,
                ...data,
            }
        },
        set_orderMatchCouponList: function(state, data) {
            state.orderMatchCouponList = data
        },
        set_hotelComments: function (state, data) {
            state.hotelComments = data
        }
    },

    actions: {
        getHotelList: async({commit, state, getters}) => {
            const res = await getHotelsAPI(getters.userId)
            if(res){
                for (var i = 0; i<res.length; i++) {
                    const n_res = await getUserHotelOrdersAPI({
                        hotelId: res[i].id,
                        userId: getters.userId
                    })
                    if (n_res.length > 0) {
                        res[i]["scheduled"] = true
                    } else {
                        res[i]["scheduled"] = false
                    }
                }
                commit('set_hotelList', res)
                commit('set_hotelListLoading', false)
            }
        },
        getHotelListBySearch: async ({commit, state, getters}, data) => {
            const res = await getHotelListBySearchAPI(data,getters.userId)
            if(res){
                for (var i = 0; i<res.length; i++) {
                    const n_res = await getUserHotelOrdersAPI({
                        hotelId: res[i].id,
                        userId: getters.userId
                    })
                    if (n_res.length > 0) {
                        res[i]["scheduled"] = true
                    } else {
                        res[i]["scheduled"] = false
                    }
                }
                commit('set_hotelList', res)
                commit('set_hotelListLoading', false)
                commit('set_searchModalVisible', false)
            }
        },
        getOrderListByUserAndHotel: async ({commit,state,getters}) => {
            const res = await getUserHotelOrdersAPI({
                hotelId: state.currentHotelId,
                userId: getters.userId
            })
            if(res){
                commit('set_userHotelOrderList',res)
            }
        },
        getHotelById: async({commit, state}) => {
            const res = await getHotelByIdAPI({
                hotelId: state.currentHotelId
            })
            if(res){
                commit('set_currentHotelInfo', res)
            }
        },
        addOrder: async({ state, dispatch, commit }, data) => {
            //console.log(data)
            const res = await reserveHotelAPI(data)
            console.log(res)
            if(res){
                message.success('预订成功')
                commit('set_orderModalVisible', false)
                dispatch('getHotelById')
            }
        },
        getOrderMatchCoupons: async({ state, commit }, data) => {
            const res = await orderMatchCouponsAPI(data)
            if(res){
                commit('set_orderMatchCouponList', res)
            }
        },
        getHotelComments: async ({state, commit}) => {
            const res = await getCommentsAPI({
                hotelId: state.currentHotelId
            })
            if (res) {
                commit('set_hotelComments', res)
            }
        },
        deleteHotel: async ({ state, dispatch }, hotelId) => {
            const res = await deleteHotelAPI(hotelId)
            if(res) {
                dispatch('getHotelList')
                dispatch('getManagerHotelList')
                message.success('删除成功')
            }else{
                message.error('删除失败')
            }
        },
    }
}

export default hotel