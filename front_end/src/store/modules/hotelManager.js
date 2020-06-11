import {
    addRoomAPI,
    addHotelAPI,
    getManagerHotelsAPI,

} from '@/api/hotelManager'
import {
    changeHotelTotalMoneyAPI,
}from '@/api/hotel'
import {
    deleteHotelAPI
} from "@/api/admin";
import {
    getAllOrdersAPI,
    deleteOrderAPI,
    execOrderAPI,
    checkOutAPI,
    getManagerOrdersAPI,

} from '@/api/order'
import {
    hotelAllCouponsAPI,
    hotelTargetMoneyAPI,
} from '@/api/coupon'
import { message } from 'ant-design-vue'

const hotelManager = {
    state: {
        managerHotelList: [],
        managerOrderList: [],
        managerOrderTypeList: [],
        managerOrderListType: "",
        addHotelParams: {
            name: '',
            address: '',
            bizRegion:'XiDan',
            hotelStar:'',
            rate: 0,
            description:'',
            phoneNum:'',
            managerId:'',
        },
        addHotelModalVisible: false,
        addRoomParams: {
            roomType: '',
            hotelId: '',
            price: '',
            total: 0,
            curNum: 0,
        },
        addRoomModalVisible: false,
        couponVisible: false,
        addCouponVisible: false,

        execOrderVisible:false,

        activeHotelId: 0,
        couponList: [],
        orderDetailVisible: false,
    },
    mutations: {
        set_managerOrderList: function(state, data) {
            state.managerOrderList = data
        },
        set_managerOrderListType: function(state,data){
            state.managerOrderListType = data
        },
        set_managerOrderTypeList: function(state){
            if (state.managerOrderListType=='scheduled'){
                state.managerOrderTypeList = this.getters.managerScheduledOrderList
            } else if (state.managerOrderListType=='executed'){
                state.managerOrderTypeList = this.getters.managerExecutedOrderList
            } else if (state.managerOrderListType=='error'){
                state.managerOrderTypeList = this.getters.managerErrorOrderList
            }
        },
        set_managerHotelList: function(state, data){
            state.managerHotelList = data
        },
        set_addHotelModalVisible: function(state, data) {
            state.addHotelModalVisible = data
        },
        //work
        set_execOrderVisible:function(state,data){
            state.execOrderVisible=data
        },
//
        set_addHotelParams: function(state, data) {
            state.addHotelParams = {
                ...state.addHotelParams,
                ...data,
            }
        },
        set_addRoomModalVisible: function(state, data) {
            state.addRoomModalVisible = data
        },
        set_addRoomParams: function(state, data) {
            state.addRoomParams = {
                ...state.addRoomParams,
                ...data,
            }
        },
        set_couponVisible: function(state, data) {
            state.couponVisible = data
        },
        set_activeHotelId: function(state, data) {
            state.activeHotelId = data
        },
        set_couponList: function(state, data) {
            state.couponList = data
        },
        set_addCouponVisible: function(state, data) {
            state.addCouponVisible =data
        },
        set_orderDetailVisible: function (state, data) {
            state.orderDetailVisible = data
        }
    },
    actions: {
        // getAllOrders: async({ state, commit }) => {
        //     const res = await getAllOrdersAPI()
        //     if(res){
        //         commit('set_orderList', res)
        //     }
        // },
        getManagerOrderList: async({ state, commit, getters}) => {
            const res = await getManagerOrdersAPI({
                managerId: getters.userId
            })
            if(res){
                commit('set_managerOrderList', res)
                // commit('set_managerOrderTypeList', state.managerOrderListType)
                commit('set_managerOrderTypeList')
            }
        },
        getManagerHotelList: async({ state, commit, getters }) => {
            const res = await getManagerHotelsAPI({
                managerId: getters.userId
            })
            if(res){
                commit('set_managerHotelList', res)
            }
        },
        checkOutOrder: async ({state, dispatch},data) => {
            const res = await checkOutAPI(data)
            if(res){
                dispatch('getManagerOrderList')
                message.success('退房成功')
            } else{
                message.error('退房失败')
            }
        },
        deleteHotelByManager: async ({ state, dispatch }, hotelId) => {
            const res = await deleteHotelAPI(hotelId)
            if(res) {
                dispatch('getManagerHotelList')
                message.success('删除成功')
            }else{
                message.error('删除失败')
            }
        },
        deleteOrderByManager: async ({dispatch}, orderId) => {
            const res = await deleteOrderAPI(orderId)
            if(res) {
                dispatch('getManagerOrderList')
                message.success('执行成功')
            } else {
                message.error('执行失败')
            }
        },
//
        execOrder: async ({ state, dispatch }, orderId) => {
            const res = await execOrderAPI(orderId)
            if(res) {
                dispatch('getManagerOrderList')
                message.success('执行成功')
            }else{
                message.error('执行失败')
            }
        },

        changeHotelTotalMoney: async ({ state, dispatch }, obj) => {
               const res = await changeHotelTotalMoneyAPI(obj.hotelId,obj.price)

},
//
        addHotel: async({ state, dispatch, commit }) => {
            const res = await addHotelAPI(state.addHotelParams)
            if(res){
                dispatch('getHotelList')
                commit('set_addHotelParams', {
                    name: '',
                    address: '',
                    bizRegion:'XiDan',
                    hotelStar:'',
                    rate: 0,
                    description:'',
                    phoneNum:'',
                    managerId:'',
                })
                commit('set_addHotelModalVisible', false)
                message.success('添加成功')
            }else{
                message.error('添加失败')
            }
        },
        addRoom: async({ state, dispatch, commit }) => {
            const res = await addRoomAPI(state.addRoomParams)
            if(res){
                commit('set_addRoomModalVisible', false)
                commit('set_addRoomParams', {
                    roomType: '',
                    hotelId: '',
                    price: '',
                    total: 0,
                    curNum: 0,
                })
                message.success('添加成功')
            }else{
                message.error('添加失败')
            }
        },
        getHotelCoupon: async({ state, commit }) => {
            const res = await hotelAllCouponsAPI(state.activeHotelId)
            if(res) {
                // 获取到酒店策略之后的操作（将获取到的数组赋值给couponList）
                commit('set_couponList', res)
            }
        },
        addHotelCoupon: async({ commit, dispatch }, data) => {
            const res = await hotelTargetMoneyAPI(data)
            if(res){
                // 添加成功后的操作（提示文案、modal框显示与关闭，调用优惠列表策略等）
                message.success('添加成功')
                commit('set_addCouponVisible', false)
                commit('set_couponVisible', true)
                dispatch('getHotelCoupon')
            }else{
                // 添加失败后的操作
                message.error('添加失败')
            }
        }
    }
}
export default hotelManager