package org.sysu.bpmprocessengineservice.service.impl;


import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.bpmprocessengineservice.service.EngineService;

import java.util.List;
import java.util.Map;

/**
 * 基本Activiti实现的EngineService
 */
@Service
public class EngineServiceImpl implements EngineService {
    private final static Logger logger = LoggerFactory.getLogger(EngineService.class);

    @Autowired
    TaskService taskService;

    @Autowired
    RuntimeService runtimeService;


    @Override
    public ProcessInstance startProcessInstanceByKey(String processModelKey, Map<String, Object> variables) {
        return runtimeService.startProcessInstanceByKey(processModelKey, variables);
    }

    @Override
    public ProcessInstance startProcessInstanceById(String processDefinitionId, Map<String, Object> variables) {
        return runtimeService.startProcessInstanceById(processDefinitionId, variables);
    }

    @Override
    public List<Task> getCurrentTasksForAssign(String processInstanceId, String assignee) {
        return taskService.createTaskQuery().processInstanceId(processInstanceId).taskAssignee(assignee).list();
    }

    @Override
    public List<Task> getCureentTask(String processInstanceId) {
        return taskService.createTaskQuery().processInstanceId(processInstanceId).list();
    }

    @Override
    public Task getCurrentSingleTask(String processInstanceId) {
        return taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
    }

    @Override
    public void claimTask(String taskId, String assignee) {
        taskService.claim(taskId, assignee);
    }

    @Override
    public void completeTask(String taskId, Map<String, Object> variables) {
        taskService.complete(taskId, variables);
    }

    @Override
    public boolean isEnded(String processInstanceId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        //如果事务提交之后，流程实例会被删除的，所以要判断
        if(processInstance != null) {
            return processInstance.isEnded();
        }
        //如果已经没有这个流程实例了，表示已经执行完成了
        return true;
    }
}
