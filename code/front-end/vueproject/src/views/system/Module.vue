/**
 * 系统管理 菜单管理
 */
<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>菜单管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 主体内容 -->
  </div>
</template>

<script>
</script>

<style>
.user-search {
  margin-top: 20px !important;
}
.user-search td {
  margin-top: 20px !important;
}
.userRole {
  width: 100%;
}
.el-table__expanded-cell {
  padding: 0px !important;
  margin: 0px !important;
}
.modt-box {
  width: 100%;
  height: 60px;
  line-height: 60px;
  background-color: #f0f2f5;
  text-align: center;
  color: #334157;
  margin-top: 20px;
  margin-bottom: 20px;
  font-size: 24px;
}
.mod-btnbox {
  margin-bottom: 20px;
}
.treeclass {
  border: 1px solid #f3f3f3;
  padding-top: 20px;
  padding-bottom: 20px;
}
.selectw {
  width: 100%;
}
</style>
 
 