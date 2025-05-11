import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'

Vue.use(ElementUI)

// 配置axios
const service = axios.create({
  //baseURL: 'http://192.168.1.112:8080',
  timeout: 10000
})

// 请求拦截器
service.interceptors.request.use(
  config => {
    // 排除OPTIONS预检请求
    if (config.method.toLowerCase() !== 'options') {
      const token = localStorage.getItem('token') || store.state.token
      if (token) {
        config.headers['auth'] = token
      } else {
        // 没有token时跳转到登录页，但先检查当前是否已在登录页
        // if (router.currentRoute.path !== '/login') {
        //   router.push('/login')
        // }
        // return Promise.reject(new Error('未登录'))
      }
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  response => {
    // 可以在这里处理通用的响应逻辑
    if (response.data && response.data.code === 401) {
      // 处理业务层面的未授权
      handleUnauthorized()
    }
    return response.data
  },
  error => {
    if (error.response) {
      switch (error.response.status) {
        case 401:
          handleUnauthorized()
          break
        case 403:
          // 处理无权限
          break
        case 500:
          // 处理服务器错误
          break
      }
    }
    return Promise.reject(error)
  }
)

function handleUnauthorized() {
  // 清除认证信息
  localStorage.removeItem('token')
  store.commit('SET_TOKEN', '')
  // 跳转到登录页
  if (router.currentRoute.path !== '/login') {
    router.replace('/login')
  }
}

Vue.prototype.$axios = service

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')