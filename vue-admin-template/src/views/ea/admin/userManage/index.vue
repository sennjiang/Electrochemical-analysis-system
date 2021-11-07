<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.key" placeholder="用户名" style="width: 300px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-button v-waves class="filter-item" style="margin-left: 0px; "  type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
      <!--<el-button class="filter-item" style="margin-left: 770px;" type="primary" icon="el-icon-edit" @click="addDialog = true">-->
      <!--  添加用户-->
      <!--</el-button>-->
    </div>

    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
    >
      <el-table-column label="用户名" width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.username }}</span>
        </template>
      </el-table-column>
      <el-table-column label="昵称" align="center" width="200px">
        <template slot-scope="{row}">
          <span>{{ row.nickname }}</span>
        </template>
      </el-table-column>
      <el-table-column label="邮箱" width="200px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.email }}</span>
        </template>
      </el-table-column>
      <el-table-column label="年龄" width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.age }}</span>
        </template>
      </el-table-column>
      <el-table-column label="性别" width="150px" align="center">
        <template slot-scope="{row}">
          <span v-if="row.gender === 1">男</span>
          <span v-else>女</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" width="150px" align="center">
        <template slot-scope="{row}">
          <span v-if="row.status === 1">正常</span>
          <span v-else>冻结</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" min-width="200px" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button v-if="row.status == 1" type="danger" size="mini" @click="freezeOpen(row)">
            冻结
          </el-button>
          <el-button type="primary" size="mini" @click="updateOpen(row)">
            修改
          </el-button>
        </template>
      </el-table-column>
    </el-table>



    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="listQuery.currentPage"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="listQuery.pageSize"
      background=true
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>


    <!--&lt;!&ndash;添加用户模态框&ndash;&gt;-->
    <!--<el-dialog title="添加用户" :visible.sync="addDialog">-->
    <!--  <el-form :model="addForm" :rules="rules" ref="addForm">-->
    <!--    <el-form-item label="用户名" prop="username" :label-width="formLabelWidth">-->
    <!--      <el-input v-model="addForm.username" type="text" auto-complete="false" placeholder="请输入用户名"></el-input>-->
    <!--    </el-form-item>-->
    <!--    <el-form-item label="密码" prop="password" :label-width="formLabelWidth">-->
    <!--      <el-input v-model="addForm.password" type="password" auto-complete="false" placeholder="请输入密码" show-password></el-input>-->
    <!--    </el-form-item>-->
    <!--    <el-form-item label="邮箱" prop="email" :label-width="formLabelWidth">-->
    <!--      <el-input v-model="addForm.email" type="text" auto-complete="false" placeholder="请输入邮箱"></el-input>-->
    <!--    </el-form-item>-->
    <!--  </el-form>-->
    <!--  <div slot="footer" class="dialog-footer">-->
    <!--    <el-button @click="addDialog = false">取 消</el-button>-->
    <!--    <el-button type="primary" @click="addUser">确 定</el-button>-->
    <!--  </div>-->
    <!--</el-dialog>-->


    <!--修改用户模态框-->
    <el-dialog title="修改用户信息" :visible.sync="updateDialog">
      <el-form :model="currentData" :rules="rules" ref="currentData">
        <el-form-item label="昵称" prop="username" :label-width="formLabelWidth">
          <el-input v-model="currentData.nickname" type="text" auto-complete="false" placeholder="请输入修改后的用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" :label-width="formLabelWidth">
          <el-input v-model="currentData.password" type="password" auto-complete="false" placeholder="请输入修改后的密码" show-password></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email" :label-width="formLabelWidth">
          <el-input v-model="currentData.email" type="text" auto-complete="false" placeholder="请输入修改后的邮箱"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="updateDialog = false">取 消</el-button>
        <el-button type="primary" @click="updateUser">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 冻结理由模态框 -->
    <el-dialog title="冻结用户" :visible.sync="freezeDialog">
      <el-form :model="freezeData" :rules="freezeRules" ref="freezeData">
        <el-form-item label="理由" prop="freezeReason" :label-width="formLabelWidth">
          <el-input v-model="freezeData.freezeReason" :rows="3" type="textarea" auto-complete="false" placeholder="请输入冻结理由"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="freezeDialog = false">取 消</el-button>
        <el-button type="primary" @click="freezeUser">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

