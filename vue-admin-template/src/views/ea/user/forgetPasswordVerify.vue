<template>
  <div class="container">
    <h1 class="style-h1-title">电化学分析系统</h1>
    <hr class="style-hr"/>
    <div class="style-form-wrapper">
      <h2 class="style-h2-forget">邮箱验证</h2>
      <el-form style="text-align: center; margin-top: 50px;position: relative; top: 10%" ref="emailFormRef"
               :rules="emailRules" :model="emailForm">

        <el-form-item prop="email">
          <label style="font-size: large;">邮箱:&nbsp;&nbsp;&nbsp;&nbsp;</label>
          <el-input style="width: 300px;" v-model="emailForm.email"></el-input>
          <!--<span style="width: 300px; font-size: large" v-text="userInfo.email"></span>-->
        </el-form-item>
        <br/>
        <br/>

        <el-form-item prop="emailCode" class="code">
          <el-input class="style-code-input" placeholder="请输入验证码" v-model="emailForm.emailCode"></el-input>
          <el-button type="primary" :disabled='isDisabled' @click="sendEmailCode">{{ buttonText }}</el-button>
        </el-form-item>

        <br/>
        <br/>
        <br/>
        <el-row>
          <el-button type="primary" size="medium" class="style-form-button" :loading="loadState" @click="goToInfo">
            确定
          </el-button>
        </el-row>
      </el-form>
    </div>
  </div>
</template>

<script>
function isEmail(s) {
  return /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(s)
}

export default {
  name: "forgetPasswordVerify",

  data() {
    // 校验邮箱
    let validateEmail = (rule, value, callback) => {
      if (!isEmail(value)) {
        callback(new Error('邮箱格式错误'))
      } else if (this.existUser()) {
        callback()
      } else {
        callback(new Error('邮箱不存在'))
      }
    };
    // 校验邮箱验证码
    let validateEmailCode = (rule, value, callback) => {
      if (this.emailForm.emailCode !== this.emailForm.emailCodeResp) {
        callback(new Error('验证码错误'))
      } else {
        this.$message.success("验证码正确")
        callback()
      }
    };
    return {
      loadState: false,

      // 表单数据对象
      emailForm: {
        email: '',
        emailCode: '',
        emailCodeResp: ''
      },

      // 用户信息
      userInfo: {
        username: '',
        email: ''
      },
      // email表单验证对象
      emailRules: {
        //  校验email
        email: [
          {required: true, message: '邮箱不能为空', trigger: 'blur'},
          {validator: validateEmail, trigger: 'blur'}
        ],
        emailCode: [
          {required: true, message: '验证码不能为空', trigger: 'blur'},
          {validator: validateEmailCode, trigger: 'blur'}
        ]
      },

      buttonText: '发送验证码',
      isDisabled: false, // 是否禁止点击发送验证码按钮
      flag: true,

      //  日期
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
      }
    }
  },

  created() {
    this.userInfo = JSON.parse(window.sessionStorage.getItem('userInfo'))
  },

  methods: {
    load() {
      this.loadState = !this.loadState;
    },

    // <!--提交注册-->
    async submitForm() {
      await this.$refs.emailFormRef.validate(async valid => {
        if (valid) {
          this.postRequest('/modifyUser', {
            boundary: '0104', username: this.userInfo.username, email: this.emailForm.email
          }).then(resp => {
            this.loadState = false;
            this.$router.push('/')
          })
        }
      })
    },
    // <!--发送邮箱验证码-->
    async sendEmailCode() {
      // await this.$refs.emailFormRef.validate(valid => {
      //   if (valid) {
          let email = this.userInfo.email
          if (this.checkEmail(email)) {
            console.log(email)
            let time = 60
            this.buttonText = '已发送'
            this.isDisabled = true
            if (this.flag) {
              this.flag = false;
              let timer = setInterval(() => {
                time--;
                this.buttonText = time + ' 秒'
                if (time === 0) {
                  clearInterval(timer);
                  this.buttonText = '重新获取'
                  this.isDisabled = false
                  this.flag = true;
                }
              }, 1000)
              this.postRequest('/sendEmail', {boundary: '0107', email: email}).then(resp => {
                this.emailForm.emailCodeResp = resp.emailCode
              })
            }
          }
      //   }
      // })
    },

    // 是否存在该用户, 参数为email, true:存在, false:不存在
    async existUser() {
      // const {data: resp} = await this.$http.post('/existUserByEmail', this.emailForm.email)
      await this.postRequest('/existUserByEmail', {boundary: '0110', email: this.emailForm.email}).then(resp => {
        if (resp.code === 200) {
          // 该email不存在, 可以注册
          return false;
        } else {
          // 该email已存在, 可以注册
          return false;
        }
      })
    },

    // 验证邮箱
    checkEmail(str) {
      let re = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$/
      if (re.test(str)) {
        return true;
      } else {
        return false;
      }
    },
    async goToInfo() {
      await this.$refs.emailFormRef.validate(async valid => {
        if (valid) {
          this.$router.push({
            path: '/forgetPasswordInfo'
          })
        }
      });
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
  //background: #eff3f5;
}

.container {
  background-image: url("../../../assets/image/backgroud-green.jpg");
  background-size: 100%;
  height: 100%;
  width: 100%;
  //background: #d3dce6;
  //background-image: url("../../../assets/image/background1.jpg");
  /*background-image: linear-gradient(to right, #fbc2eb, #a6c1ee);*/
}

.style-hr {
  position: relative;
  top: 2%;
  border: 1px solid white;
}

.style-form-button {
  width: 200px;
  height: 40px;
}

.style-h2-forget {
  position: relative;
  top: 10%;
  margin-left: 60px;
  margin-bottom: 50px;
}

.style-h1-title {
  position: relative;
  left: 2%;
  top: 1%;
}

.style-form-wrapper {
  background-color: white;
  width: 600px;
  height: 490px;
  border-radius: 15px;
  padding: 0 50px;
  position: relative;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  border: 1px #1ab2ff;
  opacity: 90%;
}

.style-code-input {
  position: relative;
  width: 200px;
  left: 5%;
}

.code button {
  position: relative;
  left: 8%;
  width: 90px;
  height: 38px;
  text-align: center;
}
</style>

