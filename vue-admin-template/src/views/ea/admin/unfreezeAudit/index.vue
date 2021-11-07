<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.key" placeholder="用户名" style="width: 300px;" class="filter-item"
                @keyup.enter.native="handleFilter"/>
      <el-button v-waves class="filter-item" style="margin-left: 0px; " type="primary" icon="el-icon-search"
                 @click="handleFilter">
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
      style="width: 100%;">
      <el-table-column label="申请id" width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column label="用户名" width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.username }}</span>
        </template>
      </el-table-column>
      <el-table-column label="邮箱" align="center" width="200px">
        <template slot-scope="{row}">
          <span>{{ row.email }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申请理由" width="400px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.applicationReason }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申请时间" width="200px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.applicationTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" min-width="200px" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="mini" @click="receive(row)">
            通过
          </el-button>
          <el-button type="danger" size="mini" @click="refuseOpen(row)">
            拒绝
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


    <!-- 冻结理由模态框 -->
    <el-dialog title="拒绝申请" :visible.sync="refuseDialog">
      <el-form :model="refuseData" :rules="refuseRules" ref="refuseData">
        <el-form-item label="理由" prop="refuseReason" :label-width="formLabelWidth">
          <el-input v-model="refuseData.refuseReason" type="text" auto-complete="false"
                    placeholder="请输入拒绝理由"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="refuseDialog = false">取 消</el-button>
        <el-button type="primary" @click="refuse">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import {parseTime} from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

export default {
  name: 'UnfreezeAudit',
  components: {Pagination},
  directives: {waves},
  data() {
    return {
      formLabelWidth: '70px',
      tableKey: 0,
      list: null,
      total: 0,
      refuseData: {
        id: '',
        refuseReason: ''
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
      refuseDialog: false,
      addDialog: false,
      rules: {
        username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
        password: [{required: true, message: '请输入密码', trigger: 'blur'}],
        email: [{required: true, message: '请输入邮箱', trigger: 'blur'}]
      },
      refuseRules: {
        refuseReason: [{required: true, message: '请输入拒绝理由', trigger: 'blur'}]
      }
    }
  },
  created() {
    this.getList('/admin/queryUnfreezeApply', '1303')
  },
  methods: {
    handleSizeChange(val) {
      this.listQuery.pageSize = val,
        this.getList('/admin/queryUnfreezeApply', '1303')
    },
    handleCurrentChange(val) {
      this.listQuery.currentPage = val,
        this.getList('/admin/queryUnfreezeApply', '1303')
    },
    // 查询解冻申请列表
    getList(path, b) {
      this.loading = true
      this.listQuery.boundary = b
      this.postRequest(path, this.listQuery).then(resp => {
        if (resp) {
          this.list = resp.unfreezes
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
      this.getList('/admin/queryUnfreezeApply', '1303')
    },
    receive(row) {
      // 更新用户状态
      this.postRequest('/admin/updateUserStatus', {
        boundary: '1202',
        username: row.username,
        status: 1
      })
      // 更新处理状态
      this.postRequest('/admin/updateHandleStatus', {
        boundary: '1302',
        id: row.id,
        handleStatus: '1'
      }).then(resp => {
        if (resp) {
          this.getList('/admin/queryUnfreezeApply', '1303')
        }
      })
    },
    refuse() {
      this.$refs.refuseData.validate(async (valid) => {
        if (valid) {
          // 更新处理状态
          this.postRequest('/admin/updateHandleStatus', {
            boundary: '1302',
            id: this.refuseData.id,
            handleStatus: '2',
            refuseReason: this.refuseData.refuseReason
          }).then(resp => {
            if (resp) {
              this.getList('/admin/queryUnfreezeApply', '1303')
            }
          })
          this.refuseDialog = false
        } else {
          this.$message.error('请正确填写信息哦')
          return false
        }
      })
    },
    refuseOpen(row) {
      this.refuseData.id = row.id
      this.refuseData.refuseReason = ''
      this.refuseDialog = true
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
.file_table td {
  height: 30px;
  /* background-color: beige; */
  border-style: none none solid none;
  border-color: #c5c5c5;
  border-width: 2px;
}

.upload-demo {
}
</style>


