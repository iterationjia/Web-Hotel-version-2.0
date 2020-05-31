import {
    addRoomAPI,
    addHotelAPI,
    getManagerHotelsAPI
} from '@/api/hotelManager'
import {
    getAllOrdersAPI,

    execOrderAPI,

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
        set_managerOrderListType: function(state, data){
            if (data=='scheduled'){
                state.managerOrderTypeList = this.getters.managerScheduledOrderList
            } else if (data=='executed'){
                state.managerOrderTypeList = this.getters.managerExecutedOrderList
            } else if (data=='error'){
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
                commit('set_managerOrderListType', 'scheduled')
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
//
        execOrder: async ({ state, dispatch }, orderId) => {
            const res = await execOrderAPI(orderId)
            if(res) {
                dispatch('getUserOrders')
                message.success('执行成功')
            }else{
                message.error('执行失败')
            }
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