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
      <el-table-column label="时间" min-width="110px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.time }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作者" width="100px" align="center">
        <template slot-scope="{row}">
          <span >{{ row.user }}</span>
        </template>
      </el-table-column>
      <el-table-column label="级别" width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.level }}</span>
        </template>
      </el-table-column>
      <el-table-column label="所属" width="120px">
        <template slot-scope="{row}">
            <span>{{ row.type | typeFilter}}</span>
        </template>
      </el-table-column>
      <el-table-column label="日志信息" align="center" min-width="180px">
        <template slot-scope="{row}">
          <span>{{ row.message }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="230" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="mini" @click="handledetail(row,$index)">
            详情
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
        <tr  align="center"><td>id</td><td>{{ data.id }}</td></tr>
        <tr align="center"><td>message</td> <td>{{ data.message }}</td></tr>
        <tr align="center"><td>level</td> <td>{{ data.level }}</td></tr>
        <tr align="center"><td>user</td> <td>{{ data.user }}</td></tr>
        <tr align="center"><td>recorder</td> <td>{{ data.recorder }}</td></tr>
        <tr align="center"><td>type</td> <td>{{ data.type | typeFilter}}</td></tr>  
        <tr align="center"><td>time</td> <td>{{data.time }}</td></tr>
        <tr align="center"><td>isFile</td> <td>{{ data.isFile | fileFilter}}</td></tr>
        <tr align="center"><td>fileType</td> <td>{{ data.fileType | fileTypeFilter}}</td></tr>
        <tr align="center"><td>boundary</td> <td>{{ data.boundary }}</td></tr>
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
import { fetchList } from '@/api/operation'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

const calendarTypeOptions = [
  { key: '最近三天' },
  { key: '最近一周' }
]

const typeOptions = [
  { key: '系统日志' },
  { key: '研究员日志' },
  { key: '管理员日志' },
  { key: '超级管理员日志' }
]

const fileOptions = [
  { key : '否' },
  { key : '是' }
]

const fileTypeOptions = [
  { key : '上传' },
  { key : '移除' },
  { key : '还原' },
  { key : '删除' },
  { key : '导出' }
]

export default {
  name: 'FileManage',
  components: { Pagination },
  directives: { waves },
  filters: {
    //类型过滤
     //类型过滤
    typeFilter:function (type) {
      return typeOptions[type].key
    },
    fileFilter:function (type) {
      return fileOptions[type].key
    },
    fileTypeFilter:function (type) {
      return fileTypeOptions[type].key
    }
  },
  data() {
    return {
      tableKey: 0,
      list: [{id: 1,message: "下载文件", level:"INFO", user:"1234567890", recorder: "SearchService.download", type: 1, time: "2021:10:01:15:00",isFile: 1,fileType: 1,boundary: "0101"}],
      total: 1,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        importance: undefined,
        title: undefined,
        type: undefined,
      },
      importanceOptions: ['正常','已删除'],
      calendarTypeOptions,
      statusOptions: ['正常', '删除'],

      data: {
        id: 1,
        message: "下载文件", 
        level:"INFO", 
        user:"1234567890", 
        recorder: "SearchService.download", 
        type: 1, 
        time: "2021:10:01:15:00",
        isFile: 1,
        fileType: 1,
        boundary: "0101"
      },
      dialogDetailVisible: false,
      dialogStatus: '',
      textMap: {
        update: 'Edit',
        create: 'Create'
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
      this.list = [{id: 1,message: "下载文件", level:"INFO", user:"1234567890", recorder: "SearchService.download", type: 1, time: "2021:10:01:15:00",isFile: 1,fileType: 1,boundary: "0101"}]
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
    handledetail(row, index) {
       this.data.data = row
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
