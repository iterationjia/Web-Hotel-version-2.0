import {
    getManagerListAPI,
    addManagerAPI,
    deleteUserAPI,
    getHotelsAPI,
    deleteHotelAPI,
    setHotelManagerAPI,
    addCommentTableAPI,
} from '@/api/admin'
import{
    addHotelAPI,
}from '@/api/hotelManager'
import { message } from 'ant-design-vue'
import {changeConfirmLocale} from "ant-design-vue/lib/modal/locale";

const admin = {
    state: {
        HotelId: 0 ,
        managerList: [

        ],
        addManagerModalVisible: false,
        addManagerParams: {
            email:'',
            password:'',
            phonenumber:'',
            username:'',
            credit:'100'
        },
        adminHotelList:{

        },
        addHotelModalVisible: false,
        setHotelManagerModalVisible:false,

    },
    mutations: {
        set_managerList: function(state, data) {
            state.managerList = data
        },
        set_HotelList:function(state,data){
            state.adminHotelList=data
        },
        set_HotelId: function(state, data) {
            state.HotelId = data
            //console.log(state.HotelId)
        },
        set_addManagerModalVisible: function(state, data) {
            state.addManagerModalVisible = data
        },
        set_setHotelManagerModalVisible: function(state, data) {
            state.setHotelManagerModalVisible = data

        },
        set_addManagerParams: function(state, data) {
            state.addManagerParams = {
                ...state.addManagerParams,
                ...data,
            }
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
    },
    actions: {
        setHotelManager:async({ state, commit, dispatch },hotelid,managerid) => {
            const res = await setHotelManagerAPI(hotelid,managerid)
            if(res) {
                commit('set_setHotelManagerVisible', false)
                message.success('设置成功')
                dispatch('getHotelList')
            }else{
                message.error('设置失败')
            }
        },
        getManagerList: async({ commit }) => {
            const res = await getManagerListAPI()
            if(res){
                commit('set_managerList', res)
            }
        },
        //
        getHotelList: async({ state, commit}) => {
            const res = await getHotelsAPI()
           // console.log(res)
            if(res){

                commit('set_HotelList', res)
            }
        },
        // addHotel: async({ state, dispatch, commit }) => {
        //     const res = await addHotelAPI(state.addHotelParams)
        //     //console.log(res)
        //     if(res){
        //         commit('set_addHotelParams', {
        //             name: '',
        //             address: '',
        //             bizRegion:'XiDan',
        //             hotelStar:'',
        //             rate: 0,
        //             description:'',
        //             phoneNum:'',
        //             managerId:'',
        //         })
        //         commit('set_addHotelModalVisible', false)
        //         message.success('添加成功')
        //         dispatch('getHotelList')
        //     }else{
        //         message.error('添加失败')
        //     }
        // },
        deleteUser: async ({ state, dispatch }, userId) => {
            const res = await deleteUserAPI(userId)
            if(res) {
                dispatch('getManagerList')
                message.success('删除成功')
            }else{
                message.error('删除失败')
            }
        },
        deleteHotel: async ({ state, dispatch }, hotelId) => {
            const res = await deleteHotelAPI(hotelId)
            if(res) {
                dispatch('getHotelList')
                message.success('删除成功')
            }else{
                message.error('删除失败')
            }
        },
        //
        addCommentTable :async () => {
            const res = await addCommentTableAPI()
            if (res){
                message.success('添加成功')
            }else{
                message.error('添加失败')
            }
        },
        addManager: async({ state, commit, dispatch }) => {
            const res = await addManagerAPI(state.addManagerParams)
            if(res) {
                commit('set_addManagerParams',{
                    email:'',
                    password:'',
                    phoneNumber:'',
                    userName:'',
                    credit:'100'
                })
                commit('set_addManagerModalVisible', false)
                message.success('添加成功')
                dispatch('getManagerList')
            }else{
                message.error('添加失败')
            }
        }
    }
}
export default admin