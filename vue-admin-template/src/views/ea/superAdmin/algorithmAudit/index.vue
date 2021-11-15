<template>
  <div class="app-container">
    <!--输入框那一行-->
    <div class="filter-container">
      <!--输入搜索框-->
      <el-input v-model="listQuery.title" placeholder="请输入关键字" style="width: 300px;" class="filter-item" @keyup.enter.native="handleAlgorithmSend" />
      <!--搜索按钮-->
      <el-button v-waves class="filter-item" style="margin-left: 20px; "  type="primary" icon="el-icon-search" @click="handleAlgorithmSend">
        搜索
      </el-button>
    </div>
    <!--表格-->
    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      border fit
      highlight-current-row
      style="width: 100%;"
    >
      <el-table-column label="ID" prop="id" align="center" width="40">
        <template slot-scope="{row}">
          <span>{{ row.algSendId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申请者" width="110px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.senderNickname }}</span>
        </template>
      </el-table-column>
      <el-table-column label="原算法名" width="110px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.algorithmName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申请类型" min-width="20px" align="center">
        <template slot-scope="{row}">
          <span v-if="0 == row.sendType"><el-button type="primary" plain disabled>添加算法</el-button></span>
          <span v-if="-1 == row.sendType"><el-button type="primary" plain disabled>删除算法</el-button></span>
          <span v-if="1 <= row.sendType"><el-button type="primary" plain disabled>修改算法</el-button></span>
        </template>
      </el-table-column>
      <el-table-column label="申请时间" min-width="20px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.applyTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="230px" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="mini" @click="handleDetail(row)">
            审核
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--分页-->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage4"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="10"
      background
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
    <!--文件详情对话框-->
    <el-dialog :visible.sync="dialogDetailVisible">
      <table
        fit
        highlight-current-row
        style="width: 100%;" class="file_table">
        <tr align="center"><td>申请者</td><td>{{ detail.senderNickname }}</td></tr>
        <tr align="center"><td>申请时间</td> <td>{{ detail.applyTime }}</td></tr>
        <tr align="center"><td>申请类型</td>
          <td>
              <span v-if="0 == detail.sendType">添加算法</span>
              <span v-if="-1 == detail.sendType">删除算法</span>
              <span v-if="1 <= detail.sendType">修改算法</span>
          </td>
        </tr>
        <tr align="center"><td>算法名称</td> <td>{{ detail.algorithmName }}</td></tr>
        <tr align="center"><td>原算法上传者</td> <td>{{ detail.createrNickname }}</td></tr>
        <tr align="center"><td>算法上传时间</td> <td>{{ detail.createdTime }}</td></tr>
        <tr align="center"><td>上次修改时间</td> <td>{{ detail.changeTime }}</td></tr>
        <tr align="center"><td>算法内容</td> <td><textarea style="width: 468px; height: 209px" disabled v-bind:value="detail.algCode"></textarea></td></tr>
      </table>
      <div slot="footer" class="dialog-footer">
        <el-button type="success" @click="ruling(1)">
          通过
        </el-button>
        <el-button type="info" @click="ruling(0)">
          不通过
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

const statusOptions = [
  { key: '正常' },
  { key: '被删除' }
];

const typeOptions = [{key:'CV'},{key:'PDV'}];

export default {
  name: "AlgorithmAudit",
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
      list: null,
      total: 0,
      // 懒加载的数据
      detail: {algSendId: null, algId: null, senderNickname: null,applyTime: null,algorithmName: null,sendType: null,algCode: null, createrNickname: null, createdTime: null, changeTime: null},
      listLoading: true,
      loadingSet:{
        boundary: '0403',
        algorithmId: undefined
      },
      rulingSet:{
        boundary: '0803',
        rulingResult: 0,
        algorithmId: undefined,
        algorithmSendId: undefined,
        sendType: undefined
      },
      listQuery: {
        boundary: '0802',
        page: 1,
        limit: 10,
        title: undefined,
        type: 1,
        status: 1
      },
      importanceOptions: ['正常','已删除'],
      /*calendarTypeOptions,*/
      statusOptions,
      dialogDetailVisible: false,
      dialogStatus: '',
      downloadLoading: false
    }
  },
  created() {
    this.getList('/algorithm/list','0802')
  },
  methods: {
    handleSizeChange(val) {
      this.listQuery.limit=val;
      this.getList('/algorithmSend/list','0802')
    },
    handleCurrentChange(val) {
      this.listQuery.page=val;
      this.getList('/algorithmSend/list','0802')
    },
    getList(path,b) {
      this.loading = true;
      this.listQuery.boundary = b;
      this.postRequest(path, this.listQuery).then(response => {
        if (response) {
          this.list = response.data;
          console.log(this.list);
          this.total = response.length;
        }
      });
      this.listLoading = false;
      setTimeout(() => {
        this.loading = false
      }, 750)
    },
    handleAlgorithmSend() {
      this.listQuery.page = 1;
      this.getList('/algorithmSend/search','0801')
    },
    handleModifyStatus(row, status) {
      this.$message({
        message: '操作Success',
        type: 'success'
      });
      row.status = status
    },
    // 显示细节
    handleDetail(row) {
      this.detail = row;
      this.loadingSet.algorithmId = row.algId;
      this.postRequest('/algorithm/loadingCode', this.loadingSet).then(response => {
        if (response) {
          this.detail.algCode = response.algCode;
          this.dialogDetailVisible = true;
          console.log(this.detail.algCode);
        }
      });
    },
    //审核通过与否，1通过，0未通过
    ruling(result){
      this.dialogDetailVisible = false;
      this.rulingSet.rulingResult = result;
      this.rulingSet.algorithmId = this.detail.algId;
      this.rulingSet.algorithmSendId = this.detail.algSendId;
      this.rulingSet.sendType = this.detail.sendType;
      this.postRequest('/algorithmSend/judgementAlgorithmSend', this.rulingSet).then(response => {
        if (response) {
          console.log(response.messsage);
        }
      });
      setTimeout(() => {
        this.getList('/algorithm/list','0802')
      }, 100)
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

<style scoped>

</style>
