/**
 * 基础菜单 模型管理
 */
<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>流程管理</el-breadcrumb-item>
      <el-breadcrumb-item>流程模型</el-breadcrumb-item>
    </el-breadcrumb>
    <br>
    <iframe v-show="true" id="show-iframe"  frameborder=0 name="showHere" scrolling=auto src="http://222.200.180.59:20002/editor?modelId=295023" width="1250" height="600" allowtransparency="true" allowfullscreen="true"></iframe>
  </div>
</template>

<script>
import { ModelList, ModelNew , ModelDelete, ModelDeploy, ModelEdit} from '../../api/basisMG'
import Pagination from '../../components/Pagination'
import axios from 'axios';
export default {
  data() {
    return {
      nshow: true, //switch开启
      fshow: false, //switch关闭
      loading: false, //是显示加载
      editFormVisible: false, //控制编辑页面显示与隐藏
      title: '添加',
      editForm: {
        ModelName: '',
        ModelDescription: '',
        ModelKey: '',
        // token: localStorage.getItem('logintoken')
      },
      // rules表单验证
      rules: {
        ModelName: [
          { required: true, message: '请输入模型名称', trigger: 'blur' }
        ],
        ModelDescription: [{ required: false, message: '请输入模型描述', trigger: 'blur' }],
        ModelKey: [{ required: true, message: '请输入key', trigger: 'blur' }]
      },
      formInline: {
        page: 1,
        limit: 10,
        varLable: '',
        varName: '',
        token: localStorage.getItem('logintoken')
      },
      // 删除部门
      seletedata: {
        ids: '',
        token: localStorage.getItem('logintoken')
      },
      userparm: [], //搜索权限
      listData: [], //用户数据
      // 分页参数
      pageparm: {
        currentPage: 1,
        pageSize: 10,
        total: 10
      }
    }
  },
  // 注册组件
  components: {
    Pagination
  },
  /**
   * 数据发生改变
   */

  /**
   * 创建完毕
   */
  created() {
    this.getdata(this.formInline)
  },

  methods: {
    // 获取模型列表
     getdata(parameter) {
      this.loading = true

      ModelList(parameter)
        .then(res => {
          this.loading = false
          if (res.success == false) {
            this.$message({
              type: 'info',
              message: res.msg
            })
          } else {
            console.log(res)
            this.listData = res.models
            // 分页赋值
            this.pageparm.currentPage = this.formInline.page
            this.pageparm.pageSize = this.formInline.limit
            this.pageparm.total = res.count
          }
        })
        .catch(err => {
          this.loading = false
          this.$message.error('菜单加载失败，请稍后再试！')
        })
     },
    // 分页插件事件
    callFather(parm) {
      this.formInline.page = parm.currentPage
      this.formInline.limit = parm.pageSize
      this.getdata(this.formInline)
    },
    // 搜索事件
    search() {
      this.getdata(this.formInline)
    },
    // 新建模型
    New(editForm) {
      ModelNew(editForm.ModelName, editForm.ModelDescription, editForm.ModelKey)
        .then(res => {
          this.loading = false
          if (res.success == false) {
            this.$message({
              type: 'info',
              message: res.msg
            })
          } else {
            console.log(res)
            this.listData = res.models
            // 分页赋值
            this.pageparm.currentPage = this.formInline.page
            this.pageparm.pageSize = this.formInline.limit
            this.pageparm.total = res.count
          }
        })
        .catch(err => {
          this.loading = false
          this.$message.error('新建模型失败，请稍后再试！')
        })
    },
    //显示编辑界面
    handleEdit(index, row) {
      ModelEdit()
        .then(res => {
          if (res.success == false) {
            this.$message({
              type: 'info',
              message: res.msg
            })
          } else {
            console.log(res)
          }
        })
        .catch(err => {
          this.loading = false
          this.$message.error('编辑模型失败，请稍后再试！')
        })
    },
    // 编辑、增加页面保存方法
    submitForm(editData) {
      
    },
    // 删除模型
    deleteModel(index, row) {
      this.$confirm('确定要删除改模型吗?', '信息', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          ModelDelete(row.id)
            .then(res => {
              if (res.status == "success") {
                this.$message({
                  type: 'success',
                  message: '模型已删除!'
                })
                this.getdata(this.formInline)
                console.log("1")
              } else {
                this.$message({
                  type: 'info',
                  message: res.msg
                })
                console.log("2")
              }
            })
            .catch(err => {
              this.loading = false
              this.$message.error('模型删除失败，请稍后再试！')
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    // 关闭编辑、增加弹出框
    closeDialog() {
      this.editFormVisible = false
    }
  }
}
</script>

<style scoped>
.user-search {
  margin-top: 20px;
}
.userRole {
  width: 100%;
}
</style>

 
 