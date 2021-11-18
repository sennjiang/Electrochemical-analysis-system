<template>
  <div class="container">
    <div class="unfreezeWrapper">
      <br/>
      <h2 style="margin-left: 10%;">解冻申请</h2>
      <el-form style="text-align: center; margin-top: 50px;position: relative; top: 10%" ref="formRef" :rules="rules"
               :model="unfreezeForm">
      </el-form>
      <div class="style-label-div">
        <label>冻结原因:</label>
        <span v-text="unfreezeForm.freezeReason">异常登录</span>
      </div>
      <div class="style-label-div">
        <label>冻结时间:</label>
        <span v-text="unfreezeForm.freezeTime">2020-10-09</span>
      </div>
      <div class="style-label-div">
        <label>申请人:</label>
        <span v-text="unfreezeForm.nickname">小明</span>
      </div>
      <div class="style-label-div">
        <label>邮箱:</label>
        <span v-text="unfreezeForm.email">123@qq.com</span>
      </div>
      <div class="style-label-div-reason">
        <label>申请理由:</label>
        <!--<FormItem prop="name" :rules="{required: true, message: '请填写付款人姓名', trigger: 'blur'}">-->
        <!--    <el-input v-model="unfreezeForm.applicationReason"></el-input>-->
        <!--</FormItem>-->
        <el-input v-model="unfreezeForm.applicationReason"></el-input>

        <br/>
        <!--<textarea class="ui-textarea" v-model="unfreezeForm.applicationReason"></textarea>-->
      </div>
      <div class="ui-button">
        <el-button type="primary" class="ui-button-submit" @click="saveUnfreezeInfo">提交</el-button>
        <el-button plain class="ui-button-submit" @click="toLogin">返回</el-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "UnfreezeApplicationInfo",

  data() {
    return {
      unfreezeForm: {
        freezeId:'',
        username: '',
        nickname: '',
        freezeReason: '',
        freezeTime: '',
        email: '',
        handleStatus: '',
        refuseReason: '',
        applicationReason: ''
      },
      // email表单验证对象
      Rules: {
      },
    }
  },

  // 浏览器的后退 1.1
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

  created() {
    this.unfreezeForm.email = window.sessionStorage.getItem('currentEmail')
    this.getFreezeInfo()
  },

  methods: {

    // 浏览器后退 1.2
    goBack () {
      sessionStorage.clear();
      history.pushState(null, null, document.URL)
    },

    getFreezeInfo() {
      this.postRequest('/getFreezeInfo', {boundary: '0111', email: this.unfreezeForm.email}).then(resp => {
        this.unfreezeForm.freezeId = resp.freeze.freezeId
        this.unfreezeForm.username = resp.freeze.username
        this.unfreezeForm.freezeReason = resp.freeze.freezeReason
        this.unfreezeForm.freezeTime = resp.freeze.freezeTime
        this.unfreezeForm.nickname = resp.nickname
      })
    },

    // 保存申请
    saveUnfreezeInfo() {
      this.postRequest('/saveUnfreezeInfo', {
        boundary: '0112',
        freezeId: this.unfreezeForm.freezeId,
        username: this.unfreezeForm.username,
        email: this.unfreezeForm.email,
        handleStatus: 0,
        applicationReason: this.unfreezeForm.applicationReason
      }).then(resp => {
        this.$message.success('已提交,等待审核')
        window.sessionStorage.removeItem('tokenStr')
        this.$router.push('/login')
      })
    },

    // 返回登录页
    async toLogin() {
      window.sessionStorage.removeItem('tokenStr')
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
  height: 20%;
  margin-left: 10%;
  top: 1%;
}

.ui-textarea {
  margin-top: 2%;
  width: 100%;
  height: 100%;
  border-color: #97a8be;
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
