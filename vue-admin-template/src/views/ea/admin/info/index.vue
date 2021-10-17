<template>
  <div class="container">

    <!--标题-->
    <h2 style="position: relative; left: 7%;">个人中心</h2>

    <!--头像-->
    <div class="style-left-div">
      <el-row class="demo-avatar demo-basic" style="left: 25%">
        <el-col :span="12">
          <div class="demo-basic--circle">
            <div class="block"><el-avatar :size="100" :src="userInfo.portrait" class="style-avatar-div"></el-avatar></div>
            <el-button>更换头像</el-button>
          </div>
        </el-col>
      </el-row>
    </div>

    <div class="style-right-div">

      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="账号" prop="name">
          <el-input v-model="ruleForm.username"></el-input>
        </el-form-item>
        <el-form-item label="昵称" prop="name">
          <el-input v-model="ruleForm.name"></el-input>
        </el-form-item>
        <el-form-item label="出生日期" required>
          <el-col :span="11">
            <el-form-item prop="date1">
              <el-date-picker type="date" placeholder="出生日期" v-model="ruleForm.date1" style="width: 100%;"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item label="性别" prop="delivery">
          <el-radio v-model="radio" label="1">男</el-radio>
          <el-radio v-model="radio" label="2">女</el-radio>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="ruleForm.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="pwd">
          <el-input v-model="ruleForm.username"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">保存修改</el-button>
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

      userInfo: {
        username: '',
        password: '',
        nickname: '',
        gender: '',
        age: '',
        email: '',
        birth: '',
        status: '',
        portrait: 'https://typorasss2021.oss-cn-shenzhen.aliyuncs.com/avatar/man.jpeg',
        gmtCreated: '',
        gmtModified: ''
      },

      // 性别
      radio: '1',

      sizeList: ["large", "medium", "small"],

    //  form start
      ruleForm: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      },
      rules: {
        name: [
          { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        region: [
          { required: true, message: '请选择活动区域', trigger: 'change' }
        ],
        date1: [
          { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
        ],
        date2: [
          { type: 'date', required: true, message: '请选择时间', trigger: 'change' }
        ],
        type: [
          { type: 'array', required: true, message: '请至少选择一个活动性质', trigger: 'change' }
        ],
        resource: [
          { required: true, message: '请选择活动资源', trigger: 'change' }
        ],
        desc: [
          { required: true, message: '请填写活动形式', trigger: 'blur' }
        ]
      }

    //  form end
    };
  },

  created() {
    this.userInfo = JSON.parse(window.sessionStorage.getItem('userInfo'))
    console.log(this.userInfo.nickname)

  },

  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          alert('submit!');
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    LogoutAccount() {

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
