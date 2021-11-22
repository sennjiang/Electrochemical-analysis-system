<template>
  <div class="container">

    <!--标题-->
    <h2 style="position: relative; left: 7%;">个人中心</h2>

    <!--头像-->
    <div class="style-left-div">
      <el-row class="demo-avatar demo-basic" style="left: 25%">
        <el-col :span="12">
          <div class="demo-basic--circle">
            <div class="block">
              <el-avatar :size="100" :src="userInfo.portrait" class="style-avatar-div"></el-avatar>
            </div>

            <el-upload
              class="upload-demo"
              :action="fileUploadPath"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :before-remove="beforeRemove"
              multiple
              :limit="3"
              :on-exceed="handleExceed"
              :file-list="fileList"
              @change="changPath"
            >
              <el-button>点击上传</el-button>
              <div slot="tip" class="el-upload__tip"></div>
            </el-upload>
          </div>
        </el-col>
      </el-row>



    </div>

    <div class="style-right-div">
      <el-form :model="userInfo" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="账号" prop="username">
          <span v-text='userInfo.username'></span>
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="userInfo.nickname" style="width: 50%"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <span>{{ genderText }}</span>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <span v-text='userInfo.age'></span>
        </el-form-item>
        <el-form-item label="出生日期" required>
          <el-col :span="11">
            <el-form-item prop="birth">
              <el-date-picker type="date" placeholder="" v-model="userInfo.birth"
                              format="yyyy-MM-dd"
                              value-format="yyyy-MM-dd"
                              style="width: 100%;"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item label="邮箱" prop="email" style="position: relative;">

            <span v-text='userInfo.email'></span>
            <el-button size="small" style="position: relative; margin-left: 38%" @click="goToEmailVerify">修改邮箱</el-button>

        </el-form-item>
        <el-form-item label="密码" prop="pwd" style="position: relative;">
          <span>******</span>
          <el-button size="small" style="position: relative; left: 50%" @click="goToPasswordVerify">修改密码</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm">保存修改</el-button>
          <el-button type="info" @click="LogoutAccount()">注销账号</el-button>
        </el-form-item>
      </el-form>

    </div>

  </div>
</template>

<script>
import {mapGetters} from 'vuex'

export default {
  name: 'Info',
  computed: {
    ...mapGetters([
      'name'
    ])
  },
  data() {
    return {

      // 上传头像
      // {name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}, {name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}
      fileList: [],

      // 用户信息
      userInfo: {
        boundary: '0104',
        username: '',
        password: '',
        nickname: '',
        gender: '',
        age: '',
        email: '',
        birth: '',
        status: '',
        // portrait: 'https://typorasss2021.oss-cn-shenzhen.aliyuncs.com/avatar/man.jpeg',
        portrait: 'http://192.168.70.128/img/qingning.png',
        gmtCreated: '',
        gmtModified: ''
      },

      // 头像上传路径
      // fileUploadPath: 'http://localhost:8080/Electrochemical_Analysis_System_war/uploadAvatar?boundary=0113&username='+this.username,
      fileUploadPath: '',
      username: '20190002',

      genderText: '女',

      sizeList: ["large", "medium", "small"],

      //  form start
      ruleForm: {
        nickname: '',
        birth: '',
      },
      rules: {
        nickname: [
          {required: true, trigger: 'blur'},
          {min: 1, max: 8, message: '长度在1到8个字符', trigger: 'blur'}
        ],
        birth: [
          {type: 'date', required: true, message: '请选择日期', trigger: 'change'}
        ]
      }

      //  form end
    };
  },

  created() {
    this.userInfo = JSON.parse(window.sessionStorage.getItem('userInfo'))
    this.genderDisplay()
    this.fileUploadPath = "http://localhost:8080/Electrochemical_Analysis_System_war/uploadAvatar?boundary=0113&username=" + this.userInfo.username
  },

  methods: {

    // 上传头像
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${ file.name }？`);
    },
    // 提交表单
    // <!--提交注册-->
    submitForm() {
      // this.$refs[formName].validate(async valid => {
      //   if (valid) {
      this.postRequest('/modifyUser', {
        boundary: '0104',
        username: this.userInfo.username,
        nickname: this.userInfo.nickname,
        portrait: this.userInfo.portrait
      }).then(resp => {
      })
    },

    // 显示性别
    genderDisplay() {
      if (this.userInfo.gender == 0) {
        this.genderText = '女'
      } else {
        this.genderText = '男'
      }

    },
    LogoutAccount() {
      this.$router.push({
        path: "/logoutUserVerify"
      });
    },
    // 跳转修改密码
    goToPasswordVerify() {
      this.$router.push({
        path: "/modifyPasswordVerify"
      });
    },
    // 跳转修改邮箱
    goToEmailVerify() {
      this.$router.push({
        path: "/modifyEmailVerify"
      });
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  width: 100%;
  height: auto;
  //border: 1px solid blue;
  position: absolute;
}

.style-left-div {
  position: relative;
  width: 18%;
  height: 100%;
  //border: 1px solid red;
  float: left;
  text-align: right;
}

.style-right-div {
  position: relative;
  width: 50%;
  height: 100%;
  float: left;
  padding-top: 0;
  //border: 1px solid green;
}

.style-avatar-div {
  border: 2px solid #09aaff;
}
</style>
