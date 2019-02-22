package org.sysu.bpmprocessenginesportal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sysu.bpmprocessenginesportal.service.ProcessInstanceService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "ProcessInstanceController", description = "引擎服务")
@RestController
public class ProcessInstanceController {
    private final static Logger logger = LoggerFactory.getLogger(ProcessInstanceController.class);

    @Resource(name = "na-processInstanceServiceImpl")
    ProcessInstanceService processInstanceService;

    @ApiOperation(value = "通过流程模型名称创建流程实例")
    @RequestMapping(value = "startProcessInstanceByKey/{processModelKey}", method = RequestMethod.POST)
    public ResponseEntity<?> startProcessInstanceByKey(@RequestParam(required = false) Map<String, Object> data,
                                                       @PathVariable(name = "processModelKey") String processModelKey) {
        return processInstanceService.startProcessInstanceByKey(data, processModelKey);
    }

    @ApiOperation(value = "根据流程定义ID创建流程实例")
    @RequestMapping(value = "startProcessInstanceById/{processDefinitionId}", method = RequestMethod.POST)
    public ResponseEntity<?> startProcessInstanceById(@RequestParam(required = false) Map<String, Object> data,
                                                      @PathVariable(name = "processDefinitionId") String processDefinitionId) {
        return processInstanceService.startProcessInstanceById(data, processDefinitionId);
    }

    @ApiOperation(value = "根据每页大小和页码获取流程实例")
    @RequestMapping(value = "/getProcessInstancesPage", method = RequestMethod.GET)
    public ResponseEntity<?> getProcessInstancesPage(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                     @RequestParam(value = "pageNumber", required = false) Integer pageNumber) {
        return processInstanceService.getProcessInstancesPage(pageSize, pageNumber);
    }

}
