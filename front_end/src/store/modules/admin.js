import {
    getManagerListAPI,
    addManagerAPI,
    deleteUserAPI,
    getHotelsAPI,
    deleteHotelAPI,
    setHotelManagerAPI,
    getUserListAPI,
    editUserInfoAPI,
} from '@/api/admin'
import{
    addHotelAPI,
}from '@/api/hotelManager'
import { message } from 'ant-design-vue'
import {changeConfirmLocale} from "ant-design-vue/lib/modal/locale";
import editUserInfoModal from "../../views/admin/components/editUserInfoModal";

const admin = {
    state: {
        hotelid:0,
        userid:0,
        managerList: [

        ],
        userList:[

        ],
        addManagerModalVisible: false,
        addManagerParams: {
            email:'',
            password:'',
            phonenumber:'',
            username:'',
            credit:'100'
        },
        editUserInfoParams: {
            password:'',
            userName:'',
            phoneNumber:'',
            credit:''
        },
        adminHotelList:{

        },
        addHotelModalVisible: false,
        setHotelManagerModalVisible:false,
        editUserInfoModalVisible:false,

    },
    mutations: {
        set_managerList: function(state, data) {
            state.managerList = data
        },

        set_userList: function(state, data) {
            state.userList = data
        },

        set_HotelList:function(state,data){
            state.adminHotelList=data
        },

        set_HotelId:function(state,data){
            state.hotelid=data
        },
        set_UserId:function(state,data){
            state.userid=data
        },


        set_editUserInfoModalVisible:function(state, data) {
             state.editUserInfoModalVisible = data
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
        set_editUserInfoParams: function(state,data){
            //console.log(state.editUserInfoParams)
            state.editUserInfoParams={
                ...state.editUserInfoParams,
                ...data,
            }
            //console.log(state.editUserInfoParams)
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
        setHotelManager:async({ state, commit, dispatch },obj) => {
            console.log(obj.hotelid,obj.managerid)
            const res = await setHotelManagerAPI(obj.hotelid,obj.managerid)
            //console.log(hotelid,managerid)
            if(res) {
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
        getUserList:async({commit})=>{
            const res= await getUserListAPI()
            if(res){
                commit('set_userList',res)
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
        editUserInfo:async({ state, commit, dispatch }) => {
            const res = await editUserInfoAPI(state.editUserInfoParams,state.editUserInfoParams.userid)
            console.log(state.editUserInfoParams)
            if(res) {
                commit('set_editUserInfoModalVisible', false)
                message.success('修改成功')
                dispatch('getUserList')

            }else{
                message.error('修改失败')
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