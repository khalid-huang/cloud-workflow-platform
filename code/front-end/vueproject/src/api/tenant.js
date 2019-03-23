import axios from 'axios';
import { req } from './axiosFun';

// 流程实例管理-获取流程实例管理列表
export const TenantList = (params) => { return req("get", "http://localhost:8780/tenants/", params) };
// 流程实例管理-保存流程实例