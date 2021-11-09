<template>
  <div class="app-container">
    <!--输入框那一行-->
    <div class="filter-container">
      <!--输入搜索框-->
      <el-input v-model="listQuery.title" placeholder="请输入关键字" style="width: 300px;" class="filter-item" @keyup.enter.native="handleAlgorithm" />
      <!--搜索按钮-->
      <el-button v-waves class="filter-item" style="margin-left: 20px; "  type="primary" icon="el-icon-search" @click="handleAlgorithm">
        搜索
      </el-button>
      <!--添加按钮-->
      <el-button class="filter-item" style="margin-left: 610px;" type="primary" icon="el-icon-edit" @click="handleImport">
        上传算法
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
          <span>{{ row.algId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="算法名" width="110px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.algorithmName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="算法上传者" width="110px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.nickname }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" min-width="20px" align="center">
        <template slot-scope="{row}">
          <span >{{ row.createdTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="算法类型" min-width="20px" align="center">
        <template slot-scope="{row}">
          <span v-if="1 == row.classification">平滑处理</span>
          <span v-else-if="2 == row.classification">滤波处理</span>
          <span v-else-if="3 == row.classification">CV伏安法</span>
          <span v-else-if="4 == row.classification">DPV</span>
          <span v-else-if="5 == row.classification">SWV</span>
          <span v-else-if="6 == row.classification">LSV</span>
          <span v-else>未知</span>
        </template>
      </el-table-column>
      <el-table-column label="上次修改时间" min-width="20px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.changeTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否启用" min-width="20px" align="center">
        <template slot-scope="{row}">
          <span v-if="1 == row.isUsed"><el-tag type="success">启用</el-tag></span>
          <span v-if="2 == row.isUsed"><el-tag type="info">未启用</el-tag></span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="230px" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="mini" @click="handleDetail(row)">
            详情
          </el-button>
          <!--TODO 单击事件记得修改-->
          <el-button type="primary" size="mini" @click="handleDetail(row)">
            修改
          </el-button>
          <el-button v-if="row.status!='deleted'" size="mini" type="danger" @click="handleDelete(row,$index)">
            删除
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
    <!--算法上传对话框-->
    <el-dialog :visible.sync="algorithmUploadVisible">
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
        <el-button type="primary" @click="this.$router.go(0)">
          确认
        </el-button>
      </div>
    </el-dialog>
    <!--文件详情对话框-->
    <el-dialog :visible.sync="dialogDetailVisible">
      <table
        fit
        highlight-current-row
        style="width: 100%;" class="file_table">
        <tr align="center"><td>属性名</td> <td>属性值</td></tr>
        <tr  align="center"><td>算法名称</td><td>{{ detail.algorithmName }}</td></tr>
        <tr align="center"><td>上传者昵称</td> <td>{{ detail.nickname }}</td></tr>
        <tr align="center"><td>上传时间</td> <td>{{ detail.createdTime }}</td></tr>
        <tr align="center"><td>上一次修改时间</td> <td>{{ detail.changeTime }}</td></tr>
        <tr align="center"><td>算法代码</td> <td><textarea style="width: 468px; height: 209px" disabled v-bind:value="detail.algCode"></textarea></td></tr><!--todo 算法代码懒加载-->
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

const statusOptions = [
  { key: '正常' },
  { key: '被删除' }
];

const typeOptions = [{key:'CV'},{key:'PDV'}];

export default {
  name: 'AlgorithmManage',
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
      detail: {algorithmName: null,nickname: null,createdTime: null,changeTime: null,algCode: null},
      owner: '',
      listLoading: true,
      loadingSet:{
        boundary: '0403',
        algorithmId: undefined
      },
      listQuery: {
        boundary: '0404',
        page: 1,
        limit: 10,
        title: undefined,
        type: 1,
        status: 1
      },
      //TODO 算法类型待修改
      fileUploadPath: 'http://localhost:8080/Electrochemical_Analysis_System_war/algorithm/addAlgorithm?boundary=0406&username='
        +this.$store.state.currentUsername,
      importanceOptions: ['正常','已删除'],
      /*calendarTypeOptions,*/
      statusOptions,
      dialogDetailVisible: false,
      algorithmUploadVisible : false,
      dialogStatus: '',
      downloadLoading: false
    }
  },
  created() {
    this.getList('/algorithm/list','0404')
  },
  methods: {
    handleChange(file, fileList) {
      this.fileList = fileList.slice(-3);
    },
    handleSizeChange(val) {
      this.listQuery.limit=val;
        this.getList('/algorithm/list','0404')
    },
    handleCurrentChange(val) {
      this.listQuery.page=val;
        this.getList('/algorithm/list','0404')
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
    handleAlgorithm() {
      this.listQuery.page = 1;
      this.getList('/algorithm/search','0407')
    },
    handleModifyStatus(row, status) {
      this.$message({
        message: '操作Success',
        type: 'success'
      });
      row.status = status
    },
    handleImport() {
      this.algorithmUploadVisible = true;
    },
    //懒加载
    handleDetail(row) {
      this.detail = row;
      this.loadingSet.algorithmId = row.algId;
      this.postRequest('/algorithm/loadingCode', this.loadingSet).then(response => {
        if (response) {
          this.detail.algCode = response.algCode;
          console.log(this.detail.algCode);
        }
      });
      setTimeout(() => {
        this.dialogDetailVisible = true;
      }, 70)
    },
    /*删除算法，这里的删除是提交删除申请*/
    handleDelete(row, index) {
      /*type:是申请的类型，-1代表删除*/
      let deleteData = {boundary:"0804", algorithmId:row.algId, type:"-1", username:this.$store.state.currentUsername};
      this.getRequest('/algorithmSend/addAlgorithmSend', deleteData).then(response => {
        if (response) {
          this.getList('/algorithm/list','0404')
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

<style scoped>

</style>
