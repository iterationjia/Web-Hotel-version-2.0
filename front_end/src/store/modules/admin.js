import {
    getManagerListAPI,
    addManagerAPI,
    deleteUserAPI,
    getUserListAPI,
    editUserInfoAPI,
    getVipListAPI,
} from '@/api/admin'
import {
    getHotelsAPI,
    setHotelManagerAPI,
} from '@/api/hotel'
import { message } from 'ant-design-vue'

const admin = {
    state: {
        hotelid:0,
        userid:0,
        managerList: [

        ],
        userList:[

        ],
        vipList:[

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

        set_vipList:function(state,data){
            state.vipList=data
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
            state.editUserInfoParams={
                ...state.editUserInfoParams,
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
        setHotelManager:async({ state, commit, dispatch },obj) => {
            const res = await setHotelManagerAPI(obj.hotelid,obj.managerid)
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
        getUserList:async({state, commit})=>{
            const res= await getUserListAPI()
            if(res){
                console.log(res)
                commit('set_userList',res)
            }
        },
        getHotelList: async({ state, commit}) => {
            const res = await getHotelsAPI()
            if(res){
                commit('set_HotelList', res)
            }
        },
        getVipList:async({state,commit})=>{
            const res= await getVipListAPI()
            if(res){
                console.log(res)
                commit('set_vipList',res)
            }

        },
        deleteUser: async ({ state, dispatch }, userId) => {
            const res = await deleteUserAPI(userId)
            if(res) {
                dispatch('getUserList')
                message.success('删除成功')
            }else{
                message.error('删除失败')
            }
        },

        editUserInfo:async({ state, commit, dispatch }) => {
            const res = await editUserInfoAPI(state.editUserInfoParams,state.userid)
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
                dispatch('getUserList')
            }else{
                message.error('添加失败')
            }
        }
    }
}
export default admin