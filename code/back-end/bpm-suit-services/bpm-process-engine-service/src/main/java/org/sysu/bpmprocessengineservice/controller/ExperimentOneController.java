package org.sysu.bpmprocessengineservice.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sysu.bpmprocessengineservice.service.workflowinterface.Interface2Service;
import org.sysu.bpmprocessengineservice.service.workflowinterface.Interface5Service;

import java.util.HashMap;
import java.util.Map;

//用于做毕业设计实验一
@RestController
@Api(tags = "ExperimentOneController", description = "hello world测试")
@RequestMapping(value = "experiment")
public class ExperimentOneController {
    private final static Logger logger = LoggerFactory.getLogger(ExperimentOneController.class);

    @Autowired
    Interface5Service interface5Service;

    @Autowired
    Interface2Service interface2Service;

    @Autowired
    TaskService taskService;

    /** 根据流程实例获取当前可执行的任务 */
    public Task _getCurrentSingleTask(String processInstanceId) {
        return taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
    }

    @ApiOperation(value = "通过流程模型名称创建流程实例")
    @RequestMapping(value = "startProcessInstanceByKey/{processModelKey}", method = RequestMethod.POST)
    public ResponseEntity<?> startProcessInstanceByKey(@RequestParam(required = false) Map<String, Object> data,
                                                       @PathVariable(name = "processModelKey") String processModelKey) {
        HashMap<String, Object> responseBody = interface5Service.startProcessInstanceByKey(processModelKey, data);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @ApiOperation(value = "根据流程定义ID创建流程实例")
    @RequestMapping(value = "startProcessInstanceById/{processDefinitionId}", method = RequestMethod.POST)
    public ResponseEntity<?> startProcessInstanceById(@RequestParam(required = false) Map<String, Object> data,
                                                      @PathVariable(name = "processDefinitionId") String processDefinitionId) {
        HashMap<String, Object> responseBody = interface5Service.startProcessInstanceById(processDefinitionId, data);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @RequestMapping(value = "getCurrentSingleTask/{processInstanceId}", method = RequestMethod.GET)
    public ResponseEntity<?> getCurrentSingleTask(@PathVariable(value = "processInstanceId", required = false) String processInstanceId) {
        HashMap<String, String> response = new HashMap<>();

        //获取列表
        Task task = _getCurrentSingleTask(processInstanceId);
        response.put("status", "success");
        response.put("message", "get current single task processInstanceId of " + processInstanceId + " success");
        response.put("taskId", task.getId());
        logger.info(response.toString());
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @ApiOperation(value = "用户拉取工作项，默认拉取就start了")
    @RequestMapping(value = "workitem/accept/{processInstanceId}/{workitemId}", method = RequestMethod.POST)
    public ResponseEntity<?> acceptWorkitem(@PathVariable(value = "processInstanceId") String processInstanceId,
                                            @PathVariable(value = "workitemId") String workitemId,
                                            @RequestParam(value = "username") String username) {
        HashMap<String, Object> responseBody = interface2Service.acceptWorkitem(processInstanceId, workitemId, username);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @ApiOperation(value = "完成工作项")
    @RequestMapping(value = "workitem/complete/{processInstanceId}/{workitemId}", method = RequestMethod.POST)
    public ResponseEntity<?> completeWorkitem(@PathVariable(value = "processInstanceId") String processInstanceId,
                                              @PathVariable(value = "workitemId") String workitemId,
                                              @RequestParam(required = false) Map<String, Object> data) {
        HashMap<String, Object> responseBody = interface2Service.completeWorkitem(processInstanceId, workitemId, data);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

}
