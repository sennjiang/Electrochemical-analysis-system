import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import app from './modules/app'
import settings from './modules/settings'
import user from './modules/user'
import Layout from "@/layout";

Vue.use(Vuex)

const store = new Vuex.Store({

  state: {
    currentUsername: undefined,
    currentNickname: undefined,
    currentStatus: undefined,
    routers: []
  },
  mutations: {
    modifyCurrentNickname (state, val) {
      state.currentNickname = val
    },
    modifyCurrentUsername (state, val) {
      state.currentUsername = val
    },
    modifyCurrentStatus (state, val) {
      state.currentStatus = val
    },
    modifyRouters (state, val) {
      state.routers = val
    }
  },
  modules: {
    app,
    settings,
    user
  },
  getters
})

export default store
