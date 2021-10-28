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
          <el-input placeholder="请输入搜索内容" v-model="queryInfo.query" clearable @clear="getUserList">
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
            <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row.id)"></el-button>
            <!-- 删除 -->
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="deleteUser(scope.row.id)"></el-button>
            <!-- 权限 -->
            <!-- 文字提示 enterable 隐藏 -->
            <el-tooltip effect="dark" content="分配权限" placement="top-start" :enterable="false"></el-tooltip>
            <el-button type="warning" icon="el-icon-setting" size="mini"></el-button>
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
        //修改管理员的信息
        editForm:{},
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
          this.$message.success("管理员列表加载成功！！！");
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
          if(res.data!=1){
            return this.$message.error("修改失败！！！");
          } else return this.$message.success("修改成功！！！");
        })

      },

      //监听添加管理员
      addDialogClosed(){
        this.$refs.addFormRef.resetFields();
      },
      async addUser(){
        this.$refs.addFormRef.validate(async valid=>{
          if(!valid) return;
          this.boundary = '1003'
          this.postRequest("admin/addAdmin",this.addForm).then(res=> {
            if(res.data!=2){
              return this.$message.success("添加失败！！！");
            }
            this.$message.success("添加成功！！！");
          })
          this.addDialogVisible = false;
          this.getUserList();
        });
      },

      //根据主键删除管理员
      async deleteUser(id){

        const confirmResult = await this.$confirm('此操作将永久删除管理员，是否继续','提示',{
          confirmButtonText:'确定',
          cancelButtonText:'取消',
          type:'warning'
        }).catch(err => err)
        if(confirmResult!='confirm'){
          return this.$message.info("已取消删除");
        }
        const {data:res} = await this.$http.delete("deleteUser?id="+id);
        if(res != "success"){
          return this.$message.error("删除失败！");
        }
        this.$message.success("删除成功！");
        this.getUserList();
      },

      //显示对话框
      async showEditDialog(id){
        const {data:res} = await this.$http.get("getupdate?id="+id);
        this.editForm = res;//查询出管理员信息反填回编辑表单
        this.editDialogVisible = true;//开始编辑对话框
      },
      //关闭窗口
      getDialogClosed(){
        this.$refs.editFormRef.resetFields();//重置信息
      },
      //确认修改
      editUserInfo(){
        this.$refs.editFormRef.validate(async valid =>{
          if(!valid) return;
          //发起修改的请求
          const {data:res} =  await this.$http.post("edituser",this.editForm);
          if(res!="success") return this.$message.error("操作失败！！！");
          this.$message.success("操作成功！！！");
          //隐藏
          this.editDialogVisible = false;
          this.getUserList();
        })
      },
    },

  }
</script>

<style lang = "less" scoped>
  .el-breadcrumb{
    margin-bottom: 15px;
    font-size: 17px;
  }
</style>
