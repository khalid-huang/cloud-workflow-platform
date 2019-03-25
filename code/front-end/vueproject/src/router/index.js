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
// 工作项管理
import workitem from '@/views/workitem/workitem';

import human from '@/views/organization/human'

import brole from '@/views/brole/brole'

import mapping from '@/views/brole/mapping'

import model from '@/views/process/model'
import editor from '@/views/process/editor'
import started from '@/views/workitem/started'

import definition from '@/views/process/definition'

import instance from '@/views/process/instance'

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
            path: '/process/instance',
            name: '流程实例',
            component: instance,
            meta: {
                requireAuth: true
            }
        },{
            path: '/process/definition',
            name: '流程定义',
            component: definition,
            meta: {
                requireAuth: true
            }
        },{
            path: '/process/model',
            name: '流程模型',
            component: editor,
            meta: {
                requireAuth: true
            }
        },
        {
            path: '/organization/human',
            name: '人员',
            component: human,
            meta: {
                requireAuth: true
            }
        },
        {
            path: '/brole/list',
            name: '业务角色',
            component: brole,
            meta: {
                requireAuth: true
            }
        },
        {
            path: '/brolemapping/list',
            name: '业务角色映射',
            component: mapping,
            meta: {
                requireAuth: true
            }
        },             
        {
            path: '/workitem/started',
            name: 'Started',
            component: started,
            meta: {
                requireAuth: true
            }            
        }, 
        {
            path: '/workitem/manage',
            name: '工作项管理',
            component: workitem,
            meta: {
                requireAuth: true
            }
        }]
    }]
})