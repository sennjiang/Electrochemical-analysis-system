<template>
  <div class="container">
    <div class="unfreezeWrapper">
      <br/>
      <h2 style="margin-left: 10%;">注销账户</h2>
      <div class="style-label-div-reason">
        <label>注销说明:</label>
        <!--</el-input>-->
        <!--<span v-text="unfreezeForm.applicationReason"></span>-->
        <br/>
        <div class="ui-textarea">
          <p>
            [特别说明] 注销电化学系统账号后,   您将无法登录, 同时,  也无法再使用本系统相关的服务.
            若您经过慎重考虑后仍决定注销本系统账号, 请你务必仔细<<电化学系统账号注销协议>>
            1.注销电化学系统账号后,   您将无法登录, 同时,  也无法再使用本系统相关的服务.
            系统将不会再对您在云端的数据进行保存, 请您务必提前进行备份, 对于您注销账号后, 将无法找回之前在云端存储的数据...
            注销电化学系统账号后,   您将无法登录, 同时,  也无法再使用本系统相关的服务.
            系统将不会再对您在云端的数据进行保存, 请您务必提前进行备份, 对于您注销账号后, 将无法找回之前在云端存储的数据
            注销电化学系统账号后,   您将无法登录, 同时,  也无法再使用本系统相关的服务.
            系统将不会再对您在云端的数据进行保存, 请您务必提前进行备份, 对于您注销账号后, 将无法找回之前在云端存储的数据
          </p>
          <br/>
        </div>
      </div>
      <div class="ui-button">
        <el-button type="primary" class="ui-button-submit" @click="deleteUSerByUsername">注销</el-button>
        <el-button plain class="ui-button-submit" @click="toLogin">返回</el-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "LogoutUserInfo",

  data() {
    return {
      unfreezeForm: {
        boundary: '',
        freezeId: '',
        username: '',
        nickname: '',
        freezeReason: '',
        freezeTime: '',
        email: '',
        handleStatus: '',
        refuseReason: '',
        applicationReason: ''
      },

      userInfo: {
        username: ''
      },

      // 表单验证对象
      Rules: {
        //  校验用户名
        // email: [
        //   {required: true, message: '邮箱不能为空', trigger: 'blur'},
        //   {validator: validateEmail, trigger: 'blur'}
        // ],
        emailCode: [
          {required: true, message: '验证码不能为空', trigger: 'blur'},
        ]
      },
    }
  },

  created() {
    this.userInfo = JSON.parse(window.sessionStorage.getItem('userInfo'))
  },

  methods: {
    getFreezeInfo () {
      this.postRequest('/getFreezeInfo', {boundary: '0111', email: this.unfreezeForm.email}).then(resp => {
        this.unfreezeForm = resp.freeze
        this.unfreezeForm.nickname = resp.nickname
      })
    },

    // 注销账户
    deleteUSerByUsername () {
      this.postRequest('/logoutUser', {boundary: '0105', username: this.userInfo.username}).then(resp => {
        window.sessionStorage.removeItem('tokenStr')
        this.$router.push('/login')
      })
    },

    // 保存申请
    async saveUnfreezeInfo() {
      await this.postRequest('/getFreezeInfo', {
        boundary: '0112',
        freezeId: this.unfreezeForm.freezeId,
        username: this.unfreezeForm.username,
        email: this.unfreezeForm.email,
        handleStatus: '0',
        applicationReason: this.unfreezeForm.applicationReason
      }).then(resp => {
        this.$message.success('已提交,等待审核')
        window.sessionStorage.removeItem('tokenStr')
        this.$router.push('/login')
      })
    },

    // 返回登录页
    async toLogin() {
      this.$router.push('/login')
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  background-image: url("../../../assets/image/backgroud-green.jpg");
  background-size: 100%;
  width: 100%;
  height: 100%;
}

// 中间大框
.unfreezeWrapper {
  //border: 1px solid #09aaff;
  border-radius: 7px;
  position: relative;
  left: 50%;
  transform: translateX(-50%);
  top: 10%;
  width: 50%;
  height: 70%;
  background-color: white;
  opacity: 80%;
}

.style-label-div {
  position: relative;
  width: 50%;
  height: 10%;
  margin-left: 10%;
  top: 1%;
}

.style-label-div-reason {
  position: relative;
  width: 50%;
  height: 50%;
  margin-left: 10%;
  top: 1%;
}

.ui-textarea {
  border: 2px solid #09aaff;
  margin-top: 2%;
  width: 100%;
  height: 100%;
  border-radius: 5px;
}

.ui-textarea:focus {
  border: 1px solid #09aaff;
  border-radius: 5px;
  outline: none !important;
}

.ui-button {
  position: relative;
  width: 50%;
  height: 10%;
  margin-left: 10%;
  top: 10%;
}

.ui-button-submit {
  width: 150px;
  left: 50%;
  //transform: translateX(-50%);
}
</style>
