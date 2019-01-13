package org.sysu.bpmmanagementservice.bpmprocessengineservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sysu.bpmmanagementservice.bpmprocessengineservice.service.EngineService;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "EngineController", description = "引擎服务")
@RestController
public class EngineController {
    private final static Logger logger = LoggerFactory.getLogger(EngineController.class);

    @Autowired
    EngineService engineService;

    /** 根据流程模型ID启动流程*/
    @ApiOperation(value = "根据流程模型ID启动流程")
    @RequestMapping(value = "/startProcessInstanceByKey/{processModelKey}", method = RequestMethod.POST)
    public ResponseEntity<?> startProcessInstanceByKey(@PathVariable(value = "processModelKey") String processModelKey,
                                                       @RequestParam Map<String, Object> variables) {
        HashMap<String,String> response = engineService.startProcessInstanceByKey(processModelKey, variables);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /** 根据流程定义ID启动流程，流程定义是流程模型的内存解析结果*/
    @ApiOperation(value = "根据流程定义ID启动流程，流程定义是流程模型的内存解析结果")
    @RequestMapping(value = "/sartProcessInstanceById/{processDefinitionId}", method = RequestMethod.POST)
    public ResponseEntity<?> startProcessInstanceById(@PathVariable(value = "processDefinitionId") String processDefinitionId,
                                                      @RequestParam Map<String, Object> variables) {
        HashMap<String, String> response = engineService.startProcessInstanceById(processDefinitionId, variables);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /** 获取指定用户的当前工作列表*/
    /** assignee通过请求参数携带 */
    @ApiOperation(value = "获取指定用户的当前工作列表")
    @RequestMapping(value = "/getWorkList/{processInstanceId}", method = RequestMethod.GET)
    public ResponseEntity<?> getWorkList(@PathVariable(value = "processInstanceId") String processInstanceId,
                                                        @RequestParam(value = "assignee") String assignee) {
        HashMap<String,String> response = engineService.getWorkList(processInstanceId, assignee);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /** 获取指定流程实例的全部工作项 */
    @ApiOperation(value = "获取指定流程实例的全部工作项 ")
    @RequestMapping(value = "/getWorkItems/{processInstanceId}", method = RequestMethod.GET)
    public ResponseEntity<?> getWorkItems(@PathVariable(value = "processInstanceId") String processInstanceId) {
        HashMap<String, String> response = engineService.getWorkItems(processInstanceId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /** 获取指定流程实例的一个工作项 */
    @ApiOperation(value = "获取指定流程实例的一个工作项 ")
    @RequestMapping(value = "/getWorkItem/{processInstanceId}", method = RequestMethod.GET)
    public ResponseEntity<?> getWorkItem(@PathVariable(value = "processInstanceId") String processInstanceId) {
        HashMap<String, String> response = engineService.getWorkItem(processInstanceId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /** 用户认领或是接受工作项 */
    @ApiOperation(value = "用户认领或是接受工作项 ")
    @RequestMapping(value = "/acceptWorkItem/{taskId}", method = RequestMethod.POST)
    public ResponseEntity<?> acceptWorkItem(@PathVariable(value = "taskId") String taskId,
                                            @RequestParam(value = "assignee") String assignee) {
        HashMap<String, String> response = engineService.acceptWorkItem(taskId, assignee);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation(value = "用户完成任务")
    @RequestMapping(value = "/completeWoItem/{taskId}", method = RequestMethod.POST)
    public ResponseEntity<?> completeWorkItem(@PathVariable(value = "taskId") String taskId,
                                              @RequestParam Map<String, Object> variables) {
        HashMap<String, String> response = engineService.completeWorkItem(taskId, variables);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
