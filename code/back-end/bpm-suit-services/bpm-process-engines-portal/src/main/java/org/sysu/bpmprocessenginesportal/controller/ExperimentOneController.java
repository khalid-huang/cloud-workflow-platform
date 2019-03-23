package org.sysu.bpmprocessenginesportal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sysu.bpmprocessenginesportal.service.ExperimentOneService;

import java.util.Map;

//用于做毕业设计实验一
@RestController
@Api(tags = "ExperimentOneController", description = "hello world测试")
@RequestMapping(value = "experiment")
public class ExperimentOneController {
    private final static Logger logger = LoggerFactory.getLogger(ExperimentOneController.class);

    @Autowired
    ExperimentOneService experimentOneService;

    @ApiOperation(value = "根据流程定义ID创建流程实例")
    @RequestMapping(value = "startProcessInstanceById/{processDefinitionId}", method = RequestMethod.POST)
    public ResponseEntity<?> startProcessInstanceById(@RequestParam(required = false) Map<String, Object> data,
                                                      @PathVariable(name = "processDefinitionId") String processDefinitionId) {
        return experimentOneService.startProcessInstanceById(data, processDefinitionId);
    }

    @RequestMapping(value = "getCurrentSingleTask/{processInstanceId}", method = RequestMethod.GET)
    public ResponseEntity<?> getCurrentSingleTask(@PathVariable(value = "processInstanceId", required = false) String processInstanceId) {
        return experimentOneService.getCurrentSingleTask(processInstanceId);
    }

    @ApiOperation(value = "用户拉取工作项，默认拉取就start了")
    @RequestMapping(value = "workitem/accept/{processInstanceId}/{workitemId}", method = RequestMethod.POST)
    public ResponseEntity<?> acceptWorkitem(@PathVariable(value = "processInstanceId") String processInstanceId,
                                            @PathVariable(value = "workitemId") String workitemId,
                                            @RequestParam(value = "username") String username) {
        return experimentOneService.acceptWorkitem(processInstanceId,workitemId,username);
    }

    @ApiOperation(value = "完成工作项")
    @RequestMapping(value = "workitem/complete/{processDefinitionId}/{processInstanceId}/{workitemId}", method = RequestMethod.POST)
    public ResponseEntity<?> completeWorkitem(@PathVariable(value = "processInstanceId") String processInstanceId,
                                              @PathVariable(value = "processDefinitionId") String processDefinitionId,
                                              @PathVariable(value = "workitemId") String workitemId,
                                              @RequestParam(required = false) Map<String, Object> data) {
        System.out.println("workitemId: " + workitemId);
        return experimentOneService.completeWorkitem(processDefinitionId, processInstanceId, workitemId, data);
    }

}