export default {
  name: 'userManage',
  components: { Pagination },
  directives: { waves },
  data() {
    return {
      formLabelWidth: '70px',
      tableKey: 0,
      list: null,
      total: 0,
      currentData: {
        boundary: '0104',
        username: '',
        password: '',
        nickname: '',
        gender: '',
        age: '',
        email: '',
        birth: '',
        status: '',
        portrait: ''
      },
      listLoading: true,
      listQuery: {
        boundary: '0208',
        currentPage: 1,
        pageSize: 10,
        title: undefined,
        type: 1,
        status: 1,
        key: undefined
      },
      freezeDialog: false,
      addDialog : false,
      updateDialog: false,
      rules: {
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        email: [{ required: true, message: '请输入邮箱', trigger: 'blur' }]
      },
      freezeRules: {
        freezeReason: [{ required: true, message: '请输入冻结理由', trigger: 'blur' }]
      },
      freezeData: {
        boundary: '1201',
        username: '',
        freezeReason: ''
      }
    }
  },
  created() {
    this.getList('/admin/queryUser', '0103')
  },
  methods: {
    handleSizeChange(val) {
      this.listQuery.pageSize=val,
        this.getList('/admin/queryUser', '0103')
    },
    handleCurrentChange(val) {
      this.listQuery.currentPage=val,
        this.getList('/admin/queryUser', '0103')
    },
    // 查询用户列表
    getList(path,b) {
      this.loading = true
      this.listQuery.boundary = b
      this.postRequest('/admin/queryUser', this.listQuery).then(resp => {
        if(resp) {
          this.list = resp.users
          this.total = resp.total
        }
      })
      this.listLoading = false
      setTimeout(() => {
        this.loading = false
      }, 750)
    },
    //
    handleFilter() {
        this.listQuery.currentPage = 1
        this.getList('/admin/queryUser','0103')
    },
    handleModifyStatus(row, status) {
      this.$message({
        message: '操作Success',
        type: 'success'
      })
      row.status = status
    },
    updateUser(row) {
      this.$refs.currentData.validate(async (valid) => {
        if (valid) {
          this.postRequest('/admin/updateUser', this.currentData).then(resp => {
            if(resp) {
              this.getList('/admin/queryUser', '0103')
            }
          })
          this.updateDialog = false
        } else {
          this.$message.error('请正确填写信息哦')
          return false
        }
      })
    },
    updateOpen(row) {
      this.updateDialog = true;
      this.currentData.username = row.username;
      this.currentData.nickname = row.nickname;
      this.currentData.password = row.password;
      this.currentData.email = row.email;
      this.currentData.gender = row.gender;
      this.currentData.age = row.age;
      this.currentData.birth = row.birth;
      this.currentData.status = row.status;
      this.currentData.portrait = row.portrait;
    },
    freezeUser(row) {
      this.$refs.freezeData.validate(async (valid) => {
        if (valid) {
          this.getRequest('/admin/freezeUserByUsername', {boundary:'1201',username:this.freezeData.username, freezeReason: this.freezeData.freezeReason})
          this.postRequest('/admin/updateUser', {boundary: '1202', username: this.freezeData.username, status: 0}).then(resp => {
            if(resp) {
              this.getList('/admin/queryUser', '0103')
            }
          })
          this.freezeDialog = false
        } else {
          this.$message.error('请正确填写信息哦')
          return false
        }
      })
    },
    freezeOpen(row) {
      this.freezeData.username = row.username
      this.freezeData.freezeReason = ''
      this.freezeDialog = true
    },
    formatTime(filterVal) {
      return this.list.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
    }
  }
}
</script>
<style>
.file_table td{
  height: 30px;
  /* background-color: beige; */
  border-style:none none solid none;
  border-color:#c5c5c5;
  border-width:2px;
}
.upload-demo{
}
</style>

