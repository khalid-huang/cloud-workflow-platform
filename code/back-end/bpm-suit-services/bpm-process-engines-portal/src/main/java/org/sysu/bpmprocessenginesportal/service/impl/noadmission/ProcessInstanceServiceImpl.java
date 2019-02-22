package org.sysu.bpmprocessenginesportal.service.impl.noadmission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.sysu.bpmprocessenginesportal.constant.GlobalConstant;
import org.sysu.bpmprocessenginesportal.service.ProcessInstanceService;
import org.sysu.bpmprocessenginesportal.util.CommonUtil;

import java.util.Map;

/** 没有加入admission时的接口实现，主要是用来测试的，后期要去掉的*/
@Service("na-processInstanceServiceImpl")
public class ProcessInstanceServiceImpl implements ProcessInstanceService {

    @Autowired
    RestTemplate restTemplate;

    private String bpmProcessEngineService = GlobalConstant.RibbonBPMProcessEngineService;

    @Override
    public ResponseEntity<?> startProcessInstanceByKey(Map<String, Object> data, String processModelKey) {
        String url = "http://" + this.bpmProcessEngineService + "/startProcessInstanceByKey/" + processModelKey;
        MultiValueMap<String, Object> valueMap = CommonUtil.map2MultiValueMap(data);
        ResponseEntity<String> result = restTemplate.postForEntity(url, valueMap, String.class);
        return result;
    }

    @Override
    public ResponseEntity<?> startProcessInstanceById(Map<String, Object> data, String processDefinitionId) {
        String url = "http://" + this.bpmProcessEngineService + "/startProcessInstanceById/" + processDefinitionId;
//        封装参数，要使用MultiValueMap，不可以用Map或是HashMap
        MultiValueMap<String, Object> valueMap = CommonUtil.map2MultiValueMap(data);
        ResponseEntity<String> result = restTemplate.postForEntity(url, valueMap, String.class);
        return result;
    }

    @Override
    public ResponseEntity<?> getProcessInstancesPage(Integer pageSize, Integer pageNumber) {
        String url = "http://" + this.bpmProcessEngineService + "/getProcessInstancesPage?pageSize="+pageSize.toString()
                + "&pageNumber=" + pageNumber.toString();
        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
        return result;
    }
}
