<template>
  <div class="container">
    <h1 class="style-h1-title">电化学分析系统</h1>
    <hr class="style-hr"/>
    <div class="style-form-wrapper">
      <h2 class="style-h2-forget">填写新密码</h2>
      <el-form style="text-align: center; margin-top: 50px;position: relative; top: 10%" ref="passwordForm"
               :rules="passwordFormRules" :model="passwordForm">
        <el-form-item prop="password1">
          <span>新的密码:&nbsp;&nbsp;&nbsp;&nbsp;</span>
          <el-input style="width: 300px;" type="password" v-model="passwordForm.password1"></el-input>
        </el-form-item>
        <br/>
        <br/>

        <el-form-item prop="password2">
          <span>密码确认:&nbsp;&nbsp;&nbsp;&nbsp;</span>
          <el-input style="width: 300px" v-model="passwordForm.password2" type="password"></el-input>
        </el-form-item>
        <br/>
        <br/>
        <br/>
        <el-row>
          <el-button type="primary" size="medium" class="style-form-button" :loading="loadState" @click="submitForm">确定
          </el-button>
        </el-row>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "modifyPasswordInfo",

  data() {
    //验证密码
    let validatePwd1 = (rule, value, callback) => {
      var reg = new RegExp(/[A-Za-z].*[0-9]|[0-9].*[A-Za-z]/);
      if (!reg.test(this.passwordForm.password1)) {
        callback(new Error("密码必须包含字母和数字!"));
        return false;
      } else {
        callback();
      }
    };
    // <!--二次验证密码-->
    let validatePwd2 = (rule, value, callback) => {
      var reg = new RegExp(/[A-Za-z].*[0-9]|[0-9].*[A-Za-z]/);
      if (value !== this.passwordForm.password1) {
        callback(new Error("两次输入密码不一致!"));
      } else if (!reg.test(this.passwordForm.password1)) {
        callback(new Error("密码必须包含字母和数字!"));
        return false;
      } else {
        callback();
      }
    };
    return {
      loadState: false,
      passwordForm: {
        password1: '',
        password2: '',
      },
      userInfo: {
        username: '',
      },

      passwordFormRules: {
        //  校验密码
        password1: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {validator: validatePwd1, trigger: 'blur'},
          {min: 6, max: 18, message: '长度在 5 到 12 个字符', trigger: 'blur'}
        ],
        password2: {validator: validatePwd2, trigger: 'blur'},
      }

    }
  },

  created() {
    this.userInfo = JSON.parse(window.sessionStorage.getItem('userInfo'))
  },

  methods: {
    async submitForm() {
      await this.$refs.passwordForm.validate(async valid => {
        if (valid) {
          this.postRequest('/modifyUser', {
            boundary: '0104', username: this.userInfo.username, password: this.passwordForm.password2
          }).then(resp => {
            this.loadState = false;
            this.$router.push('/')
          })
        }
      })
      this.loadState = !this.loadState;
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
  opacity: 80%;
}
</style>
