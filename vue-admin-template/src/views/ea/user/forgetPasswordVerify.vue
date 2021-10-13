<template>
  <div class="container">
    <h1 class="style-h1-title">电化学分析系统</h1>
    <hr class="style-hr"/>
    <div class="style-form-wrapper">
      <h2 class="style-h2-forget">邮箱验证</h2>
      <el-form style="text-align: center; margin-top: 50px;position: relative; top: 10%">

        <el-form-item>
          <span>邮箱:&nbsp;&nbsp;&nbsp;&nbsp;</span>
          <el-input style="width: 300px;"></el-input>
        </el-form-item>
        <br/>
        <br/>

        <el-form-item prop="smscode" class="code">
          <el-input class="style-code-input"  placeholder="请输入验证码"></el-input>
          <el-button type="primary" :disabled='isDisabled' @click="sendCode">发送验证码</el-button>
        </el-form-item>

        <br/>
        <br/>
        <br/>
        <el-row><el-button  type="primary" size="medium" class="style-form-button" :loading="loadState" @click="load()">确定</el-button></el-row>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "ForgetPasswordVerify",

  data() {
    return {
      loadState: false
    }
  },

  methods: {
    load() {
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
