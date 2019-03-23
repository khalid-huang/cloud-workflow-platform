// 导入组件
import Vue from 'vue';
import Router from 'vue-router';
// 登录
import login from '@/views/login';
// 首页
import index from '@/views/index';
/**
 * 基础菜单
 */
// 流程管理
import Goods from '@/views/goods/Goods';
// 流程定义管理
import def from '@/views/goods/def';
// 模型管理
import models from '@/views/goods/models';

import tenantList from '@/views/tenant/list';

import tenantAdd from '@/views/tenant/add';

// 启用路由
Vue.use(Router);

// 导出路由 
export default new Router({
    routes: [{
        path: '/',
        name: '',
        component: login,
        hidden: true,
        meta: {
            requireAuth: false
        }
    }, {
        path: '/login',
        name: '登录',
        component: login,
        hidden: true,
        meta: {
            requireAuth: false
        }
    }, {
        path: '/index',
        name: '首页',
        component: index,
        iconCls: 'el-icon-tickets',
        children: [{
            path: '/tenant/list',
            name: '租户信息列表',
            component: tenantList,
            meta: {
                requireAuth: true
            }
        },{
            path: '/tenant/add',
            name: '新建租户',
            component: tenantAdd,
            meta: {
                requireAuth: true
            }
        }]
    }]
})