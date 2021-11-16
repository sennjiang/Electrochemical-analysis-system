<template>
  <div class="app-container">
    <div class="filter-container">

      <el-button v-waves class="filter-item" style="margin-left: 20px; "  type="primary" icon="el-icon-search" @click="handleFile">
        添加文件
      </el-button>

      <el-button class="filter-item" style="margin-left: 20px;" type="primary" icon="el-icon-edit" @click="handleDataDetail">
        展示数据
      </el-button>
      
    </div>
    
    <div style="margin-top: 20px;" v-if="showEcharts">
        <echarts></echarts>
    </div>
    
    <!-- <el-table

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
      <el-table-column label="文件名" width="120px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="文件大小" width="100px" align="center">
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
        <span> {{ row.status-1 | statusFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column label="所属者" align="center" width="120px">
        <template slot-scope="{row}">
          <span>{{ row.owner}}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="230" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button v-waves :loading="downloadLoading" size="mini" type="primary" icon="el-icon-download" @click="handleExport(row)">
        导出
      </el-button>
          <el-button type="primary" size="mini" @click="handleDetail(row)">
            详情
          </el-button>
          <el-button v-if="row.status!='deleted'" size="mini" type="danger" @click="handleDelete(row,$index)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table> -->
      
      <!-- <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage4"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="10"
      background=true
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination> -->

    <el-dialog :visible.sync="filesVisible" width="1000px">
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
      <el-table-column label="文件名" width="120px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="文件大小" width="100px" align="center">
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
        <span> {{ row.status-1 | statusFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column label="所属者" align="center" width="120px">
        <template slot-scope="{row}">
          <span>{{ row.owner}}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="230" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="mini" @click="handleCheck(row,$index)">
            选择
          </el-button>
        </template>
      </el-table-column>
    </el-table>
      <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage4"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="10"
      background=true
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="filesVisible = false">
          确认
        </el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="fileUploadVisible">
      <el-upload
          align="center"
          class="upload-demo"
          drag
          :action="fileUploadPath"
          multiple>
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div class="el-upload__tip" slot="tip">只能上传txt文件，且不超过500kb</div>
        </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="fileUploadVisible = false">
          确认
        </el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="dialogDetailVisible">
      <table
      fit
      highlight-current-row
      style="width: 100%;" class="file_table">
        <tr align="center"><td>属性名</td> <td>属性值</td></tr>
        <tr  align="center"><td>fileId</td><td>{{ detail.id }}</td></tr>
        <tr align="center"><td>name</td> <td>{{ detail.name }}</td></tr>
        <tr align="center"><td>url</td> <td>{{ detail.url }}</td></tr>
        <tr align="center"><td>owner</td> <td>{{ detail.owner }}</td></tr>
        <tr align="center"><td>size</td> <td>{{ detail.size }}</td></tr>
        <tr align="center"><td>hash</td> <td>{{ detail.hash }}</td></tr>
        <tr align="center"><td>type</td> <td>{{detail.type | typeFilter }}</td></tr>
        <tr align="center"><td>status</td> <td>{{ detail.status }}</td></tr>
        <tr align="center"><td>produceTime</td> <td>{{ detail.produceTime }}</td></tr>
        <tr align="center"><td>modifiedTime</td> <td>{{ detail.modifiedTime }}</td></tr>
        <tr align="center"><td>dataStart</td> <td>{{ detail.dataStart }}</td></tr>
        <tr align="center"><td>dataEnd </td> <td>{{ detail.dataEnd }}</td></tr>
        <tr align="center"><td>dataBottom </td><td>{{ detail.dataBottom }}</td></tr>
        <tr align="center"><td>dataPeak </td><td>{{ detail.dataPeak }}</td></tr>
        <tr align="center"><td>dataPrecision </td><td>{{ detail.dataPrecision }}</td></tr>
        <tr align="center"><td>dataCycle </td><td>{{ detail.dataCycle }}</td></tr>
        <tr align="center"><td>dataRate </td><td>{{ detail.dataRate }}</td></tr>
        <tr align="center"><td>dataResult </td><td>{{ detail.dataResult }}</td></tr>
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
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import echarts from "@/views/ea/user/electrochemicalAnalysis/echarts"

export default {
  name: 'ElectrochemicalAnalysis',
  components: { 
    Pagination,
    echarts
  },
  directives: { waves },
  data() {
    return {
      tableKey: 0,
      list: null,
      total: 0,
      // 懒加载的数据
      detail: {dataBottom: 0,dataCycle: 0,dataEnd: 0,dataPeak: 0,dataPrecision: 0,dataRate: 0,dataResult: 0,dataStart: 0,id: 1,modifiedTime: "Oct 8, 2021 4:38:09 PM",name: "a.txt",owner: 1234567890,produceTime: "Oct 8, 2021 4:38:09 PM",size: 100,status: 1,type: 1,url: "/qwe"},
      listLoading: true,
      listQuery: {
        boundary: '0208',
        page: 1,
        limit: 10,
        title: undefined,
        type: 1,
        status: 1
      },
      dataDetail: [120, 50, 60, 20, -100, 100, 70],
      detailV: null,
      detailA: null,
      fileUploadPath: 'http://localhost:8080/Electrochemical_Analysis_System_war/file/upload?boundary=0205&username='+this.$store.state.currentUsername,
      importanceOptions: ['正常','已删除'],
      dialogDetailVisible: false,
      fileUploadVisible : false,
      dialogStatus: '',
      downloadLoading: false,
      dataDtailVisible: false,
      filesVisible: false,
      showEcharts: false
    }
  },
  created() {
    this.getList('/file/list','0208')
  },
  methods: {
    handleChange(detail) {
      this.dataDtailVisible = false,
        this.detail = detail
      },
    handleSizeChange(val) {
        this.listQuery.limit=val,
        this.getList('/file/list','0208')
      },
      handleCurrentChange(val) {
      this.listQuery.page=val,
        this.getList('/file/list','0208')
    },
    getList(path,b) {
          this.loading = true
          this.listQuery.boundary = b
          this.postRequest(path, this.listQuery).then(response => {
            if (response) {
              this.list = response.data
              console.log(this.list)
              this.total = response.length
            }
          })
          this.listLoading = false
          setTimeout(() => {
            this.loading = false
          }, 750)
    },
    handleFile() {
      this.filesVisible = true
    },
    handleModifyStatus(row, status) {
      this.$message({
        message: '操作Success',
        type: 'success'
      })
      row.status = status
    },
    handleImport() {
      this.fileUploadVisible = true;
    },
    handleDataDetail() {
      this.dialogDetailVisible = true;
    },
    handleExport(row) {
      this.$message({
        message: 'Export TODO fileid ' + row.id,
        type: 'success'
      })
    },
    handleCheck(row,index) {
     let fileData = {boundary:"0215",fileId:row.id};
     this.getRequest('/file/detail', fileData).then(response => {
            if (response) {
              this.detailV = response.detailV
              this.detailA = response.detailA.map(Number)
              this.detail = this.list[index]
              this.filesVisible = false 
              console.log(this.detailA)
              this.$bus.$emit("sendAV", this.detailA, this.detailV);
              this.showEcharts = true;
            }
          })
         
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
</style>
