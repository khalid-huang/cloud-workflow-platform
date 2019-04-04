package org.sysu.bpmmanagementservice.service.impl;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.bpmmanagementservice.constant.Pagination;
import org.sysu.bpmmanagementservice.constant.ResponseConstantManager;
import org.sysu.bpmmanagementservice.service.ProcessDefinitionService;
import org.sysu.bpmmanagementservice.vo.ProcessDefinitionVo;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** 基于Activiti实现的ProcessDefinitionService*/
@Service
public class ProcessDefinitionServiceImpl implements ProcessDefinitionService {
    @Autowired
    RuntimeService runtimeService;

    @Autowired
    RepositoryService repositoryService;


    @Override
    @Transactional
    public HashMap<String, Object> removeProcessDefinition(String processDefinitionId) {
        /** 通过部署Id来删除*/
        HashMap<String, Object> result = new HashMap<>();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId)
                .singleResult();
        String deploymentId = processDefinition.getDeploymentId();
        repositoryService.deleteDeployment(deploymentId);
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("data", null);
        return result;
    }

    @Override
    public HashMap<String, Object> retrieveAllProcessDefinitions() {
        HashMap<String, Object> result = new HashMap<>();
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().list();
        List<ProcessDefinitionVo> processDefinitionVos = new ArrayList<>();
        for(ProcessDefinition processDefinition : processDefinitions) {
            processDefinitionVos.add(new ProcessDefinitionVo(processDefinition));
        }
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("data", processDefinitionVos);
        return result;
    }

    @Override
    public HashMap<String, Object> retrieveProcessDefinitionsPage(int pageSize, int pageNumber) {
        HashMap<String, Object> result = new HashMap<>();
        Pagination pagination = new Pagination(pageNumber, pageSize);

        int totalCount = (int) repositoryService.createProcessDefinitionQuery().count();

        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
                .orderByProcessDefinitionId().asc().listPage(pagination.getStart(), pagination.getEnd());
        pagination.setRowTotal(totalCount);
        pagination.setPageTotal((int) Math.ceil(totalCount/pageNumber));

        List<ProcessDefinitionVo> processDefinitionVos = new ArrayList<>();
        for(ProcessDefinition processDefinition : processDefinitions) {
            processDefinitionVos.add(new ProcessDefinitionVo(processDefinition));
        }
        pagination.setRows(processDefinitionVos);
        result.put("data", pagination);
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        return result;
    }
}
