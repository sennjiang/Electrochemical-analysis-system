<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.title" placeholder="管理员昵称" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-select v-model="listQuery.importance" placeholder="状态" clearable style="width: 90px" class="filter-item">
        <el-option v-for="item in importanceOptions" :key="item" :label="item" :value="item" />
      </el-select>
      <el-select v-model="listQuery.type" placeholder="创建时间" clearable class="filter-item" style="width: 130px">
        <el-option v-for="item in calendarTypeOptions" :key="item.key" :label="item.key" :value="item.key" />
      </el-select>
      <el-button v-waves class="filter-item" style="margin-left: 20px; "  type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
      <el-button class="filter-item" style="margin-left: 410px;" type="primary" icon="el-icon-edit" @click="handleCreate">
        添加管理员
      </el-button>

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
      <el-table-column label="ID" prop="id" align="center" width="80">
        <template slot-scope="{row}">
          <span>{{ row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column label="用户名" width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.username }}</span>
        </template>
      </el-table-column>
      <el-table-column label="密码" min-width="150px">
        <template slot-scope="{row}">
          <span >{{ row.password }}</span>
        </template>
      </el-table-column>
      <el-table-column label="昵称" min-width="150px">
        <template slot-scope="{row}">
          <span >{{ row.nickname }}</span>
        </template>
      </el-table-column>
      <el-table-column label="邮箱" min-width="150px">
        <template slot-scope="{row}">
          <span >{{ row.email }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" min-width="110px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.gmtCreated }}</span>
        </template>
      </el-table-column>
      <el-table-column label="管理员状态" width="150px">
        <template slot-scope="{row}">
          <span>{{ row.status }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" width="230" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="mini" >
            解冻
          </el-button>
          <el-button v-if="row.status!='deleted'" size="mini" type="danger" >
            冻结
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- @pagination="getList" -->
    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit"  />
    <!-- 弹框 详情页面 -->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogDetailVisible">
      <div>{{ temp.username }}</div>
      <div>{{ temp.password }}</div>
      <div>{{ temp.nickname }}</div>
      <div>{{ temp.email }}</div>
      <div>{{ temp.gmtCreated }}</div>
      <div>{{ temp.status }}</div>


      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogDetailVisible = false">
          确认
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  // 查询引入的
  import waves from '@/directive/waves' // waves directive
  import { parseTime } from '@/utils'
  import Pagination from '@/components/Pagination' // secondary package based on el-pagination

  const calendarTypeOptions = [
    { key: '最近三天' },
    { key: '最近一周' }
  ]

  export default {
    name: 'AdminManage',
    components: { Pagination },
    directives: { waves },
    filters: {
      //类型过滤
      typeFilter(type) {
        return calendarTypeOptions[type]
      }
    },
    data() {
      return {
        tableKey: 0,
        list: [{id: 1,
          username: "20190001",
          password:"123456",
          nickname:"超级管理员",
          email:"1234567@163.com",
          gmtCreated:"2021-09-13 21:44:42",
          status:"1"}],
        total: 1,
        listLoading: true,
        listQuery: {
          page: 1,
          limit: 20,
          importance: undefined,
          title: undefined,
          type: undefined,
        },
        importanceOptions: ['正常','冻结·'],
        calendarTypeOptions,
        statusOptions: ['正常', '冻结'],

        temp: {
          id: 1,
          username: "20190001",
          password:"123456",
          nickname:"超级管理员",
          email:"1234567@163.com",
          gmtCreated:"2021-09-13 21:44:42",
          status:"1"
        },
        dialogDetailVisible: false,
        dialogStatus: '',
        textMap: {
          update: 'Edit',
          create: 'Create'
        },
        dialogPvVisible: false,
        pvData: [],
        rules: {
          type: [{ required: true, message: 'type is required', trigger: 'change' }],
          timestamp: [{ type: 'date', required: true, message: 'timestamp is required', trigger: 'change' }],
          title: [{ required: true, message: 'title is required', trigger: 'blur' }]
        },
        downloadLoading: false
      }
    },
    created() {
      this.getList()
    },
    methods: {
      getList() {
        this.listLoading = false
        this.list = [{id: 1,
          username: "20190001",
          password:"123456",
          nickname:"超级管理员",
          email:"1234567@163.com",
          gmtCreated:"2021-09-13 21:44:42",
          status:"1"}]
        this.total = 1
        // fetchList(this.listQuery).then(response => {
        // this.list = response.data.items
        // this.total = response.data.total
        // Just to simulate the time of the request
        // setTimeout(() => {
        //   this.listLoading = false
        // }, 1.5 * 1000)
        // })
      },
      handleFilter() {
        this.listQuery.page = 1
        this.getList()
      },
      handleModifyStatus(row, status) {
        this.$message({
          message: '操作Success',
          type: 'success'
        })
        row.status = status
      },
      handleCreate() {
        this.resetTemp()
        this.dialogStatus = 'create'
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].clearValidate()
        })
      },
      createData() {
        // this.$refs['dataForm'].validate((valid) => {
        //   if (valid) {
        //     this.temp.id = parseInt(Math.random() * 100) + 1024 // mock a id
        //     this.temp.author = 'vue-element-admin'
        //     createArticle(this.temp).then(() => {
        //       this.list.unshift(this.temp)
        //       this.dialogFormVisible = false
        //       this.$notify({
        //         title: 'Success',
        //         message: 'Created Successfully',
        //         type: 'success',
        //         duration: 2000
        //       })
        //     })
        //   }
        // })
      },
      handleDetail(row) {
        this.dialogDetailVisible = true
      },
      handleUpdate(row) {
        this.temp = Object.assign({}, row) // copy obj
        this.temp.timestamp = new Date(this.temp.timestamp)
        this.dialogStatus = 'update'
        this.dialogDetailVisible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].clearValidate()
        })
      },
      handleDelete(row, index) {
        this.$notify({
          title: 'Success',
          message: 'Delete Successfully',
          type: 'success',
          duration: 2000
        })
        this.list.splice(index, 1)
      },
      handleDownload() {
        // this.downloadLoading = true
        // import('@/vendor/Export2Excel').then(excel => {
        //   const tHeader = ['timestamp', 'title', 'type', 'importance', 'status']
        //   const filterVal = ['timestamp', 'title', 'type', 'importance', 'status']
        //   const data = this.formatJson(filterVal)
        //   excel.export_json_to_excel({
        //     header: tHeader,
        //     data,
        //     filename: 'table-list'
        //   })
        //   this.downloadLoading = false
        // })
      },
      formatJson(filterVal) {
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
