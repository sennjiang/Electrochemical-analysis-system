import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'


// 请求拦截器
axios.interceptors.request.use(config => {
  // 判断token是否存在，存在则携带token
  if (window.sessionStorage.getItem('tokenStr')) {
    config.headers.Authorization = window.sessionStorage.getItem('tokenStr')
  }
  return config
}, error => {
  console.log(error)
})

// 响应拦截器
axios.interceptors.response.use(success => {
  // 进入这里说明至少服务器已经访问到了

  if (success.status && success.status === 200) {
    // 业务逻辑错误
    // 判断响应码
    if (success.data.code === 500 || success.data.code === 401 || success.data.code === 403) {
      Message.error({ message: success.data.message })
      return
    }
    if (success.data.message) {
      Message.success({ message: success.data.message })
    }
  }
  return success.data
}, error => {
  // 进入这里说明服务器都没访问到

  if (error.response.status === 504 || error.response.status === 404) {
    Message.error({ message: '服务器挂了，呜呜呜' })
  } else if (error.response.status === 403) {
    Message.error({ message: '权限不足，请联系管理员' })
  } else if (error.response.status === 401) {
    Message.error({ message: '尚未登录，请登录' })
    router.replace('/')
  } else {
    if (error.response.data.message) {
      Message.error({ message: error.response.data.message })
    } else {
      Message.error({ message: '出现未知错误' })
    }
  }
})

const base = 'http://localhost:8080/Electrochemical_Analysis_System_war_exploded'

const querystring = require('querystring')
// 请求封装
export const postRequest = (url, params) => {
  return axios({
    method: 'post',
    url: `${base}${url}`,
    data: querystring.stringify(params)
  })
}

export const getRequest = (url, params) => {
  return axios({
    method: 'get',
    url: `${base}${url}` + '?' + querystring.stringify(params)
  })
}

export const putRequest = (url, params) => {
  return axios({
    method: 'put',
    url: `${base}${url}`,
    params: params
  })
}

export const deleteRequest = (url, params) => {
  return axios({
    method: 'delete',
    url: `${base}${url}` + '?' + querystring.stringify(params)
  })
}


//// create an axios instance
//const service = axios.create({
//  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
//  // withCredentials: true, // send cookies when cross-domain requests
//  timeout: 5000 // request timeout
//})
//
//// request interceptor
//service.interceptors.request.use(
//  config => {
//    // do something before request is sent
//
//    if (store.getters.token) {
//      // let each request carry token
//      // ['X-Token'] is a custom headers key
//      // please modify it according to the actual situation
//      config.headers['X-Token'] = getToken()
//    }
//    return config
//  },
//  error => {
//    // do something with request error
//    console.log(error) // for debug
//    return Promise.reject(error)
//  }
//)
//
//// response interceptor
//service.interceptors.response.use(
//  /**
//   * If you want to get http information such as headers or status
//   * Please return  response => response
//  */
//
//  /**
//   * Determine the request status by custom code
//   * Here is just an example
//   * You can also judge the status by HTTP Status Code
//   */
//  response => {
//    const res = response.data
//
//    // if the custom code is not 20000, it is judged as an error.
//    if (res.code !== 20000) {
//      Message({
//        message: res.message || 'Error',
//        type: 'error',
//        duration: 5 * 1000
//      })
//
//      // 50008: Illegal token; 50012: Other clients logged in; 50014: Token expired;
//      if (res.code === 50008 || res.code === 50012 || res.code === 50014) {
//        // to re-login
//        MessageBox.confirm('You have been logged out, you can cancel to stay on this page, or log in again', 'Confirm logout', {
//          confirmButtonText: 'Re-Login',
//          cancelButtonText: 'Cancel',
//          type: 'warning'
//        }).then(() => {
//          store.dispatch('user/resetToken').then(() => {
//            location.reload()
//          })
//        })
//      }
//      return Promise.reject(new Error(res.message || 'Error'))
//    } else {
//      return res
//    }
//  },
//  error => {
//    console.log('err' + error) // for debug
//    Message({
//      message: error.message,
//      type: 'error',
//      duration: 5 * 1000
//    })
//    return Promise.reject(error)
//  }
//)
//
// export default service

