package org.sysu.activitiservice.controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.HashMap;
import java.util.Map;

@Controller
@RestController
@SuppressWarnings("unchecked")
public class HelloWorldController {

    private final static Logger logger = LoggerFactory.getLogger(HelloWorldController.class);

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping(value = "/helloworld", method = RequestMethod.POST)
    public ResponseEntity<?> helloworld(@RequestParam(required = false)Map<String, Object> variables) {
        logger.info((String) variables.get("test"));
        logger.info("logback test");
        HashMap<String,String> response = new HashMap<>();
        response.put("message", "hello world, my server port is " + serverPort);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @RequestMapping(value = "/getHelloworld", method = RequestMethod.GET)
    public ResponseEntity<?> getHelloworld() {
        logger.info("logback test");
        HashMap<String,String> response = new HashMap<>();
        response.put("message", "hello world, my server port is " + serverPort);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
