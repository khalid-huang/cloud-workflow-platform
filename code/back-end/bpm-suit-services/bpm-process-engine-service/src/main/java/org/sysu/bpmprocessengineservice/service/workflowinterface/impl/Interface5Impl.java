package org.sysu.bpmprocessengineservice.service.workflowinterface.impl;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.sysu.bpmprocessengineservice.constant.Pagination;
import org.sysu.bpmprocessengineservice.constant.ResponseConstantManager;
import org.sysu.bpmprocessengineservice.service.workflowinterface.Interface5;
import org.sysu.bpmprocessengineservice.vo.InstanceMonitorVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Interface5Impl implements Interface5 {
    private final static Logger logger = LoggerFactory.getLogger(Interface5Impl.class);

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    TaskService taskService;

    @Override
    public HashMap<String, Object> startProcessInstanceByKey(String processModelKey, Map<String, Object> variables) {
        HashMap<String, Object> response = new HashMap<>();
        ProcessInstance pi =  runtimeService.startProcessInstanceByKey(processModelKey, variables);
        response.put("status", ResponseConstantManager.STATUS_SUCCESS);
        response.put("processInstanceId", pi.getId());
        response.put("processDefinitionId", pi.getProcessDefinitionId());
        return response;
    }

    @Override
    public HashMap<String, Object> startProcessInstanceById(String processDefinitionId, Map<String, Object> variables) {
        HashMap<String, Object> response = new HashMap<>();
        ProcessInstance pi =  runtimeService.startProcessInstanceById(processDefinitionId, variables);
        response.put("status", ResponseConstantManager.STATUS_SUCCESS);
        response.put("processInstanceId", pi.getId());
        response.put("processDefinitionId", pi.getProcessDefinitionId());
        return response;
    }

    @Override
    public HashMap<String, Object> isEnded(String processInstanceId) {
        HashMap<String, Object> response = new HashMap<>();
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

    @Override
    public HashMap<String, Object> getProcessInstancesPage(int pageSize, int pageNumber) {
        HashMap<String, Object> result = new HashMap<>();
        Pagination pagination = new Pagination(pageNumber, pageSize);

        int totalCount = (int) runtimeService.createProcessInstanceQuery().count();

        List<ProcessInstance> processInstances =  runtimeService.createProcessInstanceQuery()
                .orderByProcessDefinitionKey().asc().listPage(pagination.getStart(), pagination.getEnd());
        List<InstanceMonitorVo> instanceMonitorVos = new ArrayList<>();
        for (ProcessInstance ins : processInstances) {
            InstanceMonitorVo instanceMonitorVo = new InstanceMonitorVo();
            String processDefinitionId = ins.getProcessDefinitionId();
            String activityId = ins.getActivityId();
            String processInstanceId = ins.getProcessInstanceId();

            instanceMonitorVo.setProcessDefinitionName(ins.getProcessDefinitionName());
            instanceMonitorVo.setProcessDefinitionVersion(ins.getProcessDefinitionVersion());
            instanceMonitorVo.setProcessDefinitionId(ins.getProcessDefinitionId());
            instanceMonitorVo.setProcessDefinitionKey(ins.getProcessDefinitionKey());
            instanceMonitorVo.setProcessInstanceId(ins.getProcessInstanceId());

            BpmnModel model = repositoryService.getBpmnModel(processDefinitionId);
            FlowElement element =  model.getMainProcess().getFlowElement(activityId);
            instanceMonitorVo.setCurrentNode(element.getName());
            instanceMonitorVo.setCurrentNodeId(element.getId());

            Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
            if(task != null){
                instanceMonitorVo.setTaskId(task.getId());
                instanceMonitorVo.setInstanceName(task.getName());
                String assignee = task.getAssignee();
                if(!StringUtils.isEmpty(assignee)){
                    instanceMonitorVo.setAssigneeName(assignee);
                }
            }
            instanceMonitorVos.add(instanceMonitorVo);
        }
        pagination.setRows(instanceMonitorVos);
        pagination.setRowTotal(totalCount);
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("instanceMonitorVos", instanceMonitorVos);
        return result;
    }
}
