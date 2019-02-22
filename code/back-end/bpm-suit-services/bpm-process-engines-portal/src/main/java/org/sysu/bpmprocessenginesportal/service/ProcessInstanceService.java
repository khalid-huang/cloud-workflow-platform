package org.sysu.bpmprocessenginesportal.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

/** bpm-process-engine-service 服务的processInstanceController接口的门户接口*/
public interface ProcessInstanceService {

    ResponseEntity<?> startProcessInstanceByKey(Map<String, Object> data, String processModelKey);

    ResponseEntity<?> startProcessInstanceById(Map<String, Object> data, String processDefinitionId);

    ResponseEntity<?> getProcessInstancesPage(Integer pageSize, Integer pageNumber);
}
