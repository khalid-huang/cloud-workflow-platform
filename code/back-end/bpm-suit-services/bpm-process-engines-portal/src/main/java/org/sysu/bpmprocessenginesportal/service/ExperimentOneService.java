package org.sysu.bpmprocessenginesportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.sysu.bpmprocessenginesportal.constant.GlobalConstant;
import org.sysu.bpmprocessenginesportal.util.CommonUtil;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExperimentOneService {
    @Autowired
    RestTemplate restTemplate;

    private String bpmProcessEngineService = GlobalConstant.RibbonBPMProcessEngineService;

    private String prefix = bpmProcessEngineService + "/experiment";

    public ResponseEntity<?> startProcessInstanceById(Map<String, Object> data, String processDefinitionId) {
        String url = "http://" + this.prefix + "/startProcessInstanceById/" + processDefinitionId;
        MultiValueMap<String, Object> valueMap = CommonUtil.map2MultiValueMap(data);
        ResponseEntity<String> result = restTemplate.postForEntity(url, valueMap, String.class);
        return result;
    }

    public ResponseEntity<?> getCurrentSingleTask(String processInstanceId) {
        String url = "http://" + this.prefix + "/getCurrentSingleTask/" + processInstanceId;
        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
        return result;
    }

    public ResponseEntity<?> acceptWorkitem(String processInstanceId, String workitemId, String username) {
        String url = "http://" + this.prefix + "/workitem/accept/" + processInstanceId + "/" + workitemId;
        Map<String, Object> data = new HashMap<>();
        data.put("username", username);
        MultiValueMap<String, Object> valueMap = CommonUtil.map2MultiValueMap(data);
        ResponseEntity<String> result = restTemplate.postForEntity(url, valueMap, String.class);
        return result;
    }

    public ResponseEntity<?> completeWorkitem(String processDefinitionId, String processInstanceId, String workitemId, Map<String, Object> data) {
        String url = "http://" + this.prefix + "/workitem/complete/" + processDefinitionId + "/" +  processInstanceId + "/" + workitemId;
        System.out.println("url:" + url);
        MultiValueMap<String, Object> valueMap = CommonUtil.map2MultiValueMap(data);
        ResponseEntity<String> result = restTemplate.postForEntity(url, valueMap, String.class);
        return result;
    }

}
