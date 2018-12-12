package org.sysu.activitiservice.controller;

import com.alibaba.fastjson.JSON;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sysu.activitiservice.service.ActivitiService;
import org.sysu.activitiservice.util.CommonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@SuppressWarnings("unchecked")
public class ActivitiController {
    private final static Logger logger = LoggerFactory.getLogger(ActivitiController.class);

    @Autowired
    private ActivitiService activitiService;

    @Autowired
    private TaskService taskService;

    //启动指定流程
    @RequestMapping(value = "/startProcessInstanceByKey/{processModelKey}", method = RequestMethod.POST)
    public ResponseEntity<?> startProcessInstanceByKey(@RequestParam(required = false) Map<String, Object> variables,
                                          @PathVariable(value = "processModelKey", required = false) String processModelKey) {

        HashMap<String, String> response = new HashMap<>();

        //做参数校验
        ArrayList<String> missingParams = new ArrayList<>();
        if(variables == null) missingParams.add("variables");
        if(processModelKey == null) missingParams.add("processModelKey");
        if(missingParams.size() > 0) {
            response.put("status", "fail");
            response.put("message", "required parameters missing: " + CommonUtil.ArrayList2String(missingParams, " "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSON.toJSONString(response));
        }

        //启动流程
//        ProcessInstance pi =  runtimeService.startProcessInstanceByKey(processModelKey, variables);
        ProcessInstance pi =  activitiService.startProcessInstanceByKey(processModelKey, variables);
        response.put("status", "success");
        response.put("message", "start process " + processModelKey + " success");
        response.put("processInstanceId", pi.getId());
        response.put("processDefinitionId", pi.getProcessDefinitionId());
        logger.info(response.toString());
        return ResponseEntity.status(HttpStatus.OK).body(JSON.toJSONString(response));
    }

    //启动指定流程
    @RequestMapping(value = "/startProcessInstanceById/{processInstanceId}", method = RequestMethod.POST)
    public ResponseEntity<?> startProcessInstanceById(@RequestParam(required = false) Map<String, Object> variables,
                                          @PathVariable(value = "processInstanceId", required = false) String processInstanceId) {

        HashMap<String, String> response = new HashMap<>();

        //做参数校验
        ArrayList<String> missingParams = new ArrayList<>();
        if(variables == null) missingParams.add("variables");
        if(processInstanceId == null) missingParams.add("processInstanceId");
        if(missingParams.size() > 0) {
            response.put("status", "fail");
            response.put("message", "required parameters missing: " + CommonUtil.ArrayList2String(missingParams, " "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSON.toJSONString(response));
        }

        //启动流程
//        ProcessInstance pi =  runtimeService.startProcessInstanceById(processInstanceId, variables);
        ProcessInstance pi =  activitiService.startProcessInstanceById(processInstanceId, variables);
        response.put("status", "success");
        response.put("message", "start process " + processInstanceId + " success");
        response.put("processInstanceId", pi.getId());
        response.put("processDefinitionId", pi.getProcessDefinitionId());
        logger.info(response.toString());
        return ResponseEntity.status(HttpStatus.OK).body(JSON.toJSONString(response));
    }

    /** 获取一个当前可执行的任务 */
    @RequestMapping(value = "getCurrentSingleTask/{processInstanceId}", method = RequestMethod.GET)
    public ResponseEntity<?> getCurrentSingleTask(@PathVariable(value = "processInstanceId", required = false) String processInstanceId) {
        HashMap<String, String> response = new HashMap<>();

        //校验参数
        ArrayList<String> missingParams = new ArrayList<>();
        if(processInstanceId == null) missingParams.add("processInstanceId");
        if(missingParams.size() > 0) {
            response.put("status", "fail");
            response.put("message", " required parameters missing: " + CommonUtil.ArrayList2String(missingParams, " "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSON.toJSONString(response));
        }

        //获取列表
        Task task = activitiService.getCurrentSingleTask(processInstanceId);
        response.put("status", "success");
        response.put("message", "get current single task processInstanceId of " + processInstanceId + " success");
        response.put("taskId", task.getId());
        logger.info(response.toString());
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    //获取指定流程的当前任务列表
    @RequestMapping(value = "getCurrentTasks/{processInstanceId}", method = RequestMethod.GET)
    public ResponseEntity<?> getCurrentTasks(@PathVariable(value = "processInstanceId", required = false) String processInstanceId) {
        HashMap<String, String> response = new HashMap<>();

        //校验参数
        ArrayList<String> missingParams = new ArrayList<>();
        if(processInstanceId == null) missingParams.add("processInstanceId");
        if(missingParams.size() > 0) {
            response.put("status", "fail");
            response.put("message", "required parameters missing: " + CommonUtil.ArrayList2String(missingParams, " "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSON.toJSONString(response));
        }

        //获取列表
//        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        List<Task> tasks = activitiService.getCurrentTasks(processInstanceId);
        List<String> taskIds = new ArrayList<>();
        for(Task task : tasks) {
            taskIds.add(task.getId());
        }
        response.put("status", "success");
        response.put("message", "get task list of processInstanceId of " + processInstanceId + " success");
        response.put("taskIds", JSON.toJSONString(taskIds));
        logger.info(response.toString());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //获取指定流程的指定用户的任务列表
    @RequestMapping(value = "getCurrentTasksOfAssignee/{processInstanceId}", method = RequestMethod.GET)
    public ResponseEntity<?> getCurrentTasksOfAssignee(@RequestParam(value = "assignee", required = false) String assignee,
                                                       @PathVariable(value = "processInstanceId", required = false) String processInstanceId) {
        System.out.println("getCurrentTaskAssignee:" + assignee);
        HashMap<String, String> response = new HashMap<>();

        //校验参数
        ArrayList<String> missingParams = new ArrayList<>();
        if(processInstanceId == null) missingParams.add("pid");
        if(assignee == null) missingParams.add("assignee");
        if(missingParams.size() > 0) {
            response.put("status", "fail");
            response.put("message", "required parameters missing: " + CommonUtil.ArrayList2String(missingParams, " "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSON.toJSONString(response));
        }

        //获取列表
//        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).taskAssignee(assignee).list();
        List<Task> tasks = activitiService.getCurrentTasks(processInstanceId, assignee);
        List<String> taskIds = new ArrayList<>();
        for(Task task : tasks) {
            taskIds.add(task.getId());
        }
        response.put("status", "success");
        response.put("message", "get " + assignee + "'s task list of processInstanceId of " + processInstanceId + " success");
        response.put("taskIds", taskIds.toString());
        logger.info(response.toString());
        return ResponseEntity.status(HttpStatus.OK).body(JSON.toJSONString(response));
    }

    //claim任务
    @RequestMapping(value = "claimTask/{processInstanceId}/{taskId}", method = RequestMethod.POST)
    public ResponseEntity<?> claimTask(@RequestParam(required = false) Map<String, Object> data,
                                       @PathVariable(value = "processInstanceId", required = false) String processInstanceId,
                                       @PathVariable(value = "taskId", required = false) String taskId) {
        //取出参数; 因为客户端nameService那边用了okhttp针对Object类型加了一个toJSONString，所以这里也要做一个解压出来
        String assignee = JSON.parseObject((String) data.get("assignee"), String.class);
        System.out.println("claimTask-assignee:" + assignee);

        HashMap<String, String> response = new HashMap<>();
        //参数校验
        ArrayList<String> missingParams = new ArrayList<>();
        if(taskId == null) missingParams.add("taskId");
        if(processInstanceId == null) missingParams.add("processInstanceId");
        if(assignee == null) missingParams.add("assignee");
        if(missingParams.size() > 0) {
            response.put("status", "fail");
            response.put("message", "required parameters missing: " + CommonUtil.ArrayList2String(missingParams, " "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSON.toJSONString(response));
        }

        //认领任务
//        taskService.claim(taskId, assignee);
        activitiService.claimTask(taskId, assignee);
        response.put("status", "success");
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        response.put("message", "assignee " + assignee + " claim the task of " + taskId + " with taskName " + task.getName());
        logger.info("claimTask: " + assignee + " " + response.toString());
        return ResponseEntity.status(HttpStatus.OK).body(JSON.toJSONString(response));
    }

    //完成任务
    @RequestMapping(value = "completeTask/{processDefinitionId}/{processInstanceId}/{taskId}", method = RequestMethod.POST)
    public ResponseEntity<?> completeTask(@RequestParam(required = false) Map<String, Object> variables, 
        @PathVariable(value = "processDefinitionId", required = false) String processDefinitionId ,
        @PathVariable(value = "processInstanceId", required = false) String processInstanceId ,@PathVariable(value = "taskId", required = false) String taskId) {

        HashMap<String, String> response = new HashMap<>();

        //校验参数
        ArrayList<String> missingParams = new ArrayList<>();
        if(variables == null) missingParams.add("variables");
        if(processInstanceId == null) missingParams.add("processInstanceId");
        if(processDefinitionId == null) missingParams.add("processDefinitionId");
        if(taskId == null) missingParams.add("taskId");
        if(missingParams.size() > 0) {
            response.put("status", "fail");
            response.put("message", "required parameters missing: " + CommonUtil.ArrayList2String(missingParams, " "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSON.toJSONString(response));
        }

        //这里需要对variables做一次JSON反序列化

        for(Map.Entry<String, Object> entry : variables.entrySet()) {
            System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
            //需不需要做，需要做个验证才知道
            variables.put(entry.getKey(), JSON.parseObject((String)entry.getValue(), Object.class));
        }

        //完成任务
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
//        taskService.complete(taskId, variables);
        Map<String, String> temp = activitiService.completeTask(processInstanceId, taskId, variables);
        response.put("status", "message");
        response.put("message", "complete task of taskId " + taskId + "with taskName" + task.getName());
        response.put("newWorkItemNumber", temp.get("newWorkItemNumber"));
        response.put("isEnded", activitiService.isEnded(processInstanceId) ? "1" : "0");
        logger.info(response.toString());
        return ResponseEntity.status(HttpStatus.OK).body(JSON.toJSONString(response));
    }

}
