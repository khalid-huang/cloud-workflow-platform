package org.sysu.bpmprocessengineservice.service.impl;


import com.alibaba.fastjson.JSON;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.bpmprocessengineservice.constant.ResponseConstantManager;
import org.sysu.bpmprocessengineservice.service.EngineService;

import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.HashMap;
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
    public HashMap<String, String> startProcessInstanceByKey(String processModelKey, Map<String, Object> variables) {
        HashMap<String, String> response = new HashMap<>();
        ProcessInstance pi =  runtimeService.startProcessInstanceByKey(processModelKey, variables);
        response.put("status", ResponseConstantManager.STATUS_SUCCESS);
        response.put("processInstanceId", pi.getId());
        response.put("processDefinitionId", pi.getProcessDefinitionId());
        return response;
    }

    @Override
    public HashMap<String, String> startProcessInstanceById(String processDefinitionId, Map<String, Object> variables) {
        HashMap<String, String> response = new HashMap<>();
        ProcessInstance pi =  runtimeService.startProcessInstanceById(processDefinitionId, variables);
        response.put("status", ResponseConstantManager.STATUS_SUCCESS);
        response.put("processInstanceId", pi.getId());
        response.put("processDefinitionId", pi.getProcessDefinitionId());
        return response;
    }

    @Override
    public HashMap<String, String> getWorkList(String processInstanceId, String assignee) {
        HashMap<String, String> response = new HashMap<>();
        List<Task> tasks=  taskService.createTaskQuery().processInstanceId(processInstanceId).taskAssignee(assignee).list();
        List<String> taskIds = new ArrayList<>();
        for(Task task : tasks) {
            taskIds.add(task.getId());
        }
        response.put("status", ResponseConstantManager.STATUS_SUCCESS);
        response.put("taskIds", JSON.toJSONString(taskIds));
        return response;
    }

    @Override
    public HashMap<String, String> getWorkItems(String processInstanceId) {
        HashMap<String, String> response = new HashMap<>();
        List<Task> tasks =taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        List<String> taskIds = new ArrayList<>();
        for(Task task : tasks) {
            taskIds.add(task.getId());
        }
        response.put("taskIds", JSON.toJSONString(taskIds));
        response.put("status", ResponseConstantManager.STATUS_SUCCESS);
        return response;
    }

    @Override
    public HashMap<String, String> getWorkItem(String processInstanceId) {
        HashMap<String, String> response = new HashMap<>();
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        response.put("status", ResponseConstantManager.STATUS_SUCCESS);
        response.put("taskId", task.getId());
        return response;
    }

    @Override
    public HashMap<String, String> acceptWorkItem(String taskId, String assignee) {
        HashMap<String, String> response = new HashMap<>();
        taskService.claim(taskId, assignee);
        response.put("status", ResponseConstantManager.STATUS_SUCCESS);
        return response;
    }

    @Override
    public HashMap<String, String> completeWorkItem(String taskId, Map<String, Object> variables) {
        HashMap<String, String> response = new HashMap<>();
        taskService.complete(taskId, variables);
        response.put("status", ResponseConstantManager.STATUS_SUCCESS);
        return response;
    }

    @Override
    public HashMap<String, String> isEnded(String processInstanceId) {
        HashMap<String, String> response = new HashMap<>();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        int flag = 0; //0表示未结束，1表示已经结束
        //如果事务提交之后，流程实例会被删除的，所以要判断
        if(processInstance != null) {
            flag = processInstance.isEnded() ? 1 : 0;
        } else {
            //如果已经没有这个流程实例了，表示已经执行完成了
            flag = 1;
        }
        response.put("status", ResponseConstantManager.STATUS_SUCCESS);
        response.put("isEnd", "" + flag);
        return response;
    }

}
