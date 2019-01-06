package org.sysu.bpmprocessengineservice.service;

import java.util.HashMap;
import java.util.Map;

public interface EngineService {
    /** 根据流程模型名称 启动流程实例 */
    HashMap<String, String> startProcessInstanceByKey(String processModelKey, Map<String, Object> variables);

    /** 根据流程定义ID启动流程; 流程定义是流程模型的解析结果 **/
    HashMap<String, String> startProcessInstanceById(String processDefinitionId, Map<String, Object> variables);

    /** 根据流程实例ID和用户，获取用户的任务列表*/
    HashMap<String, String> getCurrentTasksForAssign(String processInstanceId, String assignee);

    /** 根据流程实例ID获取流程实例当前所有的可执行任务*/
    HashMap<String, String> getCurrentTasks(String processInstanceId);

    /** 根据当前的流程实例ID获取当前可以执行的一个任务*/
    HashMap<String, String> getCurrentSingleTask(String processInstanceId);

    /** 用户认领任务 */
    HashMap<String, String> claimTask(String taskId, String assignee);

    /** 完成任务 */
    HashMap<String, String> completeTask(String taskId, Map<String, Object> variables);

    /** 根据流程实例ID判断任务是否已经完成 */
    HashMap<String, String> isEnded(String processInstanceId);
}
