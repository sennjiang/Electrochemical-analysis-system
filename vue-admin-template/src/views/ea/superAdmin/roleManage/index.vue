<template>
  <div>
    <!-- 角色列表 -->

    <!-- 角色列表主体 -->
    <!-- 卡片区 -->
    <el-card>
      <!-- 间隙 -->
      <el-row :gutter="25">
        <!-- 搜索区域 -->
        <!-- 搜索添加 -->
        <!-- 列宽10 -->
        <el-col :span="10">
          <el-input placeholder="角色名称" v-model="queryInfo.query" clearable @clear="getRoleList">
            <el-button slot="append" icon="el-icon-search" @click="getRoleList"></el-button>
          </el-input>
        </el-col>

        <!-- 添加按钮 -->
        <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible = true">添加角色</el-button>
        </el-col>
      </el-row>

      <!-- 角色列表 border边框 stripe隔行变色 -->
      <el-table :data="roleList" border stripe>
        <!-- 索引列 -->
        <el-table-column type="index"></el-table-column>
        <el-table-column label="角色ID" prop="roleId"></el-table-column>
        <el-table-column label="角色名称" prop="roleName"></el-table-column>
        <el-table-column label="创建时间" :formatter="dateFormat" prop="genTime"></el-table-column>
        <el-table-column label="角色级别（0，管理员，1，普通用户，2，超级管理员）" prop="roleType"></el-table-column>

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

    <!-- 新增角色区域 -->
    <el-dialog title="添加角色" :visible.sync="addDialogVisible" width="50%" @close="addDialogClosed">
      <el-form :model="addForm"  ref="addFormRef" label-width="70px">
        <!-- 角色名 -->
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="addForm.roleName"></el-input>
        </el-form-item>
        <!-- 角色描述 -->
        <el-form-item label="角色描述" prop="description">
          <el-input v-model="addForm.description"></el-input>
        </el-form-item>
        <!-- 角色级别-->
        <el-form-item label="角色级别" prop="roleType">
          <el-radio-group v-model="addForm.roleType">
            <el-radio :label='0' @change="getRightList('管理员')">管理员</el-radio>
            <el-radio :label='1' @change="getRightList('普通用户')">普通用户</el-radio>
          </el-radio-group>

        </el-form-item>
        <!-- 菜单分配-->

        <el-form-item label="菜单分配" prop="checkRightList">
          <el-checkbox-group v-model="rightList" >
            <el-checkbox v-model="rightList" v-for="right in rightList" :label="right.rightName" :key="right.rightName">{{right.rightName}}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>


      </el-form>
      <!-- 内容底部区域 -->
      <span slot="footer" class="dialog-footer">
          <el-button @click="addDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="addRole">确定</el-button>
        </span>
    </el-dialog>



  </div>
</template>

<script>

  import moment from 'moment'

  export default {
    created(){
      this.getRoleList();

    },
    data(){
      return {
        //查询角色信息实体
        queryInfo:{
          boundary: '1401',
          query:"",//查询信息
          pageNum:1,//当前页
          pageSize:5,//每页最大数
        },

        roleList:[],//角色列表
        total: 0,//总记录数

        //查询权限信息实体
        queryRightInfo:{
          boundary: '1402',
          query:""
        },
        //权限名称列表
        rightList:[
          {
            rightName:'',
          }
        ],




        addDialogVisible: false,//对话框状态
        //添加表单信息
        addForm: {
          boundary: '1003',
          roleName:'',
          description:'',
          //角色级别
          roleType:'',
          //权限
          checkRightList:[
            {
              rightName:'',
            }
          ],
        },



      };
    },
    methods:{

      //获取所有角色
      getRoleList: function () {
        this.queryInfo.boundary = '1401'
        this.postRequest("/role/list", this.queryInfo).then(res => {

          this.roleList = res.data;//角色列表数据封装
          this.total = res.numbers;//总管理员数封装
          this.$message.success("角色列表加载成功！！！");
        })
      },

      //根据选择获取权限
      getRightList:function (rightType){
        this.queryRightInfo.boundary = '1402'
        this.queryRightInfo.query = rightType
        this.postRequest("/right/nameList",this.queryRightInfo).then(res=>{
          this.rightList = res.data;//权限列表数据封装
          console.log(this.rightList);
        })
      },

      //最大数E:\project\Electrochemical-analysis-system\vue-admin-template
      handleSizeChange(newSize){
        this.queryInfo.pageSize = newSize;
        this.getRoleList();
      },

      //pageNum的触发动作
      handleCurrentChange(newPage){
        this.queryInfo.pageNum = newPage;
        this.getRoleList();
      },



      //监听添加管理员
      addDialogClosed(){
        this.$refs.addFormRef.resetFields();
      },

      //添加管理员
      addRole(){
        this.$refs.addFormRef.validate(async valid=>{
          if(!valid) return;
          this.addForm.boundary = '1403'
          this.addForm.checkRightList = this.rightList
          this.postRequest("/role/addRole",this.addForm).then(res=> {
            if(res.data!=2){
              return this.$message.error("添加失败！！！");
            }else{
              this.$message.success("添加成功！！！");
              this.addDialogVisible = false;
              this.getRoleList();
            }

          })
        });
      },

      dateFormat:function(row,column){

        let date = row[column.property];

        if(date == undefined){return ''};

        return moment(date).format("YYYY-MM-DD HH:mm:ss")

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
