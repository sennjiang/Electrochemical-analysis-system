<template>
  <div class="container">
    <label class="index-title bolt">电化学分析系统</label>
    <hr class="hr-line"/>
    <div class="login-wrapper">
      <div class="header">登录</div>
      <el-form class="form-wrapper" ref="loginForm" :rules="loginRules" :model="loginForm">
        <el-input type="text" name="username" placeholder="账号/邮箱" class="input-item"/>
        <el-input type="password" name="password" placeholder="密码" class="input-item"/>
        <div class="btn btn-login">登录</div>
      </el-form>

      <div class="msg">
        没有账号?
        <a href="#">立即注册</a>
      </div>
      <div>
        <div class="btn-forget-password">
          <a href="#">忘记密码</a>
          &nbsp;
          <a href="#">申请解冻</a>
        </div>
        <div>

        </div>
      </div>
    </div>
  </div>
</template>

<script>

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
  background-image: url("../../assets/login/login-background.jpg");
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
  background: #fbc2eb;
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
</style>
