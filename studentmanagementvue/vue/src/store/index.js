import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: localStorage.getItem('token') || '',
    userInfo: null
  },
  getters: {
    isAuthenticated: state => !!state.token
  },
  mutations: {
    SET_TOKEN(state, token) {
      state.token = token
      localStorage.setItem('token', token)
    },
    SET_USER_INFO(state, userInfo) {
      state.userInfo = userInfo
    },
    CLEAR_USER_INFO(state) {
      state.token = ''
      state.userInfo = null
      localStorage.removeItem('token')
    }
  },
  actions: {
    login({ commit }, token) {
      commit('SET_TOKEN', token)
    },
    logout({ commit }) {
      commit('CLEAR_USER_INFO')
    }
  },
  modules: {
  }
})
