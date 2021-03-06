import Vue from 'vue'
import router from '@/router'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'
import { message } from 'ant-design-vue'
import {
    loginAPI,
    registerAPI,
    getUserInfoAPI,
    updateUserInfoAPI,
    updateAvatarAPI,
} from '@/api/user'

import {
    getUserOrdersAPI,
    cancelOrderAPI,
    updateUserOrderCommentAPI
} from '@/api/order'

const getDefaultState = () => {
    return {
        userId: '',
        userInfo: {

        },
        userOrderList: [

        ],
        userOrderTypeList: [],
        orderDetailVisible: false,
        showCommentVisible: false,
        commentVisible: false,
    }
}

const user = {
    state : getDefaultState(),


    mutations: {
        reset_state: function(state) {
            state.token = '',
            state.userId = '',
            state.userInfo = {
                
            },
            state.userOrderList = []
        },
        set_token: function(state, token){
            state.token = token
        },
        set_email: (state, data) => {
            state.email = data
        },
        set_userId: (state, data) => {
            state.userId = data
        },
        set_userInfo: (state, data) => {
            state.userInfo = {
                ...state.userInfo,
                ...data
            }
        },
        set_userOrderList: (state, data) => {
            state.userOrderList = data
        },
        set_userOrderListType: function(state, data){
            if (data=='scheduled'){
                state.userOrderTypeList = this.getters.userScheduledOrderList
            } else if (data=='executed'){
                state.userOrderTypeList = this.getters.userExecutedOrderList
            } else if (data=='error'){
                state.userOrderTypeList = this.getters.userErrorOrderList
            }
        },
        set_orderDetailVisible: function (state, data) {
            state.orderDetailVisible = data
        },
        set_showCommentVisible: function (state, data) {
            state.showCommentVisible = data
        },
        set_commentVisible: function (state, data) {
            state.commentVisible = data
        },
    },

    actions: {
        login: async ({ state, dispatch, commit }, userData) => {
            const res = await loginAPI(userData)
            console.log(res)
            if(res){
                setToken(res)
                commit('set_userId', res.id)
                dispatch('getUserInfo')
                router.push('/')
            }
        },
        register: async({ commit }, data) => {
            const res = await registerAPI(data)
            if(res){
                message.success('注册成功')
            }
        },
        getUserInfo({ state, commit }) {
            return new Promise((resolve, reject) => {
              getUserInfoAPI(state.userId).then(response => {
                const data = response
                  setToken(data)
                if (!data) {
                  reject('登录已过期，请重新登录')
                }
                commit('set_userInfo', data)
                commit('set_userId', data.id)
                resolve(data)
              }).catch(error => {
                reject(error)
              })
            })
        },
        updateUserInfo: async({ state, dispatch }, data) => {
            const params = {
                id: state.userId,
                ...data,
            }
            const res = await updateUserInfoAPI(params)
            if(res){
                 message.success('修改成功')
                dispatch('getUserInfo')
            }
        },
        updateUserAvatar: async({state, dispatch}, data) => {
            const formData = new FormData();
            formData.append('file',data)
            const res = await updateAvatarAPI(state.userId, formData)
            if(res){
                message.success('上传成功')
                dispatch('getUserInfo')
            } else {
                message.error('上传失败')
            }
        },

        //评论
        updateUserOrderComment: async({state},data) =>{
            const res = await updateUserOrderCommentAPI(data)
            if(res){
                message.success('评论成功')
            }
        },
        getUserOrders: async({ state, commit }) => {
            const data = {
                userId: Number(state.userId)
            }
            const res = await getUserOrdersAPI(data)
            if(res){
                commit('set_userOrderList', res)
                commit('set_userOrderListType', 'scheduled')
                //console.log(state.userOrderList)
            }
        },
        cancelOrder: async({ state, dispatch }, orderId) => {
            const res = await cancelOrderAPI(orderId)
            if(res) {
                dispatch('getUserOrders')
                message.success('撤销成功')
            }else{
                message.error('撤销失败')
            }
        },
        logout: async({ commit }) => {
            removeToken()
            resetRouter()
            commit('reset_state')
        },
          // remove token
        resetToken({ commit }) {
            return new Promise(resolve => {
                removeToken() // must remove  token  first
                commit('reset_state')
                resolve()
            })
        },
    }
}

export default user