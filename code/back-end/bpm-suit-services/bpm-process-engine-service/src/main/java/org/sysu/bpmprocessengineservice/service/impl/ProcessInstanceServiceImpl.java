package org.sysu.bpmprocessengineservice.service.impl;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.tomcat.jni.Proc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.bpmprocessengineservice.constant.ResponseConstantManager;
import org.sysu.bpmprocessengineservice.service.ProcessInstanceService;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProcessInstanceServiceImpl implements ProcessInstanceService {
    private final static Logger logger = LoggerFactory.getLogger(ProcessInstanceServiceImpl.class);

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
}
