package org.sysu.bpmmanagementservice.bpmprocessengineservice.service;

import java.util.HashMap;
import java.util.Map;

public interface EngineService {
    /** 根据流程模型名称 启动流程实例 */
    HashMap<String, String> startProcessInstanceByKey(String processModelKey, Map<String, Object> variables);

    /** 根据流程定义ID启动流程; 流程定义是流程模型的解析结果 **/
    HashMap<String, String> startProcessInstanceById(String processDefinitionId, Map<String, Object> variables);

    /** 根据流程实例ID和用户，获取用户的工作列表*/
    /** 工作项是由活动实例化而产生的,Activitii中活动包含了CallActiviti，SubActiviti和Task,工作项可以理解成是Task的实例化*/
    HashMap<String, String> getWorkList(String processInstanceId, String assignee);

    /** 根据流程实例ID获取流程实例当前所有的可执行任务*/
    HashMap<String, String> getWorkItems(String processInstanceId);

    /** 根据当前的流程实例ID获取当前可以执行的一个任务*/
    HashMap<String, String> getWorkItem(String processInstanceId);

    /** 用户接受/认领工作项 */
    HashMap<String, String> acceptWorkItem(String taskId, String assignee);

    /** 完成工作项 */
    HashMap<String, String> completeWorkItem(String taskId, Map<String, Object> variables);

    /** 根据流程实例ID判断任务是否已经完成 */
    HashMap<String, String> isEnded(String processInstanceId);
}
