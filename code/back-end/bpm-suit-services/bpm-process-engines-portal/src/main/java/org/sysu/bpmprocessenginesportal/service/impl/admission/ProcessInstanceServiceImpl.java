package org.sysu.bpmprocessenginesportal.service.impl.admission;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.sysu.bpmprocessenginesportal.service.ProcessInstanceService;

import java.util.Map;

@Service
public class ProcessInstanceServiceImpl implements ProcessInstanceService {
    @Override
    public ResponseEntity<?> startProcessInstanceByKey(Map<String, Object> data, String processModelKey) {
        return null;
    }

    @Override
    public ResponseEntity<?> startProcessInstanceById(Map<String, Object> data, String processDefinitionId) {
        return null;
    }

    @Override
    public ResponseEntity<?> getProcessInstancesPage(Integer pageSize, Integer pageNumber) {
        return null;
    }
}
