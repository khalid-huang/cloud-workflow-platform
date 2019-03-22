import axios from 'axios';
import { req } from './axiosFun';

/**
 * 流程实例管理
 **/
// 流程实例管理-获取流程实例管理列表
export const GoodsList = (params) => { return req("get", "http://222.200.180.59:20002/models/getAll", params) };
// 流程实例管理-保存流程实例
export const GoodsSave = (params) => { return req("post", "/api/Goods/save", params) };
// 流程实例管理-删除流程实例
export const GoodsDelete = (params) => { return axios.delete("http://222.200.180.59:20002/models/delete?ids=" + params + "&token=" + localStorage.getItem('logintoken')).then(res => res.data) };

/**
 * 模型管理
 **/
// 模型管理-获取所有模型
export const ModelList = (params) => { return req("get", "http://222.200.180.59:20002/models/getAll", params) };
// 模型管理-部署模型
export const ModelDeploy = (params) => { return req("post", "http://222.200.180.59:20002/models/deploy/" + params) };
// 模型管理-删除模型
export const ModelDelete = (params) => { return axios.delete("http://222.200.180.59:20002/models/delete/" + params).then(res => res.data) };
// 模型管理-新建模型
export const ModelNew = (params1, params2, params3) => { return req("post", "http://222.200.180.59:20002/models/newModel?modelName=" + params1 + "&description=" + params2 + "&key=" + params3) };
// 模型管理-进入模型编辑器
export const ModelEdit = (params) => { return req("get", "http://222.200.180.59:20002/editor", params) };


/**
 * 机器信息管理 
 **/
// 机器信息管理-获取机器信息管理列表
export const MachineList = (params) => { return req("post", "/api/Machine/list", params) };
// 机器信息管理-保存机器信息管理
export const MachineSave = (params) => { return req("post", "/api/Machine/save", params) };
// 机器信息管理-删除机器信息管理
export const MachineDelete = (params) => { return axios.delete("/api/Machine/delete?ids=" + params + "&token=" + localStorage.getItem('logintoken')).then(res => res.data) };

/**
 * 货道信息管理
 **/
// 货道信息管理-获取获取货道信息管理列表
export const MachineAisleList = (params) => { return req("post", "/api/MachineAisle/list", params) };
// 货道信息管理-删除货道信息管理
export const MachineAisleDelete = (params) => { return axios.delete("/api/MachineAisle/delete?ids=" + params + "&token=" + localStorage.getItem('logintoken')).then(res => res.data) };
// 货道信息管理-保存货道信息管理
export const MachineAisleRsave = (params) => { return req("post", "/api/MachineAisle/save", params) };