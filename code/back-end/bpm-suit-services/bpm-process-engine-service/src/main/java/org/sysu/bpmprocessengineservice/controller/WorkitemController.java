package org.sysu.bpmprocessengineservice.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sysu.bpmprocessengineservice.service.workflowinterface.Interface2Service;

import java.util.HashMap;
import java.util.Map;


@Api(tags = "WorkitemController", description = "工作项服务")
@RestController
public class WorkitemController {
    private final  static Logger logger = LoggerFactory.getLogger(WorkitemController.class);

    @Autowired
    Interface2Service interface2Service;


    @ApiOperation(value = "提供工作项给指定用户")
    @RequestMapping(value = "workitem/offer/{processInstanceId}/{workitemId}", method = RequestMethod.POST)
    public ResponseEntity<?> offerWorkitem(@PathVariable(value = "processInstanceId") String processInstanceId,
                                           @PathVariable(value = "workitemId") String workitemId,
                                           @RequestParam(value = "username") String username) {
        HashMap<String, Object> responseBody = interface2Service.offerWorkitem(processInstanceId, workitemId, username);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);

    }

    @RequestMapping(value = "workitem/allocate/{processInstanceId}/{workitemId}", method = RequestMethod.POST)
    public ResponseEntity<?> allocateWorkitem(@PathVariable(value = "processInstanceId") String processInstanceId,
                                              @PathVariable(value = "workitemId") String workitemId,
                                              @RequestParam(value = "username") String username) {
        HashMap<String, Object> responseBody = interface2Service.allocateWorkitem(processInstanceId, workitemId, username);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);

    }

    @RequestMapping(value = "workitem/reoffer/{processInstanceId}/{workitemId}", method = RequestMethod.POST)
    public ResponseEntity<?> reofferWorkitem(@PathVariable(value = "processInstanceId") String processInstanceId,
                                             @PathVariable(value = "workitemId") String workitemId,
                                             @RequestParam(value = "username") String username) {
        HashMap<String, Object> responseBody = interface2Service.reofferWorkitem(processInstanceId, workitemId, username);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }


    @ApiOperation(value = "用户拉取工作项，默认拉取就start了")
    @RequestMapping(value = "workitem/accept/{processInstanceId}/{workitemId}", method = RequestMethod.POST)
    public ResponseEntity<?> acceptWorkitem(@PathVariable(value = "processInstanceId") String processInstanceId,
                                            @PathVariable(value = "workitemId") String workitemId,
                                            @RequestParam(value = "username") String username) {
        HashMap<String, Object> responseBody = interface2Service.acceptWorkitem(processInstanceId, workitemId, username);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }


    @ApiOperation(value = "拉取并启动工作项")
    @RequestMapping(value = "workitem/acceptAndStart/{processInstanceId}/{workitemId}", method = RequestMethod.POST)
    public  ResponseEntity<?> acceptAndStartWorkitem(@PathVariable(value = "processInstanceId") String processInstanceId,
                                                     @PathVariable(value = "workitemId") String workitemId,
                                                     @RequestParam(value = "username") String username) {
        HashMap<String, Object> responseBody = interface2Service.acceptAndStartWorkitem(processInstanceId, workitemId, username);
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

    @ApiOperation(value = "挂起工作项")
    @RequestMapping(value = "workitem/suspend/{processInstanceId}/{workitemId}", method = RequestMethod.POST)
    public ResponseEntity<?> suspendWorkitem(@PathVariable(value = "processInstanceId") String processInstanceId,
                                             @PathVariable(value = "workitemId") String workitemId) {
        HashMap<String, Object> responseBody = interface2Service.suspendWorkitem(processInstanceId, workitemId);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @ApiOperation(value = "重启工作项")
    @RequestMapping(value = "workitem/unsuspend/{processInstanceId}/{workitemId}", method = RequestMethod.POST)
    public ResponseEntity<?> unsuspendWorkitem(@PathVariable(value = "processInstanceId") String processInstanceId,
                                               @PathVariable(value = "workitemId") String workitemId) {
        HashMap<String, Object> responseBody = interface2Service.unsuspendWorkitem(processInstanceId, workitemId);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @ApiOperation(value = "撤销拉取，回复到offer状态")
    @RequestMapping(value = "workitem/deallocate/{processInstanceId}/{workitemId}", method = RequestMethod.POST)
    public ResponseEntity<?> deallocateWorkitem(@PathVariable(value = "processInstnceId") String processInstanceId,
                                                @PathVariable(value = "workitemId") String workitemId) {
        HashMap<String, Object> responseBody = interface2Service.deallocateWorkitem(processInstanceId, workitemId);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @ApiOperation(value = "撤销启动， 重新")
    @RequestMapping(value = "workitem/reallocate/{processInstanceId}/{workitemId}", method = RequestMethod.POST)
    public ResponseEntity<?> reallocateWorkitem(@PathVariable(value = "processInstanceId") String processInstanceId,
                                                @PathVariable(value = "workitemId") String workitemId,
                                                @RequestParam(value = "username") String username) {
        HashMap<String, Object> responseBody = interface2Service.reallocateWorkitem(processInstanceId, workitemId, username);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

}
