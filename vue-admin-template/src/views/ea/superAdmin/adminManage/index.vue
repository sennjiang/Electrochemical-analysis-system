<template>
  <div>
    <!-- 管理员列表 -->

    <!-- 管理员列表主体 -->
    <!-- 卡片区 -->
    <el-card>
      <!-- 间隙 -->
      <el-row :gutter="25">
        <!-- 搜索区域 -->
        <!-- 搜索添加 -->
        <!-- 列宽10 -->
        <el-col :span="10">
          <el-input placeholder="用户名、昵称" v-model="queryInfo.query" clearable @clear="getUserList">
            <el-button slot="append" icon="el-icon-search" @click="getUserList"></el-button>
          </el-input>
        </el-col>

        <!-- 添加按钮 -->
        <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible = true">添加管理员</el-button>
        </el-col>
      </el-row>

      <!-- 管理员列表 border边框 stripe隔行变色 -->
      <el-table :data="userList" border stripe>
        <!-- 索引列 -->
        <el-table-column type="index"></el-table-column>
        <el-table-column label="用户名" prop="username"></el-table-column>
        <el-table-column label="邮箱" prop="email"></el-table-column>
        <el-table-column label="密码" prop="password"></el-table-column>
        <el-table-column label="昵称" prop="nickname"></el-table-column>
        <el-table-column label="状态" prop="status">
          <!-- 作用域插槽 -->
          <template slot-scope="scope">
            <!-- {{scope.row}}每一行封存的数据 -->
            <el-switch v-model="scope.row.status" @change="userStateChanged(scope.row)"></el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <!-- 修改 -->
            <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row.username)"></el-button>
            <!-- 删除 -->
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="deleteUser(scope.row.username)"></el-button>

          </template>
        </el-table-column>
      </el-table>

      <!-- 分页组件 size-change 每页最大变化数 current-change 当前最大变化 layout功能组件-->
      <div>
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="queryInfo.pageNum"
          :page-sizes="[1, 2, 5, 100]"
          :page-size="queryInfo.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </div>
    </el-card>

    <!-- 新增管理员区域 -->
    <el-dialog title="添加管理员" :visible.sync="addDialogVisible" width="50%" @close="addDialogClosed">
      <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="70px">
        <!-- 管理员名 -->
        <el-form-item label="用户名" prop="username">
          <el-input v-model="addForm.username"></el-input>
        </el-form-item>
        <!-- 密码 -->
        <el-form-item label="密码" prop="password">
          <el-input v-model="addForm.password"></el-input>
        </el-form-item>
        <!-- 邮箱 -->
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="addForm.email"></el-input>
        </el-form-item>
      </el-form>
      <!-- 内容底部区域 -->
      <span slot="footer" class="dialog-footer">
          <el-button @click="addDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="addUser">确定</el-button>
        </span>
    </el-dialog>

    <!-- 修改管理员信息对话框 -->
    <el-dialog title="修改管理员" :visible.sync="editDialogVisible" width="50%" @close="editDialogClosed">
      <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="70px">
        <!-- 管理员名 -->
        <el-form-item label="管理员名" prop="username">
          <el-input v-model="editForm.username" disabled></el-input>
        </el-form-item>
        <!-- 密码 -->
        <el-form-item label="密码" prop="password">
          <el-input v-model="editForm.password"></el-input>
        </el-form-item>
        <!-- 邮箱 -->
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="editForm.email"></el-input>
        </el-form-item>
      </el-form>
      <!-- 内容底部区域 -->
      <span slot="footer" class="dialog-footer">
          <el-button @click="editDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="editUserInfo">确定</el-button>
        </span>
    </el-dialog>

  </div>
</template>

