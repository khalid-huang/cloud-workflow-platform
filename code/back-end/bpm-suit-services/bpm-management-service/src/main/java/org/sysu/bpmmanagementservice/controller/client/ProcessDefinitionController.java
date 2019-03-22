package org.sysu.bpmmanagementservice.controller.client;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sysu.bpmmanagementservice.service.ProcessDefinitionService;

import java.util.HashMap;

@Api(tags = "ProcessDefinitionController", description = "管理流程定义")
@RestController
public class  ProcessDefinitionController {
    private static final Logger logger = LoggerFactory.getLogger(ProcessDefinitionController.class);

    @Autowired
    ProcessDefinitionService processDefinitionService;

    @ApiOperation(value = "根据流程定义Id删除流程定义")
    @DeleteMapping(value = "processDefinitions/{processDefinitionId}")
    public ResponseEntity<?> removeProcessDefinition(@PathVariable(value = "processDefinitionId") String processDefinitionId) {
        HashMap<String, Object> result = processDefinitionService.removeProcessDefinition(processDefinitionId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

//    @ApiOperation(value = "获取全部的流程定义")
//    @GetMapping(value = "/processDefinitions")
//    public ResponseEntity<?> retrieveAllProcessDefinitions() {
//        HashMap<String, Object> result = processDefinitionService.retrieveAllProcessDefinitions();
//        return ResponseEntity.status(HttpStatus.OK).body(result);
//    }

    /** pageSize每页的个数，pageNumber，页码*/
    @ApiOperation(value = "获取在指定页数下的指定页码")
    @GetMapping(value = "/processDefinitions")
    public ResponseEntity<?> retrieveProcessDefinitionsPage(@RequestParam(value = "pageSize") int pageSize,
                                                            @RequestParam(value = "pageNumber") int pageNumber) {
        HashMap<String, Object> result = processDefinitionService.retrieveProcessDefinitionsPage(pageSize, pageNumber);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
