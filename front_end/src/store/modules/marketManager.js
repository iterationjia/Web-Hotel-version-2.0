import {
    getAllOrdersAPI,
    deleteOrderAPI,
} from '@/api/order'
import { message } from 'ant-design-vue'

const marketManager = {
    state: {
        orderList: [],

    },
    mutations: {
        set_orderList: function(state, data) {
            state.orderList = data
        },

    },
    actions: {
        getAllOrders: async({ state, commit}) => {
            const res = await getAllOrdersAPI()
            if(res){
                commit('set_orderList', res)
            }
        },
        deleteOrder: async(state, data) => {
            const res = await deleteOrderAPI(data)
            if(res) {
                //dispatch('this.getters.managerExceptionalOrderList')
                message.success('撤销成功')
            }else{
                message.error('撤销失败')
            }
        },
    }
}
export default marketManager