<script>
  export default {
    created(){
      this.getUserList();
    },
    data(){
      return {
        //查询信息实体
        queryInfo:{
          boundary: '1001',
          query:"",//查询信息
          pageNum:1,//当前页
          pageSize:5,//每页最大数
        },
        //修改状态信息实体{
        editStatusInfo:{
          boundary: '1002',
          username:"",
          status:"",
        },
        userList:[],//管理员列表
        total: 0,//总记录数

        addDialogVisible: false,//对话框状态
        //添加表单信息
        addForm: {
          boundary: '1003',
          username:'',
          password:'',
          email:''
        },
        //删除管理员信息实体
        deleteInfo:{
          boundary: '1004',
          username:'',
        },

        //查询要修改的管理员的信息
        queryEditUserInfo:{
          boundary: '1005',
          username:'',
        },
        //修改管理员的信息
        editForm:{
          boundary: '1006',
          username:'',
          password:'',
          email:''
        },
        //显示和隐藏修改管理员栏
        editDialogVisible:false,
        //添加的表单验证
        addFormRules: {
          username: [
            { required: true, message: '请输入管理员名', trigger: 'blur' },
            { min: 5, max: 8, message: '长度在 5 到 8 个字符', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 6, max: 8, message: '长度在 6 到 8 个字符', trigger: 'blur' }
          ],
          email: [
            { required: true, message: '请输入邮箱', trigger: 'blur' },
            { min: 5, max: 15, message: '请输入正确的邮箱地址', trigger: 'blur' }
          ],
        },
        //修改的表单验证
        editFormRules:{
          password: [
            { required: true, message: "请输入密码", trigger: "blur" },
            { min: 6, max: 8, message: "长度在 6 到 8 个字符", trigger: "blur" }
          ],
          email: [
            { required: true, message: "请输入邮箱", trigger: "blur" },
            { min: 5, max: 15, message: "请输入正确邮箱地址", trigger: "blur" }
          ],
        },
      };
    },
    methods:{

      //获取所有管理员
      getUserList: function () {
        this.queryInfo.boundary = '1001'
        this.postRequest("/admin/list", this.queryInfo).then(res => {


          this.userList = res.data;//管理员列表数据封装
          //console.log(this.userList);
          //console.log(this.userList[0].status);

          for (var i = 0; i < this.userList.length; i++) {
            //console.log(this.userList[i].status);
            //this.userList[i].status = this.userList[i].status === "1" ?  Boolean("true") : Boolean("false");
            if(this.userList[i].status === 1) this.userList[i].status = true;
            //console.log(this.userList[i].status)

          }
          //console.log(this.userList);
          this.total = res.numbers;//总管理员数封装

        })
      },

      //最大数E:\project\Electrochemical-analysis-system\vue-admin-template
      handleSizeChange(newSize){
        this.queryInfo.pageSize = newSize;
        this.getUserList();
      },

      //pageNum的触发动作
      handleCurrentChange(newPage){
        this.queryInfo.pageNum = newPage;
        this.getUserList();
      },

      //修改管理员状态
      async userStateChanged(userInfo){
        this.editStatusInfo.boundary = '1002';
        this.editStatusInfo.username = userInfo.username;
        this.editStatusInfo.status = userInfo.status;
        this.postRequest("/admin/adminStatusChanged", this.editStatusInfo).then(res => {

        })

      },

      //监听添加管理员
      addDialogClosed(){
        this.$refs.addFormRef.resetFields();
      },

      //添加管理员
      addUser(){
        this.$refs.addFormRef.validate(async valid=>{
          if(!valid) return;
          this.addForm.boundary = '1003'
          this.postRequest("/admin/addAdmin",this.addForm).then(res=> {
            if(res.data!=2){

            }else{

              this.addDialogVisible = false;
              this.getUserList();
            }

          })
        });
      },

      //根据主键删除该用户的管理员角色
      async deleteUser(username){

        const confirmResult = await this.$confirm('此操作将永久删除该用户的管理员角色，是否继续','提示',{
          confirmButtonText:'确定',
          cancelButtonText:'取消',
          type:'warning'
        }).catch(err => err)
        if(confirmResult!='confirm'){
          return this.$message.info("已取消删除");
        }

        this.deleteInfo.boundary = '1004';
        this.deleteInfo.username = username;
        this.postRequest("/admin/deleteAdmin",this.deleteInfo).then(res=>{
          if(res.data != 1){

          }

          this.getUserList();
        });

      },

      //显示修改对话框
      async showEditDialog(username){

        this.queryEditUserInfo.boundary = '1005';
        this.queryEditUserInfo.username = username;
        this.postRequest("/admin/queryEditAdmin" , this.queryEditUserInfo).then(res=>{
          this.editForm = res.data;//查询出管理员信息反填回编辑表单
          this.editDialogVisible = true;//开始编辑对话框
        });

      },
      //关闭窗口
      getDialogClosed(){
        this.$refs.editFormRef.resetFields();//重置信息
      },
      //确认修改
      editUserInfo() {
        this.$refs.editFormRef.validate(async valid => {
          if (!valid) return;
          //发起修改的请求
          this.editForm.boundary = '1006'
          this.postRequest("/admin/editAdmin", this.editForm).then(res => {
            if (res.data != 1) {

            } else {

              this.editDialogVisible = false;
              this.getUserList();
            }
          })
        });
      }
    },

  }
</script>

<style lang = "less" scoped>
  .el-breadcrumb{
    margin-bottom: 15px;
    font-size: 17px;
  }
</style>
