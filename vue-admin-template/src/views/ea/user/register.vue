<template>
  <div class="container">
    <div class="register-wrapper">
      <h1>电化学分析系统, 欢迎注册</h1>
      <hr class="line-style"/>
      <div class="register register-div">
        <p class="title">注册</p>
        <el-form
          :model="userForm"
          status-icon
          :rules="registerRules"
          ref="registerForm"
          label-width="0"
          class="demo-ruleForm"

        >

          <el-form-item prop="nickname">
            <span class="demonstration">昵称：</span>
            <el-input v-model="userForm.nickname" auto-complete="off" placeholder="请输入昵称 2-5位"
                      class="input-style-width"></el-input>
          </el-form-item>

          <el-form-item prop="pwd1">
            <span class="demonstration">密码：</span>
            <el-input v-model="userForm.pwd1" auto-complete="off" placeholder="6-18位字母+数字组合"
                      class="input-style-width" type="password"></el-input>
          </el-form-item>

          <el-form-item prop="pwd2">
            <span class="demonstration">确认：</span>
            <el-input v-model="userForm.pwd2" auto-complete="off" placeholder="密码确认"
                      class="input-style-width" type="password"></el-input>
          </el-form-item>

          <el-form-item prop="gender" style="text-align: left">
            <div style="margin-left: 15px">
              <span class="demonstration">性别：</span>
              <el-radio v-model="userForm.gender" label="1" border size="medium">男</el-radio>
              <el-radio v-model="userForm.gender" label="0" border size="medium">女</el-radio>
            </div>
          </el-form-item>

          <el-form-item prop="birth" style="text-align: left">
            <div class="block" style="margin-left: 15px">
              <span class="demonstration">生日：</span>
              <el-date-picker
                v-model="userForm.birth"
                type="date"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                placeholder="选择日期">
              </el-date-picker>
            </div>
          </el-form-item>

          <el-form-item prop="email">
            <span class="demonstration">邮箱：</span>
            <el-input v-model="userForm.email" auto-complete="off" placeholder="请输入邮箱"
                      class="input-style-width"></el-input>
          </el-form-item>


          <el-form-item prop="emailCode" class="code">
            <el-input class="style-code-input" v-model="userForm.emailCode" placeholder="请输入验证码"></el-input>
            <el-button type="primary" :disabled='isDisabled' @click="sendEmailCode">{{ buttonText }}</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('registerForm')" style="width:100%;">注册</el-button>
            <p class="login" @click="gotoLogin">已有账号？立即登录</p>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import store from "@/store";
import global from "@/views/global";
import {Message} from "element-ui";

function isEmail(s) {
  return /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(s)
}

