package org.sysu.processexecutionservice.controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sysu.processexecutionservice.service.ActivitiService;
import org.sysu.processexecutionservice.util.CommonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ActivitiController {
    private static Logger logger = LoggerFactory.getLogger(ActivitiService.class);

    @Autowired
    ActivitiService activitiService;

    //启动指定流程
    @RequestMapping(value = "/startProcessInstanceByKey/{processModelKey}", method = RequestMethod.POST)
    public ResponseEntity<?> startProcessInstanceByKey(@RequestParam(required = false) Map<String, Object> variables,
                                          @PathVariable(value = "processModelKey", required = false) String processModelKey) {

        HashMap<String, String> response = new HashMap<>();

        //参数校验
        ArrayList<String> missingParams = new ArrayList<>();
        if(variables == null) missingParams.add("variables");
        if(processModelKey == null) missingParams.add("processModelKey ");
        if(missingParams.size() > 0) {
            response.put("status", "fail");
            response.put("message", "required parameters  missing: " + CommonUtil.ArrayList2String(missingParams, " "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSON.toJSONString(response));
        }

        //启动流程
        return activitiService.startProcessInstanceByKey(variables, processModelKey);
    }

    //启动指定流程
    @RequestMapping(value = "/startProcessInstanceById/{processDefinitionId}", method = RequestMethod.POST)
    public ResponseEntity<?> startProcessInstanceById(@RequestParam(required = false) Map<String, Object> variables,
                                          @PathVariable(value = "processDefinitionId", required = false) String processDefinitionId) {

        HashMap<String, String> response = new HashMap<>();

        //参数校验
        ArrayList<String> missingParams = new ArrayList<>();
        if(variables == null) missingParams.add("variables");
        if(processDefinitionId == null) missingParams.add("processDefinitionId ");
        if(missingParams.size() > 0) {
            response.put("status", "fail");
            response.put("message", "required parameters  missing: " + CommonUtil.ArrayList2String(missingParams, " "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSON.toJSONString(response));
        }

        //启动流程
        return activitiService.startProcessInstanceById(variables, processDefinitionId);
    }

    /** 获取一个当前可执行的任务 */
    @RequestMapping(value = "getCurrentSingleTask/{processInstanceId}", method = RequestMethod.GET)
    public ResponseEntity<?> getCurrentSingleTask(@PathVariable(value = "processInstanceId", required = false) String processInstanceId) {
        HashMap<String, String> response = new HashMap<>();

        //校验参数
        ArrayList<String> missingParams = new ArrayList<>();
        if (processInstanceId == null) missingParams.add("processInstanceId");
        if (missingParams.size() > 0) {
            response.put("status", "fail");
            response.put("message", " required parameters missing: " + CommonUtil.ArrayList2String(missingParams, " "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSON.toJSONString(response));
        }
        return activitiService.getCurrentSingleTask(processInstanceId);
    }

    //获取指定流程的当前任务列表
    @RequestMapping(value = "getCurrentTasks/{processInstanceId}", method = RequestMethod.GET)
    public ResponseEntity<?> getCurrentTasks(@PathVariable(value = "processInstanceId", required = false) String processInstanceId) {
        HashMap<String, String> response = new HashMap<>();

        //校验参数
        ArrayList<String> missingParams = new ArrayList<>();
        if (processInstanceId == null) missingParams.add("processInstanceId");
        if (missingParams.size() > 0) {
            response.put("status", "fail");
            response.put("message", "required parameters missing: " + CommonUtil.ArrayList2String(missingParams, " "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSON.toJSONString(response));
        }

        return activitiService.getCurrentTasks(processInstanceId);
    }

    //完成任务
    @RequestMapping(value = "completeTask/{processDefinitionId}/{processInstanceId}/{taskId}", method = RequestMethod.POST)
    public ResponseEntity<?> completeTask(@RequestParam(required = false) Map<String, Object> variables, 
        @PathVariable(value = "processDefinitionId", required = false) String processDefinitionId, @PathVariable(value = "processInstanceId", required = false) String processInstanceId ,@PathVariable(value = "taskId", required = false) String taskId) {

        HashMap<String, String> response = new HashMap<>();

        //校验参数
        ArrayList<String> missingParams = new ArrayList<>();
        if (variables == null) missingParams.add("variables");
        if (processInstanceId == null) missingParams.add("processInstanceId");
        if (processDefinitionId == null) missingParams.add("processDefinitionId");
        if (taskId == null) missingParams.add("taskId");
        if (missingParams.size() > 0) {
            response.put("status", "fail");
            response.put("message", "required parameters missing: " + CommonUtil.ArrayList2String(missingParams, " "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSON.toJSONString(response));
        }

//        return activitiService.completeTask(variables, processInstanceId, taskId);
        return activitiService.completeTaskWithFutureTask(variables, processInstanceId, taskId);
    }
}
