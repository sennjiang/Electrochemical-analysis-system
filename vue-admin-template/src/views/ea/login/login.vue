<template>
  <div class="container">
    <label class="index-title bolt">电化学分析系统</label>
    <hr class="hr-line"/>
    <div class="login-wrapper">
      <div class="header">登录</div>
      <el-form class="form-wrapper" ref="loginForm" :rules="loginRules" :model="loginForm">
        <!--      用户名-->
        <el-form-item prop="username">
          <el-input type="text" name="username" placeholder="账号/邮箱" class="input-item" v-model="loginForm.username"/>
        </el-form-item>
        <!--      密码-->
        <el-form-item prop="password">
          <el-input type="password" name="password" placeholder="密码" class="input-item" v-model="loginForm.password"/>
        </el-form-item>

        <!--滑动验证-->
        <div class="jc-component__range">
          <div class="jc-range" :class="rangeStatus?'success':''" >
            <i @mousedown="rangeMove" :class="rangeStatus?successIcon:startIcon"></i>
            {{rangeStatus?successText:startText}}
          </div>

        </div>

        <div class="btn btn-login" @click="login">登录</div>
      </el-form>

      <div class="msg">
        没有账号?
        <!--<router-link to="/admin/userManage/register">立即注册</router-link>-->
        <a rel="noopener" @click="toRegister">立即注册</a>
        <!--<a @click="$router">立即注册</a>-->
      </div>
      <div>
        <div class="btn-forget-password">
          <a href="#" @click="forgetPassword()">忘记密码</a>
          &nbsp;
          <a @click="toUnfreeze">申请解冻</a>
        </div>
        <div>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
import store from "@/store";
import global from "@/views/global";

