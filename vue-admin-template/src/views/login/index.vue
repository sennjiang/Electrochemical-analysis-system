<template>
  <div class="login-container">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on"
             label-position="left">

      <div class="title-container">
        <h3 class="title">Login Form</h3>
      </div>

      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user"/>
        </span>
        <el-input
          ref="username"
          v-model="loginForm.username"
          placeholder="Username"
          name="username"
          type="text"
          tabindex="1"
          auto-complete="on"
        />
      </el-form-item>

      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password"/>
        </span>
        <el-input
          :key="passwordType"
          ref="password"
          v-model="loginForm.password"
          :type="passwordType"
          placeholder="Password"
          name="password"
          tabindex="2"
          auto-complete="on"
          @keyup.enter.native="handleLogin"
        />
        <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'"/>
        </span>
      </el-form-item>

      <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:30px;"
                 @click.native.prevent="handleLogin">Login
      </el-button>

      <div class="tips">
        <span style="margin-right:20px;">username: admin</span>
        <span> password: any</span>
      </div>

    </el-form>
  </div>
</template>

<script>
import {validUsername} from '@/utils/validate'
import store from "@/store";
import Layout from "@/layout";
import router from "@/router";

export default {
  name: 'Login',
  data() {
    const validateUsername = (rule, value, callback) => {
      if (!validUsername(value)) {
        callback(new Error('Please enter the correct user name'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (value.length < 6) {
        callback(new Error('The password can not be less than 6 digits'))
      } else {
        callback()
      }
    }
    return {
      loginForm: {
        boundary: '0101',
        username: '20190001',
        password: '123456'
      },
      loginRules: {
        username: [{required: true, trigger: 'blur', validator: validateUsername}],
        password: [{required: true, trigger: 'blur', validator: validatePassword}]
      },
      loading: false,
      passwordType: 'password',
      redirect: undefined,
      currentRoles:[],
      map: undefined
    }
  },
  created() {
    console.log('初始化权限map')
    this.map = new Map()
    this.map.set('101', {
      path: '/superAdmin/adminManage',
      component: Layout,
      children: [
        {
          path: 'index',
          name: 'AdminManage',
          component: () => import('@/views/ea/superAdmin/adminManage/index'),
          meta: { title: '管理员管理', icon: 'user' }
        }
      ]
    })
    this.map.set('102', {
      path: '/superAdmin/operationRecord',
      component: Layout,
      children: [
        {
          path: 'index',
          name: 'OperationRecord',
          component: () => import('@/views/ea/superAdmin/operationRecord/index'),
          meta: { title: '操作记录管理', icon: 'user'}
        }
      ]
    })
    this.map.set('103', {
      path: '/superAdmin/systemBackup',
      component: Layout,
      children: [
        {
          path: 'index',
          name: 'SystemBackup',
          component: () => import('@/views/ea/superAdmin/systemBackup/index'),
          meta: {title: '系统备份', icon: 'user'}
        }
      ]
    })
    this.map.set('104', {
      path: '/superAdmin/systemRestore',
      component: Layout,
      children: [
        {
          path: 'index',
          name: 'SystemRestore',
          component: () => import('@/views/ea/superAdmin/systemRestore/index'),
          meta: {title: '系统还原', icon: 'user'}
        }
      ]
    })
    this.map.set('105', {
      path: '/superAdmin/algorithmAudit',
      component: Layout,
      children: [
        {
          path: 'index',
          name: 'AlgorithmAudit',
          component: () => import('@/views/ea/superAdmin/algorithmAudit/index'),
          meta: {title: '算法审核', icon: 'user'}
        }
      ]
    })
    this.map.set('106', {
      path: '/superAdmin/roleManage',
      component: Layout,
      children: [
        {
          path: 'index',
          name: 'RoleManage',
          component: () => import('@/views/ea/superAdmin/roleManage/index'),
          meta: {title: '角色管理', icon: 'user'}
        }
      ]
    })
    this.map.set('201', {
      path: '/admin/userManage',
      component: Layout,
      children: [
        {
          path: 'index',
          name: 'UserManage',
          component: () => import('@/views/ea/admin/userManage/index'),
          meta: { title: '用户管理', icon: 'user' }
        }
      ]
    })
    this.map.set('202', {
      path: '/admin/unfreezeAudit',
      component: Layout,
      children: [
        {
          path: 'index',
          name: 'UnfreezeAudit',
          component: () => import('@/views/ea/admin/unfreezeAudit/index'),
          meta: { title: '解冻审核', icon: 'user' }
        }
      ]
    })
    this.map.set('203', {
      path: '/admin/fileManage',
      component: Layout,
      children: [
        {
          path: 'index',
          name: 'FileManage',
          component: () => import('@/views/ea/admin/fileManage/index'),
          meta: { title: '文件管理', icon: 'user' }
        }
      ]
    })
    this.map.set('204', {
      path: '/admin/operatingRecord',
      component: Layout,
      children: [
        {
          path: 'index',
          name: 'OperatingRecord',
          component: () => import('@/views/ea/admin/operatingRecord/index'),
          meta: { title: '操作记录', icon: 'user' }
        }
      ]
    })
    this.map.set('205', {
      path: '/admin/algorithmManage',
      component: Layout,
      children: [
        {
          path: 'index',
          name: 'AlgorithmManage',
          component: () => import('@/views/ea/admin/algorithmManage/index'),
          meta: { title: '算法管理', icon: 'user' }
        }
      ]
    })
  },
  watch: {
    $route: {
      handler: function (route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  methods: {
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true

          // start 此处为方便调试, 直接进入了主页
          // const tokenStr = "20190001"
          // window.sessionStorage.setItem('tokenStr', tokenStr)
          // this.$store.commit('modifyCurrentUsername', "20190001")
          // this.$store.commit('modifyCurrentNickname', "20190001")
          // this.$store.commit('modifyCurrentStatus', "1")
          // this.$router.push({ path: this.redirect || '/' })
          // end

          this.postRequest('/login', this.loginForm).then(resp => {
            if (resp) {

              //权限添加


              // 将服务器返回的token存储到sessionStorage
              const tokenStr = resp.username
              window.sessionStorage.setItem('tokenStr', tokenStr)
              // 将loginUser存入session
              this.$store.commit('modifyCurrentUsername', resp.username)
              this.$store.commit('modifyCurrentNickname', resp.nickname)
              this.$store.commit('modifyCurrentStatus', resp.status)
              console.log('跳转')
              this.$router.push({path: this.redirect || '/'})
              console.log(window.sessionStorage.getItem(tokenStr))
            }
          })
          setTimeout(() => {
            this.loading = false
          }, 750)
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg: #283443;
$light_gray: #fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;

.login-container {
  min-height: 100%;
  width: 100%;
  background-image: url("../../assets/login/login-background.jpg");
  overflow: hidden;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}
</style>
