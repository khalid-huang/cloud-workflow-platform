package org.sysu.bpmprocessenginesportal.service.impl.noadmission;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.sysu.bpmprocessenginesportal.service.ProcessInstanceService;

import java.util.Map;

/** 没有加入admission时的接口实现，主要是用来测试的，后期要去掉的*/
@Service("na-processInstanceServiceImpl")
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
