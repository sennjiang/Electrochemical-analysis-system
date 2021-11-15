<template>
  <div>
    <el-form :model="backupForm" label-width="140px">
      <!-- 角色名 -->
      <el-form-item label="sql文件存储的路径" prop="filePath">
        <el-input v-model="backupForm.filePath" placeholder="D:/MysqlFile"></el-input>
      </el-form-item>
    </el-form>
    <el-button type="primary" @click="backup" >备份</el-button>
  </div>
</template>

<script>
    export default {
      name: "SystemBackup",

      data(){
        return{
          backupForm:{
            filePath:'D:/MysqlFile',
            boundary:'1501',
          },
        }
      },

      methods:{
        backup(){
          this.backupForm.boundary = '1501';
          this.postRequest("/system/Backup", this.backupForm).then(res => {

          })

        },
      }
    }


</script>

<style lang = "less" scoped>
  .el-breadcrumb{
    margin-bottom: 15px;
    font-size: 17px;
  }
</style>
