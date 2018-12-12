package org.sysu.processexecutionservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.sysu.processexecutionservice.admission.ActivitiExecuteAdmissionor;
import org.sysu.processexecutionservice.admission.requestcontext.ActivitiExecuteRequestContext;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

@Service
public class ActivitiService {
    private static Logger logger = LoggerFactory.getLogger(ActivitiService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ActivitiExecuteAdmissionor activitiExecuteAdmissionor;

    private String activitiQueryService = "activiti-query-service";
    private String activitiExecutionService = "activiti-execute-service";
    private String activitiStartService = "activiti-start-service";

    public ResponseEntity<?> getCurrentSingleTask(String processInstanceId) {
        String url = "http://" + this.activitiQueryService + "/getCurrentSingleTask/" + processInstanceId;
        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
        return result;
    }

    public ResponseEntity<?> getCurrentTasks(String processInstanceId) {
        String url = "http://" + this.activitiQueryService + "/getCurrentTasks" + processInstanceId;
        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
        return result;
    }

    public ResponseEntity<?> startProcessInstanceByKey(Map<String, Object> variables, String processModelKey) {
        String url = "http://" + this.activitiStartService + "/startProcessInstanceByKey/" + processModelKey;
        ResponseEntity<String> result = restTemplate.postForEntity(url, variables, String.class);
        return result;
    }

    public ResponseEntity<?> startProcessInstanceById(Map<String, Object> variables, String processDefinitionId) {
        String url = "http://" + this.activitiStartService + "/startProcessInstanceById/" + processDefinitionId;
        ResponseEntity<String> result = restTemplate.postForEntity(url, variables, String.class);
        return result;
    }

//    异步处理，不立即返回客户端结果
    public ResponseEntity<?> completeTask(Map<String, Object> variables, String processInstanceId, String taskId) {
        String url = "http://" + this.activitiExecutionService + "/completeTask/" + processInstanceId + "/" + taskId;
//      取出rtl
        String rtl = (String) variables.get("rtl");
        variables.remove("rtl");
        ActivitiExecuteRequestContext activitiExecuteRequestContext = new ActivitiExecuteRequestContext(rtl, url, variables, this.restTemplate);
//        同步的处理方式
        this.activitiExecuteAdmissionor.admit(activitiExecuteRequestContext);
//      主要还是如何处理请求线程和获取响应方式的问题； 如果同步的话，线程会一直占用，而且怎么个获取方法；
//      如果不同步的话，要怎么获取响应;
//      直接返回吧；完全符合前端的模拟就可以了；
        return ResponseEntity.ok("请求正在调度中");
    }
//    异步处理，返回客户端结果
    public ResponseEntity<?> completeTaskWithFutureTask(Map<String, Object> variables, String processInstanceId, String taskId) {
        String url = "http://" + this.activitiExecutionService + "/completeTask/" + processInstanceId + "/" + taskId;
//      取出rtl
        String rtl = (String) variables.get("rtl");
        variables.remove("rtl");
        ActivitiExecuteRequestContext activitiExecuteRequestContext = new ActivitiExecuteRequestContext(rtl, url, variables, this.restTemplate);
        this.activitiExecuteAdmissionor.admit(activitiExecuteRequestContext);
        try {
            ResponseEntity<?> result = activitiExecuteRequestContext.getFutureTask().get();
            System.out.println(result);
            return result;
        } catch (Exception e) {
            return ResponseEntity.ok(e.toString());
        }
    }
}
