package org.sysu.bpmprocessengineservice.controller;

import com.alibaba.fastjson.JSON;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sysu.bpmprocessengineservice.service.EngineService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EngineController {
    private final static Logger logger = LoggerFactory.getLogger(EngineController.class);

    @Autowired
    EngineService engineService;

    @RequestMapping(value = "/startProcessInstanceByKey/{processModelKey}", method = RequestMethod.POST)
    public ResponseEntity<?> startProcessInstanceByKey(@PathVariable(value = "processModelKey") String processModelKey,
                                                       @RequestParam Map<String, Object> variables) {
        HashMap<String,String> response = engineService.startProcessInstanceByKey(processModelKey, variables);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
