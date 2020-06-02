import {
    getAllOrdersAPI,
    deleteOrderAPI,
} from '@/api/order'
import { message } from 'ant-design-vue'

import {
    creditSetAPI,
    getAccountByEmailAPI
} from "@/api/user";

const marketManager = {
    state: {
        orderList: [],
        userCredit: 0
    },
    mutations: {
        set_orderList: function(state, data) {
            state.orderList = data
        },
        set_userCredit: function (state,data) {
            state.userCredit = data
        }
    },
    actions: {
        getAllOrders: async({ state, commit}) => {
            const res = await getAllOrdersAPI()
            if(res){
                commit('set_orderList', res)
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
        creditSet: async({state}, data) => {
            //console.log(data);
            const res = await creditSetAPI(data);
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
    }
}
export default marketManager