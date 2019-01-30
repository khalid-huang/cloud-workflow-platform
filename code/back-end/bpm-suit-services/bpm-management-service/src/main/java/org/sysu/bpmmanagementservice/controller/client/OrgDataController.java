package org.sysu.bpmmanagementservice.controller.client;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sysu.bpmmanagementservice.service.OrgDataService;

import java.util.HashMap;

/**
 *  组织架构的管理
 */
@Api(tags = "OrgDataController", description = "组织架构相关的管理")
@RestController
@RequestMapping(value = "/orgData")
public class OrgDataController {
    private static final Logger logger = LoggerFactory.getLogger(OrgDataController.class);

    @Autowired
    OrgDataService orgDataService;

    @ApiOperation(value = "增加新资源-人力")
    @PostMapping(value = "humans")
    public ResponseEntity<?> addHuman(@RequestParam(value = "username", required = true) String username,
                                      @RequestParam(value = "firstName", required = false) String firstName,
                                      @RequestParam(value = "lastName", required = false) String lastName,
                                      @RequestParam(value = "email", required = false) String email,
                                      @RequestParam(value = "password", required = false) String password) {
        HashMap<String, Object> result = orgDataService.addHuman(username, firstName, lastName, email, password);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "删除资源-人力")
    @DeleteMapping(value = "/humans/{username}")
    public ResponseEntity<?> removeHuman(@PathVariable(value = "username") String username) {
        HashMap<String, Object> result = orgDataService.removeHuman(username);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "更新人力信息，只可更新密码和邮件")
    @PostMapping(value = "/humans/{username}")
    public ResponseEntity<?> updateHuman(@PathVariable(value = "username") String username,
                                         @RequestParam(value = "emial", required = false) String email,
                                         @RequestParam(value = "password", required = false) String password) {
        HashMap<String, String> pairs = new HashMap<>();
        if(email == null && password == null) {
            return ResponseEntity.status(HttpStatus.OK).body("OK");
        }
        if(email != null) pairs.put("email", email);
        if(password != null) pairs.put("password", password);
        HashMap<String, Object> result = orgDataService.updateHuman(username, pairs);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "根据用户名获取用户信息")
    @GetMapping(value = "/humans/{username}")
    public ResponseEntity<?> retrieveHumanByUsername(@PathVariable(value = "username") String username) {
        HashMap<String, Object> result = orgDataService.retrieveHumanByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "获取所有人力")
    @GetMapping(value = "/humans")
    public ResponseEntity<?> retrieveAllHuman() {
        HashMap<String, Object> result = orgDataService.retrieveAllHuman();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "增加新子组")
    @PostMapping(value = "/groups/")
    public ResponseEntity<?> addGroup(@RequestParam(value = "name") String name,
                                      @RequestParam(value = "description", required = false) String description,
                                      @RequestParam(value = "note", required = false) String note,
                                      @RequestParam(value = "belongToId", required = false) String belongToId,
                                      @RequestParam(value = "groupType", required = false) int groupType) {
        HashMap<String, Object> result = orgDataService.addGroup(name, description, note, belongToId, groupType);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "根据子组名称删除")
    @DeleteMapping(value = "/groups/{name}")
    public ResponseEntity<?> removeGroupByName(@PathVariable(value = "name") String name) {
        HashMap<String, Object> result = orgDataService.removeGroupByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "根据子组信息")
    @PostMapping(value = "/groups/{name}")
    public ResponseEntity<?> updateGroup(@PathVariable(value = "name") String name,
                                         @RequestParam(value = "description", required = false) String description,
                                         @RequestParam(value = "note", required = false) String note,
                                         @RequestParam(value = "belongToId", required = false) String belongToId,
                                         @RequestParam(value = "groupType", required = false) String groupType) {
        HashMap<String, String> pairs = new HashMap<>();
        if(description != null) pairs.put("description", description);
        if(note != null) pairs.put("note", note);
        if(belongToId != null) pairs.put("belongToId", belongToId);
        if(groupType != null) pairs.put("groupType", groupType);

        HashMap<String, Object> result = orgDataService.updateGroup(name, pairs);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public 



}