export default {
  name: "Login",

  // 滑动验证
  props: {
    // 成功之后的函数
    successFun: {
      type: Function,
    },
    //成功图标
    successIcon: {
      type: String,
      default: 'el-icon-success'
    },
    //成功文字
    successText: {
      type: String,
      default: '验证成功'
    },
    //开始的图标
    startIcon: {
      type: String,
      default: 'el-icon-d-arrow-right'
    },
    //开始的文字
    startText: {
      type: String,
      default: '拖动滑块到最右边'
    },
    //失败之后的函数
    errorFun: {
      type: Function
    },
    //或者用值来进行监听
    status: {
      type: String
    }
  },

  data() {
    return {

      //滑动验证
      disX : 0,
      rangeStatus: false,
      verifyStatus: false,

      loginForm: {
        boundary: '0101',
        username: '20190002',
        password: '123456'
      },
      // 校验规则
      loginRules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 4, max: 20, message: '长度在 6 到20 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 4, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur'}
        ]
      },
      loading: false,
      passwordType: 'password',
      redirect: undefined,
      currentRoles:[]
    }

  },

  methods: {
    //  登陆
    login() {
      this.$refs.loginForm.validate(async valid => {
        if (valid) {
          if (this.verifyStatus) {
            this.loading = true
            this.postRequest('/login', this.loginForm).then(resp => {
              if (resp.code === 200) {
                // 用户状态正常,状态码为1,正常登录
                if (resp.userInfo.status === 1) {
                  // 将服务器返回的token存储到sessionStorage
                  const tokenStr = resp.userInfo.username
                  window.sessionStorage.setItem('tokenStr', tokenStr)
                  window.sessionStorage.setItem('userInfo', JSON.stringify(resp.userInfo))
                  // 将userInfo存入session
                  this.$store.commit('modifyCurrentUsername', resp.userInfo.username)
                  this.$store.commit('modifyCurrentNickname', resp.userInfo.nickname)
                  this.$store.commit('modifyCurrentStatus', resp.userInfo.status)

                  sessionStorage.setItem('store', JSON.stringify(this.$store.state))

                  // 获得用户名后查找用户权限加载侧边栏
                  this.postRequest('/roles', { boundary : '1101', username : store.state.currentUsername }).then(resp => {
                    for(let i = 0; i < resp.rightIdList.length; i ++) {
                      let roleId = resp.rightIdList[i].rightId;
                      this.currentRoles.push(global.map.get(roleId.toString()))
                    }
                    this.$store.commit('modifyRouters', this.currentRoles)
                  })
                  this.$message.success('登录成功')
                  this.$router.push({path: this.redirect || '/'})

                }

                // 用户状态被冻结, 状态码为0
                if (resp.userInfo.status === 0) {
                  // 跳转到解冻申请页
                  this.$message.warning('账号异常, 请先解锁账号')
                  let tempUserKey = Math.round(Math.random()*99999999+10000000).toString()
                  window.sessionStorage.setItem('tokenStr', tempUserKey)
                  this.$router.push('/unfreezeApplicationVerify')
                }

                // 剩下被删除状态, 状态码为2
                if (resp.userInfo.status === 2) {
                  this.$message.error('用户不存在')
                }
              }
            })

            setTimeout(() => {
              this.loading = false
            }, 750)
          } else {
            console.log('error submit!!')
            return false
          }

          }

      })
    },

    // 滑动验证-start
    // 滑动验证文档 https://blog.csdn.net/qq_43487181/article/details/94143663?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522163585538016780261927285%2522%252C%2522scm%2522%253A%252220140713.130102334..%2522%257D&request_id=163585538016780261927285&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduend~default-2-94143663.pc_search_mgc_flag&utm_term=%E6%BB%91%E5%8A%A8%E9%AA%8C%E8%AF%81vue&spm=1018.2226.3001.4187
    // 改变验证状态
    changeStatus() {
      this.verifyStatus = true;
    },
    //滑块移动
    rangeMove(e){
      let ele = e.target;
      let startX = e.clientX;
      let eleWidth = ele.offsetWidth;
      let parentWidth =  ele.parentElement.offsetWidth;
      let MaxX = parentWidth - eleWidth;
      if(this.rangeStatus){//不运行
        return false;
      }
      document.onmousemove = (e) => {
        let endX = e.clientX;
        this.disX = endX - startX;
        if(this.disX<=0){
          this.disX = 0;
        }
        if(this.disX>=MaxX-eleWidth){//减去滑块的宽度,体验效果更好
          this.disX = MaxX;
        }
        ele.style.transition = '.1s all';
        ele.style.transform = 'translateX('+this.disX+'px)';
        e.preventDefault();
      }
      document.onmouseup = ()=> {
        if(this.disX !== MaxX){
          ele.style.transition = '.5s all';
          ele.style.transform = 'translateX(0)';
          //执行成功的函数
          this.errorFun && this.errorFun();
        }else{
          this.rangeStatus = true;
          if(this.status){
            this.$parent[this.status] = true;
          }
          //执行成功的函数
          this.changeStatus && this.changeStatus();
        }
        document.onmousemove = null;
        document.onmouseup = null;
      }
    },

    // to忘记密码页
    forgetPassword() {
      let tempUserKey = Math.round(Math.random()*99999999+10000000).toString()
      window.sessionStorage.setItem('tokenStr', tempUserKey)
      this.$router.push({path: '/forgetPasswordVerify'})
    },

    // to注册页
    toRegister() {
      let tempUserKey = Math.round(Math.random()*99999999+10000000).toString()
      window.sessionStorage.setItem('tokenStr', tempUserKey)
      this.$router.push({path: '/register'})
    },

    // to申请解冻页
    toUnfreeze() {
      let tempUserKey = Math.round(Math.random()*99999999+10000000).toString()
      window.sessionStorage.setItem('tokenStr', tempUserKey)
      this.$router.push({path: '/unfreezeApplicationVerify'})
    }
  }
}
</script>

<style lang="scss" scoped>
* {
  margin: 0;
  padding: 0;
}

html {
  height: 100%;
}

