import {
    addRoomAPI,
    addHotelAPI,
    editHotelAPI,
    updateHotelImgAPI,
    getManagerHotelsAPI,
    editRoomPriceAPI,
    editRoomTotalAPI,
    editRoomCurNumAPI,
    deleteRoomAPI,
} from '@/api/hotelManager'
import {
    getHotelByIdAPI,
} from "@/api/hotel";
import {
    execOrderAPI,
    checkOutAPI,
    getManagerOrdersAPI,
    setOrderExcepAPI,
    recoverOrderAPI,
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
        hotelDetail:{},
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
        manageRoomModalVisible: false,
        editHotelModalVisible: false,
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
        set_hotelDetail: function(state,data){
            state.hotelDetail = data
        },
        set_addHotelModalVisible: function(state, data) {
            state.addHotelModalVisible = data
        },
        set_addHotelParams: function(state, data) {
            state.addHotelParams = {
                ...state.addHotelParams,
                ...data,
            }
        },
        set_addRoomModalVisible: function(state, data) {
            state.addRoomModalVisible = data
        },
        set_manageRoomModalVisible: function(state, data) {
            state.manageRoomModalVisible = data
        },
        set_editHotelModalVisible: function(state, data) {
            state.editHotelModalVisible = data
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
        getHotelDetail: async ({state, commit}) => {
            const res = await getHotelByIdAPI({
                hotelId: state.activeHotelId
            })
            if (res){
                commit('set_hotelDetail', res)
            }
        },
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
        execOrder: async ({ state, dispatch }, orderId) => {
            const res = await execOrderAPI(orderId)
            if(res) {
                dispatch('getManagerOrderList')
                message.success('执行成功')
            }else{
                message.error('执行失败')
            }
        },
        recoverOrder:  async ({ state, dispatch }, orderid) => {
            // console.log(orderid)
            const res = await recoverOrderAPI(orderid)
            if(res) {
                dispatch('getManagerOrderList')
                message.success('操作成功')
            }else{
                message.error('操作失败')
            }
        },
        setOrderExcep:  async ({ state, dispatch }, orderid) => {
           // console.log(orderid)
            const res = await setOrderExcepAPI(orderid)
            if(res) {
                dispatch('getManagerOrderList')
                message.success('操作成功')
            }else{
                message.error('操作失败')
            }
        },
        addHotel: async({ state, dispatch, commit }) => {
            const res = await addHotelAPI(state.addHotelParams)
            if(res){
                dispatch('getHotelList')
                dispatch('getManagerHotelList')
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
                commit('set_addRoomModalVisible',false)
                dispatch('getHotelDetail')
                commit('set_manageRoomModalVisible', true)
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
        editHotel: async ({ dispatch, commit }, data) => {
            console.log(data)
            const res = await editHotelAPI(data)
            if(res){
                dispatch('getManagerHotelList')
                commit('set_editHotelModalVisible', false)
                message.success('修改成功')
            }else{
                message.error('修改失败')
            }
        },
        updateHotelImg: async ({commit}, data) => {
            const formData = new FormData();
            formData.append('file',data.img)
            const res = await updateHotelImgAPI(data.id, formData)
            if (res){
                message.success('上传成功')
            } else {
                message.error('上传失败')
            }
        },
        editRoomPrice: async ({dispatch}, data) => {
            const res = await editRoomPriceAPI(data.roomId,data.val)
            if (res) {
                dispatch('getHotelDetail')
                message.success('修改成功')
            } else {
                message.error('修改失败')
            }
        },
        editRoomTotal: async ({dispatch},data) => {
            const res = await editRoomTotalAPI(data.roomId,data.val)
            if (res) {
                dispatch('getHotelDetail')
                message.success('修改成功')
            } else {
                message.error('修改失败')
            }
        },
        editRoomCurNum: async ({dispatch}, data) => {
            const res = await editRoomCurNumAPI(data.roomId,data.val)
            if (res) {
                dispatch('getHotelDetail')
                message.success('修改成功')
            } else {
                message.error('修改失败')
            }
        },
        deleteRoom: async ({dispatch}, roomId) => {
            const res = await deleteRoomAPI(roomId)
            if (res) {
                dispatch('getHotelDetail')
                message.success('删除成功')
            } else {
                message.error('删除失败')
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