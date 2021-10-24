<template>
  <div class="container">
    <div class="unfreezeWrapper">
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
      <div class="style-label-div">
        <label>申请理由:</label>
        <span v-text="unfreezeForm.applicationReason"></span>
      </div>
      <div class="style-label-div">
        <el-button type="primary">提交</el-button>
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
    this.unfreezeForm.email = window.sessionStorage.getItem('currentEmail')
    this.getFreezeInfo()
  },

  methods: {
    getFreezeInfo () {
      this.postRequest('/getFreezeInfo', {boundary: '0111', email: this.unfreezeForm.email}).then(resp => {
        this.unfreezeForm = resp.freeze
        console.log('我执行啦')
        console.log(this.unfreezeForm.email)
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  width: 100%;
  height: 100%;
}

// 中间大框
.unfreezeWrapper {
  border: 1px solid #09aaff;
  border-radius: 5px;
  position: relative;
  left: 50%;
  transform: translateX(-50%);
  top: 20%;
  width: 50%;
  height: 50%;
}

.style-label-div {
  position: relative;
  width: 50%;
  height: 10%;
  margin-left: 10%;
  top: 1%;
}
</style>
