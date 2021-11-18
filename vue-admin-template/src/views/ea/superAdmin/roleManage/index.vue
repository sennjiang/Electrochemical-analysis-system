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
        <el-table-column label="角色权限" >
          <span slot-scope="scope">
              <span >{{ rightNames[scope.$index] }}&nbsp</span>
          </span>
        </el-table-column>
        <el-table-column label="创建时间" :formatter="dateFormat" prop="genTime"></el-table-column>
        <el-table-column label="角色级别（0，超级管理员，1，管理员，2，普通用户）" prop="roleType"></el-table-column>


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
            <el-radio :label="0" >管理员</el-radio>
            <el-radio :label="1" >普通用户</el-radio>
          </el-radio-group>

        </el-form-item>

        <!-- 菜单分配-->

        <el-form-item label="菜单分配" prop="checkRightList">
          <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
          <div style="margin: 15px 0;"></div>
          <el-checkbox-group v-model="addForm.checkRightList" @change="handleCheckedCitiesChange">
            <el-checkbox v-for="rightName in rights" :label="rightName" :key="rightName">{{rightName}}</el-checkbox>
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

  const allRightList = ['用户管理','解冻审核','文件管理(管理员)','操作记录','算法管理','电化学分析','文件管理','回收站管理']


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

        rightNames:[],//角色权限列表
        //rightNames:[[]],
        roleList:[

        ],//角色列表


        total: 0,//总记录数

        //查询权限信息实体
        queryRightInfo:{
          boundary: '1402',
          query:""
        },

        checkAll: false,

        //所有权限列表
        rights: allRightList,
        isIndeterminate: true,

        addDialogVisible: false,//对话框状态
        //添加表单信息
        addForm: {
          boundary: '1003',
          roleName:'',
          description:'',
          //角色级别
          roleType: 0,
          //权限
          checkRightList:[],
        },



      };
    },
    methods:{

      //获取所有角色
      getRoleList: function () {
        this.queryInfo.boundary = '1401'
        this.postRequest("/role/list", this.queryInfo).then(res => {
          this.rightNames = res.rightNames;
          this.roleList = res.data;//角色列表数据封装
          console.log(this.roleList);

          this.total = res.numbers;//总管理员数封装

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
          console.log(this.addForm.checkRightList)

          this.postRequest("/role/addRole",this.addForm).then(res=> {
            if(res.data!=2){

            }else{


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

      },

      handleCheckAllChange(val) {
        this.checkedCities = val ? allRightList : [];
        this.isIndeterminate = false;
      },
      handleCheckedCitiesChange(value) {
        let checkedCount = value.length;
        this.checkAll = checkedCount === this.rights.length;
        this.isIndeterminate = checkedCount > 0 && checkedCount < this.rights.length;
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
