<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.title" placeholder="文件名" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-select v-model="listQuery.importance" placeholder="文件类型" clearable style="width: 90px" class="filter-item">
        <el-option v-for="item in importanceOptions" :key="item" :label="item" :value="item" />
      </el-select>
      <el-select v-model="listQuery.type" placeholder="处理时间" clearable class="filter-item" style="width: 130px">
        <el-option v-for="item in calendarTypeOptions" :key="item.key" :label="item.key" :value="item.key" />
      </el-select>
      <el-button v-waves class="filter-item" style="margin-left: 20px; "  type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
      <el-button class="filter-item" style="margin-left: 410px;" type="primary" icon="el-icon-edit" @click="handleCreate">
        添加
      </el-button>
      <el-button v-waves :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload">
        导出
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
          <span>{{ row.fileId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="文件名" width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="文件大小" width="80px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.size }}</span>
        </template>
      </el-table-column>
      <el-table-column label="上传时间" min-width="110px" align="center">
        <template slot-scope="{row}">
          <span >{{ row.produceTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="处理时间" min-width="110px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.modifiedTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="文件状态" width="80px" align="center">
        <template slot-scope="{row}">
        <span> {{ row.status | statusFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column label="所属者" align="center" width="120px">
        <template slot-scope="{row}">
          <span>{{ row.owner}}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="230" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="mini" @click="handleDetail(row)">
            详情
          </el-button>
          <el-button v-if="row.status!='deleted'" size="mini" type="danger" @click="handleDelete(row,$index)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
 <!-- @pagination="getList" -->
    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit"  />
    <!-- 弹框 详情页面 -->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogDetailVisible">
      <table  
      fit
      highlight-current-row
      style="width: 100%;" class="file_table">
        <tr align="center"><td>属性名</td> <td>属性值</td></tr>
        <tr  align="center"><td>fileId</td><td>{{ data.fileId }}</td></tr>
        <tr align="center"><td>name</td> <td>{{ data.name }}</td></tr>
        <tr align="center"><td>url</td> <td>{{ data.url }}</td></tr>
        <tr align="center"><td>owner</td> <td>{{ data.owner }}</td></tr>
        <tr align="center"><td>size</td> <td>{{ data.size }}</td></tr>
        <tr align="center"><td>hash</td> <td>{{ data.hash }}</td></tr>  
        <tr align="center"><td>type</td> <td>{{data.type | typeFilter }}</td></tr>
        <tr align="center"><td>status</td> <td>{{ data.status }}</td></tr>
        <tr align="center"><td>produceTime</td> <td>{{ data.produceTime }}</td></tr>
        <tr align="center"><td>modifiedTime</td> <td>{{ data.modifiedTime }}</td></tr>
        <tr align="center"><td>dataStart</td> <td>{{ data.dataStart }}</td></tr>
        <tr align="center"><td>dataEnd </td> <td>{{ data.dataEnd }}</td></tr>
        <tr align="center"><td>dataBottom </td><td>{{ data.dataBottom }}</td></tr>
        <tr align="center"><td>dataPeak </td><td>{{ data.dataPeak }}</td></tr>
        <tr align="center"><td>dataPrecision </td><td>{{ data.dataPrecision }}</td></tr>
        <tr align="center"><td>dataCycle </td><td>{{ data.dataCycle }}</td></tr>
        <tr align="center"><td>dataRate </td><td>{{ data.dataRate }}</td></tr>
        <tr align="center"><td>dataResult </td><td>{{ data.dataResult }}</td></tr>
        </table>
      
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
import { fetchList } from '@/api/file'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

const calendarTypeOptions = [
  { key: "最近三天" },
  { key: "最近一周" }
]

const statusOptions = [
  { key: '正常' }, 
  { key: '被删除' }
]

const typeOptions = [{key:'用户文件'},{key:'系统文件'}]

export default {
  name: 'FileManage',
  components: { Pagination },
  directives: { waves },
  filters: {
    //类型过滤
    typeFilter:function (type) {
      return typeOptions[type].key
    },
    statusFilter:function (type) {
      return statusOptions[type].key
    }
  },
  data() {
    return {
      tableKey: 0,
      list: [{fileId: 1, name: "a.txt", url: "/aaa/bbb", owner: "1234567890", size: "100kb",hash:"qwer", type: 1, status: "1", produceTime:"2021:10:01:15:00", modifiedTime:"2021:10:01:15:00"}],
      total: 1,
      // 懒加载的数据
      data:{
        fileId: 1,
        name: "a.txt", 
        url: "/aaa/bbb", 
        owner: "1234567890", 
        size: "100kb",
        hash:"qwer", 
        type: 1, 
        status: "1",
        produceTime:"2021:10:01:15:00", 
        modifiedTime:"2021:10:01:15:00",
        dataStart: 1 ,
        dataEnd: 1,
        dataBottom: 1,
        dataPeak: 1,
        dataPrecision: 1,
        dataCycle: 1,
        dataRate: 1,
        dataResult: 1
      },
      listLoading: true,
      listQuery: {
        boundary: '0207',
        page: 1,
        limit: 20,
        importance: undefined,
        title: undefined,
        type: undefined,
      },
      importanceOptions: ['正常','已删除'],
      calendarTypeOptions,
      statusOptions,
      statusOptions,
      temp: {
        id: 1,
        name: "a.txt",
        timestamp:"2021:10:01:15:00",
        status:"1",
        author:"1234567890"
      },
      dialogDetailVisible: false,
      dialogStatus: '',
      textMap: {
        update: 'Edit',
        create: 'Create'
      },
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
      this.list = [{fileId: 1, name: "a.txt", url: "/aaa/bbb", owner: "1234567890", size: "100kb",hash:"qwer", type: 1, status: 0, produceTime:"2021:10:01:15:00", modifiedTime:"2021:10:01:15:00"}]
      this.total = 1
      // fetchList(this.listQuery).then(response => {
      //   console.log(response.data)
      //   console.log(response)
      //   this.list = response.data.data
      //   this.total = 1
      //   setTimeout(() => {
      //     this.listLoading = false
      //   }, 1.5 * 1000)
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
    handleDetail(row) {
      this.dialogDetailVisible = true
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
<style>
.file_table td{
  height: 30px;
  /* background-color: beige; */
  border-style:none none solid none;
  border-color:#c5c5c5;
  border-width:2px;
}
</style>