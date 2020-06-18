import {
    deleteOrderAPI,
} from '@/api/order'
import {
    creditSetAPI,
    lvSetAPI,
    getAccountByEmailAPI
} from "@/api/user";
import{
    getCouponListAPI,
    deleteCouponAPI,
}from'@/api/coupon';
import { message } from 'ant-design-vue'


const marketManager = {
    state: {
        orderList: [],
        userCredit: 0,
        userlv: 0,
        totalmoney: 0,
        couponList:[],
    },
    mutations: {
        set_orderList: function(state, data) {
            state.orderList = data
        },
        set_userCredit: function (state,data) {
            state.userCredit = data
        },
        set_userlv: function (state,data) {
            state.userlv = data
        },
        set_userTotalMoney : function (state,data) {
            state.totalmoney = data
        },
        set_couponList:function (state,data) {
            state.couponList = data
        },
    },
    actions: {
        getCouponList: async({ state, commit}) => {
            const res = await getCouponListAPI()
            if(res){
                commit('set_couponList', res)
            }
        },

        deleteOrder: async({state,dispatch}, data) => {
            //console.log(data)
            const res = await deleteOrderAPI(data)
            //console.log(res)
            if(res) {
                dispatch('getAllOrders')
                message.success('撤销成功')
            }else{
                message.error('撤销失败')
            }
        },

        deleteCoupon: async({state,dispatch}, data) => {
            const res = await deleteCouponAPI(data)
            if(res) {
                dispatch('getCouponList')
                message.success('删除成功')
            }else{
                message.error('删除失败')
            }
        },

        creditSet: async({state}, data) => {
            //console.log(data);
            const res = await creditSetAPI(data);
            if(res){
                message.success('充值成功')
            }
        },

        lvSet: async({state}, data) => {
            //console.log(data);
            const res = await lvSetAPI(data);
            if(res){
                message.success('充值成功')
            }
        },
        getCredit: async({ state, commit},data) => {
            const res = await getAccountByEmailAPI(data);
            if(res){
                commit('set_userCredit', res.credit);
            }
        },
        getlv: async({ state, commit},data) => {
            const res = await getAccountByEmailAPI(data);
            if(res){
                commit('set_userlv', res.lv);
            }
        },
        getTotalMoney: async({ state, commit},data) => {
            const res = await getAccountByEmailAPI(data);
            if(res){
                commit('set_userTotalMoney', res.totalmoney);
            }
        },
    }
}
export default marketManager