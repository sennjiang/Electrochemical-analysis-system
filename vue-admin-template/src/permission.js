import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar（加载进度条控件）
import 'nprogress/nprogress.css' // progress bar style （进度条样式）
import { getToken } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'

NProgress.configure({ showSpinner: false }) // NProgress配置

const whiteList = ['/login'] // no redirect whitelist （白名单）

router.beforeEach(async(to, from, next) => {
  // start progress bar
  NProgress.start()

  // set page title
  document.title = getPageTitle(to.meta.title)

  // determine whether the user has logged in
  const hasToken = getToken()

  if (hasToken) {
    if (to.path === '/login') {
      // if is logged in, redirect to the home page
      next({ path: '/' })
      NProgress.done()
    } else {
      const hasGetUserInfo = store.getters.name
      if (hasGetUserInfo) {
        next()
      } else {
        try {
          // 尝试重新获取用户信息
          await store.dispatch('user/getInfo')

          next()
        } catch (error) {
          // remove token and go to login page to re-login
          await store.dispatch('user/resetToken')
          Message.error(error || 'Has Error')
          next(`/login?redirect=${to.path}`)
          NProgress.done()
        }
      }
    }
  } else {
    /* has no token*/

    // 判断是否为白名单路径
    if (whiteList.indexOf(to.path) !== -1) {
      // in the free login whitelist, go directly
      next()
    } else {
      // other pages that do not have permission to access are redirected to the login page.
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  // finish progress bar （进度条结束加载）
  NProgress.done()
})