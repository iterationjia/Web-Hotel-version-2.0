import Vue from 'vue'
import Vuex from 'vuex'
import user from './modules/user'
import hotel from './modules/hotel'
import hotelManager from './modules/hotelManager'
import marketManager from './modules/marketManager'
import admin from './modules/admin'
import getters from './getters'
import {
  updateDatabaseAPI
} from '@/api/db'
import {message} from "ant-design-vue";
Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    hotel,
    user,
    hotelManager,
    marketManager,
    admin,
  },
  state: {
  },
  mutations: {
  },
  actions: {
    updateDatabase: async() => {
      const res = await updateDatabaseAPI()
      console.log(res)
      if (res){
        message.success('数据库更新成功')
      } else {
        message.error('数据库更新失败')
      }
    }
  },
  getters
})
