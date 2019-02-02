package org.sysu.bpmmanagementservice.service;

import java.util.HashMap;

public interface ProcessDefinitionService {

    HashMap<String, Object> removeProcessDefinition(String processDefinitionId);

    HashMap<String, Object> retrieveAllProcessDefinitions();

    HashMap<String, Object> retrieveProcessDefinitionsPage(int pageSize, int pageNumber);
}
