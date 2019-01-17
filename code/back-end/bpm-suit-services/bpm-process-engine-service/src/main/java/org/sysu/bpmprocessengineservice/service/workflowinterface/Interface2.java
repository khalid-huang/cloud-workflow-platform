package org.sysu.bpmprocessengineservice.service.workflowinterface;

import java.util.HashMap;
import java.util.Map;

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
    /** 将工作项提供给某个用户，是管理员的操作*/
    HashMap<String, Object> offerWorkitem(String processInstanceId, String workitemId, String username);

    /** 将工作项指派给某个用户，是管理员的操作*/
    HashMap<String, Object> allocateWorkitem(String processInstanceId, String workitemId, String username);

    /** 撤销指派，将工作项从allocated/offered状态转化为offered状态*/
    HashMap<String, Object> reofferWorkitem(String processInstanceId, String workitemId, String username);

    /** 启动,将工作项从Allocated状态转化为Started状态 */
    /** 也就是标志用户开始处理工作项 */
    HashMap<String, Object> startWorkitem(String processInstanceId, String workitemId);

    /** 拉取，将工作项从Offered状态转化为Allocated状态 */
    /** 标志用户接受工作项，从候选人变成处理人 */
    HashMap<String, Object> acceptWorkitem(String processInstanceId, String workitemId, String username);

    /** 拉取并启动, 将工作项从Offered状态变成started*/
    HashMap<String, Object> acceptAndStartWorkitem(String processInstanceId, String workitemId, String username);

    /**  完成,将工作项从Started转化为completed*/
    HashMap<String, Object> completeWorkitem(String processInstanceId,String workitemId, Map<String, Object> data);

    /** 挂起，将工作项从Started 转化为suspended*/
    HashMap<String, Object> suspendWorkitem(String processInstanceId, String workitemId);

    /** 重启，将工作项从Suspended 转化为 unsuspended*/
    HashMap<String, Object> unsuspendWorkitem(String processInstanceId, String workitemId);

    /** 跳过， u将工作项从allocated转化为complete*/
    HashMap<String, Object> skipWorkitem(String processInstanceId, String workitemId);

    /** 撤销拉取，将工作项从Allocated状态转化为Offered状态*/
    HashMap<String, Object> deallocateWorkitem(String processInstanceId, String workitemId);

    /**  撤销启动，将工作项从Started/allocated状态转化为Allocated状态*/
    HashMap<String, Object> reallocateWorkitem(String processInstanceId, String workitemId, String username);
}
