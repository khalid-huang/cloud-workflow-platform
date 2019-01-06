package org.sysu.bpmprocessengineservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    private static Logger logger = LoggerFactory.getLogger(HelloWorldController.class);

    @RequestMapping(value = "/")
    public ResponseEntity<?> getHelloworld() {
        System.out.println("ns hello world");
        return  ResponseEntity.ok("helloworld");
    }
}
