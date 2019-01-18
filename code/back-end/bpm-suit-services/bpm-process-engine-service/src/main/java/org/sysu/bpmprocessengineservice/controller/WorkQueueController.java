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

import javax.annotation.security.PermitAll;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "WorkQueueController", description = "工作列表服务")
@RestController
public class WorkQueueController {
    private static final Logger logger = LoggerFactory.getLogger(WorkQueueController.class);

    @Autowired
    Interface2Service interface2Service;

    @ApiOperation(value = "通过用户名和角色获取相应的工作列表")
    @RequestMapping(value = "getWorkQueue", method = RequestMethod.GET)
    public ResponseEntity<?> getWorkQueue(@RequestParam(value = "username") String username,
                                          @RequestParam(value = "role") String role) {
        HashMap<String, Object> responseBody = interface2Service.getWorkQueue(username, role);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }


}
