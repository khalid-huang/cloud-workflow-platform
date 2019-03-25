/**
* 左边菜单
*/ 
<template>
  <el-menu default-active="2" :collapse="collapsed" collapse-transition router :default-active="$route.path" unique-opened class="el-menu-vertical-demo" background-color="#334157" text-color="#fff" active-text-color="#ffd04b">
    <div class="logobox">
      <img class="logoimg" src="../assets/img/logo.jpg" alt="">
    </div>
    <el-submenu v-for="menu in allmenu" :key="menu.menuid" :index="menu.menuname">
      <template slot="title">
        <i class="iconfont" :class="menu.icon"></i>
        <span>{{menu.menuname}}</span>
      </template>
      <el-menu-item-group>
        <el-menu-item v-for="chmenu in menu.menus" :index="'/'+chmenu.url" :key="chmenu.menuid">
          <i class="iconfont" :class="chmenu.icon"></i>
          <span>{{chmenu.menuname}}</span>
        </el-menu-item>
      </el-menu-item-group>
    </el-submenu>
  </el-menu>
</template>
<script>
import { menu } from '../api/userMG'
export default {
  name: 'leftnav',
  data() {
    return {
      collapsed: false,
      allmenu: []
    }
  },
  // 创建完毕状态(里面是操作)
  created() {
    // 获取图形验证码
    let res = {
      success: true,
      data: [
        {
          menuid: 1,
          icon: 'li-icon-xiangmuguanli',
          menuname: '流程管理',
          hasThird: null,
          url: null,
          menus: [
            {
              menuid: 13,
              icon: 'icon-cat-skuQuery',
              menuname: '流程模型',
              hasThird: 'N',
              url: 'process/model',
              menus: null
            },
            {
              menuid: 11,
              icon: 'icon-cat-skuQuery',
              menuname: '流程定义',
              hasThird: 'N',
              url: 'process/definition',
              menus: null
            },
            {
              menuid: 12,
              icon: 'icon-cat-skuQuery',
              menuname: '流程实例',
              hasThird: 'N',
              url: 'process/instance',
              menus: null
            }
          ]
        },
        {
          menuid: 33,
          icon: 'li-icon-dingdanguanli',
          menuname: '组织结构管理',
          hasThird: null,
          url: null,
          menus: [
           {
              menuid: 34,
              icon: 'icon-order-manage',
              menuname: '人员',
              hasThird: 'N',
              url: 'organization/human',
              menus: null
            },            
            {
              menuid: 35,
              icon: 'icon-order-manage',
              menuname: '子组',
              hasThird: 'N',
              url: 'pay/Order',
              menus: null
            },
           {
              menuid: 36,
              icon: 'icon-order-manage',
              menuname: '职位',
              hasThird: 'N',
              url: 'pay/Order',
              menus: null
            },
           {
              menuid: 37,
              icon: 'icon-order-manage',
              menuname: '能力',
              hasThird: 'N',
              url: 'pay/Order',
              menus: null
            }                                   
          ]
        },
        {
          menuid: 40,
          icon: 'li-icon-dingdanguanli',
          menuname: '业务角色管理',
          hasThird: 'N',
          url: null,
          menus: [
           {
              menuid: 41,
              icon: 'icon-order-manage',
              menuname: '业务角色',
              hasThird: 'N',
              url: 'brole/list',
              menus: null
            },
           {
              menuid: 42,
              icon: 'icon-order-manage',
              menuname: '业务角色映射',
              hasThird: 'N',
              url: 'brolemapping/list',
              menus: null
            },                         
          ]
        },             
        {
          menuid: 50,
          icon: 'li-icon-dingdanguanli',
          menuname: '工作项管理',
          hasThird: null,
          url: null,
          menus: [
            {
              menuid: 51,
              icon: 'icon-order-manage',
              menuname: 'Offered',
              hasThird: 'N',
              url: 'workitem/manage',
              menus: null
            },
            {
              menuid: 52,
              icon: 'icon-order-manage',
              menuname: 'Allocated',
              hasThird: 'N',
              url: 'workitem/manage',
              menus: null
            },
            {
              menuid: 53,
              icon: 'icon-order-manage',
              menuname: 'Started',
              hasThird: 'N',
              url: 'workitem/started',
              menus: null
            },
            {
              menuid: 54,
              icon: 'icon-order-manage',
              menuname: 'Suspended',
              hasThird: 'N',
              url: 'workitem/manage',
              menus: null
            }                        
          ]
        },
        {
          menuid: 71,
          icon: 'li-icon-xitongguanli',
          menuname: '流程API管理',
          hasThird: null,
          url: null,
          menus: null
        }
      ],
      msg: 'success'
    }
          this.allmenu = res.data
    
    // menu(localStorage.getItem('logintoken'))
    //   .then(res => {
    //     console.log(JSON.stringify(res))
    //     if (res.success) {
    //       this.allmenu = res.data
    //     } else {
    //       this.$message.error(res.msg)
    //       return false
    //     }
    //   })
    //   .catch(err > {
    //     this.$message.error('菜单加载失败，请稍后再试！')
    //   })
    // 监听
    this.$root.Bus.$on('toggle', value => {
      this.collapsed = !value
    })
  }
}
</script>
<style>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 240px;
  min-height: 400px;
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  border: none;
  text-align: left;
}
.el-menu-item-group__title {
  padding: 0px;
}
.el-menu-bg {
  background-color: #1f2d3d !important;
}
.el-menu {
  border: none;
}
.logobox {
  height: 40px;
  line-height: 40px;
  color: #9d9d9d;
  font-size: 20px;
  text-align: center;
  padding: 20px 0px;
}
.logoimg {
  height: 60px;
}
</style>