export default {
  name: "Register",
  data() {
    // <!--验证nickname是否合法-->
    // let checkTel = (rule, value, callback) => {
    //   if (value === '') {
    //     callback(new Error('请输入昵称'))
    //   } else if (!this.checkMobile(value)) {
    //     callback(new Error('昵称不合法'))
    //   } else {
    //     callback()
    //   }
    // }
    //  <!--验证码是否为空-->
    // let checkemailCode = (rule, value, callback) => {
    //   if (value === '') {
    //     callback(new Error('请输入邮箱验证码'))
    //   } else {
    //     callback()
    //   }
    // }
    // <!--验证密码-->
    // let validatePwd1 = (rule, value, callback) => {
    //   if (value === "") {
    //     callback(new Error("请输入密码"))
    //   } else {
    //     if (this.ruleForm2.checkPass !== "") {
    //       this.$refs.ruleForm2.validateField("checkPass");
    //     }
    //     callback()
    //   }
    // }
    // <!--二次验证密码-->
    let validatePwd2 = (rule, value, callback) => {
      var reg = new RegExp(/[A-Za-z].*[0-9]|[0-9].*[A-Za-z]/);
      if (value !== this.userForm.pwd1) {
        callback(new Error("两次输入密码不一致!"));
      } else if (!reg.test(this.userForm.pwd1)) {
        callback(new Error("密码必须包含字母和数字!"));
        return false;
      } else {
        callback();
      }
    };
    // 校验邮箱
    let validateEmail = (rule, value, callback) => {
      if (!isEmail(value)) {
        callback(new Error('邮箱格式错误'))
      } else if (!this.existUser()) {
        callback(new Error('邮箱已存在'))
      } else {
        callback()
      }
    };
    // 校验邮箱验证码
    let validateEmailCode = (rule, value, callback) => {
      if (this.userForm.emailCode !== this.userForm.emailCodeResp) {
        callback(new Error('验证码错误'))
      } else {
        // this.$message.success("验证码正确")
        callback()
      }
    };
    return {
      // 注册信息核验规则
      registerRules: {
        //  校验用户名
        nickname: [
          // trigger:blur-触发方式，blur失去焦点，change数据改变
          {required: true, message: '请输入昵称', trigger: 'blur'},
          // min: 3, max: 5, message: '长度在3到5个字符', trigger: 'blur'
          {min: 1, max: 8, message: '长度在1到8个字符', trigger: 'blur'}
        ],
        //  校验密码
        pwd1: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {validator: validatePwd2, trigger: 'blur'},
          {min: 6, max: 18, message: '长度在 5 到 12 个字符', trigger: 'blur'}
        ],
        pwd2: {validator: validatePwd2, trigger: 'blur'},
        email: [
          {required: true, message: '邮箱不能为空', trigger: 'blur'},
          {validator: validateEmail, trigger: 'blur'}
        ],
        emailCode: [
          {required: true, message: '验证码不能为空', trigger: 'blur'},
          {validator: validateEmailCode, trigger: 'blur'}
        ]

      },

      userForm: {
        boundary: '0106',
        nickname: "",
        pwd1: "",
        pwd2: "",
        gender: "",
        birth: "",
        email: '',
        emailCode: "",
        emailCodeResp: ''
      },
      // rules2: {
      //   pass: [{validator: validatePass, trigger: 'change'}],
      //   checkPass: [{validator: validatePass2, trigger: 'change'}],
      //   tel: [{validator: checkTel, trigger: 'change'}],
      //   emailCode: [{validator: checkemailCode, trigger: 'change'}],
      // },
      buttonText: '发送验证码',
      isDisabled: false, // 是否禁止点击发送验证码按钮
      flag: true,

      //  日期
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
      },
      value1: '',
      value2: '',

    }
  },
  // 浏览器的后退情况
  mounted () {
    if (window.history && window.history.pushState) {
      // 向历史记录中插入了当前页
      history.pushState(null, null, document.URL);
      window.addEventListener('popstate', this.goBack, false);
    }
  },
  destroyed () {
    window.removeEventListener('popstate', this.goBack, false);
  },
  methods: {

    // 浏览器后退的场景
    goBack () {
      sessionStorage.clear();
      window.history.back();
    },

    // 是否存在该用户, 参数为email, true:存在, false:不存在
    async existUser() {
      // const {data: resp} = await this.$http.post('/existUserByEmail', this.userForm.email)
      await this.postRequest('/existUserByEmail', {boundary: '0110', email: this.userForm.email}).then(resp => {
        if (resp.code === 200) {
          // 该email不存在, 可以注册
          // this.$message.success("可以注册")
          return true;
        } else {
          // 该email已存在, 不可以注册
          this.$message.error("用户已存在")
          return false;
        }
      })
    },

    // <!--发送邮箱验证码-->
    sendEmailCode() {
      let email = this.userForm.email
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
            this.userForm.emailCodeResp = resp.emailCode
          })
        }
      }
    },
    // <!--提交注册-->
    async submitForm(formName) {
      this.$refs[formName].validate(async valid => {
        if (valid) {
      await this.postRequest('/addUser', this.userForm).then(resp => {
        if (resp.code == 200) {
          this.$message.success('注册成功')
          window.sessionStorage.removeItem('tokenStr')
          this.$router.push('/login')
        } else {
          console.log("注册失败")
        }
      })
      setTimeout(() => {
      }, 400);
        } else {
          return false;
        }
      })
    },
    // <!--进入登录页-->
    gotoLogin() {
      this.$router.push({
        path: "/login"
      });
    },
    // 验证邮箱
    checkEmail(str) {
      let re = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$/
      if (re.test(str)) {
        return true;
      } else {
        return false;
      }
    }
  }
};
</script>

<style scoped>
.container {
  background-image: url("../../../assets/image/backgroud-tree.jpg");
  background-size: 100%;
  height: 100%;
  /*background-image: linear-gradient(to right, #fbc2eb, #a6c1ee);*/
}

/*水平线*/
.line-style {
  box-shadow: #1ab2ff;
}

.input-style-width {
  width: 300px;
}

.input-birth-width {
  width: 275px;
}

.loading-wrapper {
  position: fixed;
  top: 0;
  right: 0;
  left: 0;
  bottom: 0;
  background: #aedff8;
  display: flex;
  align-items: center;
  justify-content: center;
}

.register-div {
  border: 1px solid #f2f2f2;
  bottom: 5%;
}

.register-wrapper img {
  position: absolute;
  z-index: 1;
}

.register-wrapper {
  position: fixed;
  top: 0;
  right: 0;
  left: 0;
  bottom: 0;
}

.register {
  max-width: 450px;
  margin: 60px auto;
  background: #fff;
  padding: 20px 40px;
  border-radius: 10px;
  position: relative;
  z-index: 9;
}

.title {
  font-size: 26px;
  line-height: 50px;
  font-weight: bold;
  margin: 10px;
  text-align: center;
}

.el-form-item {
  text-align: center;
}

.login {
  margin-top: 10px;
  font-size: 14px;
  line-height: 22px;
  color: #1ab2ff;
  cursor: pointer;
  text-align: left;
  text-indent: 8px;
  width: 160px;
}

.login:hover {
  color: #2c2fd6;
}

.code >>> .el-form-item__content {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.style-code-input {
  width: 200px;
  margin-left: 55px;
}

.code button {
  margin-left: 20px;
  width: 100px;
  text-align: center;
}

.el-button--primary:focus {
  background: #409EFF;
  border-color: #409EFF;
  color: #fff;
}
</style>

