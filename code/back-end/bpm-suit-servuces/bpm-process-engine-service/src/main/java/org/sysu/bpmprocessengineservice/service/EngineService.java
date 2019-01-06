package org.sysu.bpmprocessengineservice.service;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

public interface EngineService {
    /** 根据流程模型名称 启动流程实例 */
    ProcessInstance startProcessInstanceByKey(String processModelKey, Map<String, Object> variables);

    /** 根据流程定义ID启动流程; 流程定义是流程模型的解析结果 **/
    ProcessInstance startProcessInstanceById(String processDefinitionId, Map<String, Object> variables);

    /** 根据流程实例ID和用户，获取用户的任务列表*/
    List<Task> getCurrentTasksForAssign(String processInstanceId, String assignee);

    /** 根据流程实例ID获取流程实例当前所有的可执行任务*/
    List<Task> getCureentTask(String processInstanceId);

    /** 根据当前的流程实例ID获取当前可以执行的一个任务*/
    Task getCurrentSingleTask(String processInstanceId);

    /** 用户认领任务 */
    void claimTask(String taskId, String assignee);

    /** 完成任务 */
    void completeTask(String taskId, Map<String, Object> variables);

    /** 根据流程实例ID判断任务是否已经完成 */
    boolean isEnded(String processInstanceId);
}
