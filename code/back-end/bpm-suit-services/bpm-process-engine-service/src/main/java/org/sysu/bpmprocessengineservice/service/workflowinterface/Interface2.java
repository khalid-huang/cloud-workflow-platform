package org.sysu.bpmprocessengineservice.service.workflowinterface;

import java.util.HashMap;

/**
 * 实现与客户端应用的交互接口，主要是提供了工作列表和处理工作项的服务
 */
public interface Interface2 {
    /** 工作列表服务实现*/

    /** 根据用户名和角色信息，获取用户的工作列表*/
    /** 如果用户是管理员的话，需要返回所有的工作项组成的列表 */
    /** 工作列表包含了四个状态的列表，分别是Offered, Allocated, Started, Suspended*/
    /** 工作项是由活动实例化而产生的,Activitii中活动包含了CallActiviti，SubActiviti和Task,工作项可以理解成是Task的实例化*/
    HashMap<String, Object> getWorkQueue(String username, String role);

    /** 当登录用户为管理员时获取工作列表，需要获取所有的工作列表*/
    HashMap<String, Object> getWorkQueueForAdmin(String username);

    /** 当登录用户为普通用户时获取工作列表，只获取自己的工作列表*/
    HashMap<String, Object> getWorkQueueForUser(String username);

    /** 工作项相关 */

}