body {
  height: 100%;
}

.container {
  background-image: url("../../../assets/login/login-background.jpg");
  background-size: 100%;
  height: 100%;
  /*background-image: linear-gradient(to right, #fbc2eb, #a6c1ee);*/
}

.container .index-title {
  font-family: "Microsoft YaHei";
  font-style: oblique;
  font-size: 50px;
  /*字间距*/
  letter-spacing: 10px;
  color: #ffffff;
  /*text-shadow: 5px 5px 5px #ffaa4b;*/
  text-shadow: 1px 20px 20px #03a9f4;
  margin-top: 7%;
  margin-left: 4%;
}

.hr-line {
  background-color: white;
  height: 1px;
  border: none;
  margin-top: 2px;
}

.login-wrapper {
  background-color: #fff;
  width: 450px;
  height: 588px;
  border-radius: 15px;
  padding: 0 50px;
  position: relative;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}

.header {
  font-size: 38px;
  font-weight: bold;
  text-align: center;
  line-height: 200px;
}

.container .login-wrapper .form-wrapper {
  position: relative;
}

.container .login-wrapper .form-wrapper input:focus ~ label,
.container .login-wrapper .form-wrapper input:valid ~ label {
  top: -18px;
  left: 0;
  color: #03a9f4;
  font-size: 12px;
}

//.input-item {
//  display: block;
//  width: 100%;
//  margin-bottom: 20px;
//  border: 0;
//  padding: 10px;
//  border-bottom: 1px solid rgb(128, 125, 125);
//  font-size: 15px;
//  outline: none;
//}
//
//.input-item::placeholder {
//  text-transform: uppercase;
//}

.btn {
  text-align: center;
  padding: 10px;
  width: 100%;
  margin-top: 40px;
  background-image: linear-gradient(to right, #a6c1ee, #fbc2eb);
  color: #fff;
}

.msg {
  text-align: right;
  line-height: 88px;
  /**/
  /*background: #b9e769;*/
}

a {
  text-decoration-line: none;
  color: #abc1ee;
}

/*登录按钮*/
.btn-login {
  border-radius: 3px;
  color: #4cc9f0;
}

.btn-login:hover {
  cursor: pointer;
  background: #e3e3e3;
  color: white;
}

/*按钮荧光效果*/
.btn-login:hover {
  background-color: #4cc9f0;
  -webkit-box-shadow: 10px 10px 99px 6px rgba(76, 201, 240, 1);
  -moz-box-shadow: 10px 10px 99px 6px rgba(76, 201, 240, 1);
  box-shadow: 10px 10px 99px 6px rgba(76, 201, 240, 1);
  transition: all ease-in 0.3s;
}

/*忘记密码按钮*/
.btn-forget-password {
  text-align: right;
}

/*申请解冻按钮*/
.btn-unfreeze-application {
  color: #4cc9f0;
  cursor: pointer;
}

/*动画效果*/
.transition-box {
  margin-bottom: 10px;
  width: 200px;
  height: 100px;
  border-radius: 4px;
  background-color: #409EFF;
  text-align: center;
  color: #fff;
  padding: 40px 20px;
  box-sizing: border-box;
  margin-right: 20px;
}

// 滑动验证
@mixin jc-flex{
  display: flex;
  justify-content: center;
  align-items: center;
}
.jc-component__range{
  .jc-range{
    background-color: #e3e3e3;
    position: relative;
    transition: 1s all;
    user-select: none;
    color: #333;
    @include jc-flex;
    height: 45px; /*no*/
    &.success{
      background-color: #7AC23C;
      color: #fff;
      i{
        color: #7AC23C;
      }
    }
    i{
      position: absolute;
      left: 0;
      width: 60px;/*no*/
      height: 100%;
      color: #919191;
      background-color: #fff;
      border: 1px solid #bbb;
      cursor: pointer;
      @include jc-flex;
    }
  }
}
</style>
