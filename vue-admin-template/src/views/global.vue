<template>

</template>

<script>
import Layout from "@/layout";

const map = new Map([
  ['101', {
    path: '/superAdmin/adminManage',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'AdminManage',
        component: () => import('@/views/ea/superAdmin/adminManage/index'),
        meta: {title: '管理员管理', icon: 'user'}
      }
    ]
  }],
  ['102', {
    path: '/superAdmin/operationRecord',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'OperationRecord',
        component: () => import('@/views/ea/superAdmin/operationRecord/index'),
        meta: {title: '操作记录管理', icon: 'user'}
      }
    ]
  }],
  ['103', {
    path: '/superAdmin/systemBackup',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'SystemBackup',
        component: () => import('@/views/ea/superAdmin/systemBackup/index'),
        meta: {title: '系统备份', icon: 'user'}
      }
    ]
  }],
  ['104', {
    path: '/superAdmin/systemRestore',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'SystemRestore',
        component: () => import('@/views/ea/superAdmin/systemRestore/index'),
        meta: {title: '系统还原', icon: 'user'}
      }
    ]
  }],
  ['105', {
    path: '/superAdmin/algorithmAudit',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'AlgorithmAudit',
        component: () => import('@/views/ea/superAdmin/algorithmAudit/index'),
        meta: {title: '算法审核', icon: 'user'}
      }
    ]
  }],

  ['106', {
    path: '/superAdmin/roleManage',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'RoleManage',
        component: () => import('@/views/ea/superAdmin/roleManage/index'),
        meta: {title: '角色管理', icon: 'user'}
      }
    ]
  }],
  ['201', {
    path: '/admin/userManage',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'UserManage',
        component: () => import('@/views/ea/admin/userManage/index'),
        meta: {title: '用户管理', icon: 'user'}
      }
    ]
  }],
  ['202', {
    path: '/admin/unfreezeAudit',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'UnfreezeAudit',
        component: () => import('@/views/ea/admin/unfreezeAudit/index'),
        meta: {title: '解冻审核', icon: 'user'}
      }
    ]
  }],
  ['203', {
    path: '/admin/fileManage',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'FileManage',
        component: () => import('@/views/ea/admin/fileManage/index'),
        meta: {title: '文件管理', icon: 'user'}
      }
    ]
  }],
  ['204', {
    path: '/admin/operatingRecord',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'OperatingRecord',
        component: () => import('@/views/ea/admin/operatingRecord/index'),
        meta: {title: '操作记录', icon: 'user'}
      }
    ]
  }],
  ['205', {
    path: '/admin/algorithmManage',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'AlgorithmManage',
        component: () => import('@/views/ea/admin/algorithmManage/index'),
        meta: {title: '算法管理', icon: 'user'}
      }
    ]
  }],
  ['301', {
    path: '/user/electrochemicalAnalysis',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'ElectrochemicalAnalysis',
        component: () => import('@/views/ea/user/electrochemicalAnalysis/index'),
        meta: {title: '电化学分析', icon: 'user'}
      }
    ]
  }],
  ['302', {
    path: '/user/fileManage',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'FileManage',
        component: () => import('@/views/ea/user/fileManage/index'),
        meta: {title: '文件管理', icon: 'user'}
      }
    ]
  }],
  ['303', {
    path: '/user/recycleManage',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'RecycleManage',
        component: () => import('@/views/ea/user/recycleManage/index'),
        meta: {title: '回收站管理', icon: 'user'}
      }
    ]
  }],
])

export default
{
  map
}
</script>

<style scoped>

</style>
