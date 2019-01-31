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
    @DeleteMapping(value = "/groups/{id}")
    public ResponseEntity<?> removeGroupByName(@PathVariable(value = "id") String id) {
        HashMap<String, Object> result = orgDataService.removeGroupById(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "根据子组信息")
    @PostMapping(value = "/groups/{id}")
    public ResponseEntity<?> updateGroup(@PathVariable(value = "id") String id,
                                         @RequestParam(value = "name", required = false) String name,
                                         @RequestParam(value = "description", required = false) String description,
                                         @RequestParam(value = "note", required = false) String note,
                                         @RequestParam(value = "belongToId", required = false) String belongToId,
                                         @RequestParam(value = "groupType", required = false) String groupType) {
        HashMap<String, String> pairs = new HashMap<>();
        if(description != null) pairs.put("description", description);
        if(note != null) pairs.put("note", note);
        if(belongToId != null) pairs.put("belongToId", belongToId);
        if(groupType != null) pairs.put("groupType", groupType);

        HashMap<String, Object> result = orgDataService.updateGroup(id, pairs);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "根据子组名称获取组信息")
    @GetMapping(value = "/groups/{id}")
    public ResponseEntity<?> retrieveGroupById(@PathVariable(value = "id") String id) {
        HashMap<String, Object> result = orgDataService.retrieveGroupById(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "获取全部子组信息")
    @GetMapping(value = "/groups")
    public ResponseEntity<?> retrieveAllGroup() {
        HashMap<String, Object> result = orgDataService.retrieveAllGroup();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "增加职位")
    @PostMapping(value = "/positions")
    public ResponseEntity<?> addPosition(@RequestParam(value = "name") String name,
                                         @RequestParam(value = "description", required = false) String description,
                                         @RequestParam(value = "note", required = false) String note,
                                         @RequestParam(value = "belongToId", required = false) String belongToId,
                                         @RequestParam(value = "reportToId", required = false) String reportToId) {
        HashMap<String, Object> result = orgDataService.addPosition(name, description, note, belongToId, reportToId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "删除相关职位")
    @DeleteMapping(value = "/positions/{id}")
    public ResponseEntity<?> removePositionByName(@PathVariable(value = "id") String id) {
        HashMap<String, Object> result = orgDataService.removePositionById(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "更新职位信息")
    @PostMapping(value = "/positions/{id}")
    public ResponseEntity<?> updatePositionById(@PathVariable(value = "id") String id,
                                                @RequestParam(value = "name", required = false) String name,
                                                @RequestParam(value = "description", required = false) String description,
                                                @RequestParam(value = "note", required = false) String note,
                                                @RequestParam(value = "belongToId", required = false) String belongToId,
                                                @RequestParam(value = "reportToId", required = false) String reportToId) {
        HashMap<String, String> pairs = new HashMap<>();
        if(name != null) pairs.put("name", name);
        if(description != null) pairs.put("description", description);
        if(note != null) pairs.put("note", note);
        if(belongToId != null) pairs.put("belongToId", belongToId);
        if(reportToId != null) pairs.put("reportToId", reportToId);
        HashMap<String, Object> result = orgDataService.updatePosition(id, pairs);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "根据id获取职位相关信息")
    @GetMapping(value = "/positions/{id}")
    public ResponseEntity<?> retrievePositionById(@PathVariable(value = "id") String id) {
        HashMap<String, Object> result = orgDataService.retrievePositionById(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "获取全部职位信息")
    @GetMapping(value = "/positions")
    public ResponseEntity<?> retrieveAllPosition() {
        HashMap<String, Object> result = orgDataService.retrieveAllPosition();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "添加能力")
    @PostMapping(value = "/capabilities/")
    public ResponseEntity<?> addCapability(@RequestParam(value = "name") String name,
                                           @RequestParam(value = "description", required = false) String description,
                                           @RequestParam(value = "note", required = false) String note) {
        HashMap<String, Object> result = orgDataService.addCapability(name, description, note);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "根据Id删除能力")
    @DeleteMapping(value = "/capabilities/{id}")
    public ResponseEntity<?> removeCapabilityById(@PathVariable(value = "id") String id) {
        HashMap<String, Object> result = orgDataService.removeCapabilityById(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "根据id更新能力")
    @PostMapping(value = "/capabilities/{id}")
    public ResponseEntity<?> updateCapability(@PathVariable(value = "id") String id,
                                             @RequestParam(value = "name") String name,
                                             @RequestParam(value = "description", required = false) String description,
                                             @RequestParam(value = "note", required = false) String note) {
        HashMap<String, String> pairs = new HashMap<>();
        pairs.put("name", name);
        if(description != null) pairs.put("description", description);
        if(note != null) pairs.put("note", note);

        HashMap<String, Object> result = orgDataService.updateCapability(id, pairs);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "根据id获取能力")
    @GetMapping(value = "/capabilities/{}id")
    public ResponseEntity<?> retrieveCapabilityById(@PathVariable(value = "id") String id) {
        HashMap<String, Object> result = orgDataService.retrieveCapabilityById(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "获取全部能力")
    @GetMapping(value = "/capabilities")
    public ResponseEntity<?> retrieveAllCapability() {
        HashMap<String, Object> result = orgDataService.retrieveAllCapability();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "删除指定用户与组织架构的所有连接")
    @DeleteMapping(value = "/humans/{username}/connections")
    public ResponseEntity<?> removeHumanConnection(@PathVariable(value = "username") String username) {
        HashMap<String, Object> result = orgDataService.removeHumanConnection(username);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "查询用户拥有哪些职位")
    @GetMapping(value = "/humans/{username}/positions/")
    public ResponseEntity<?> retrieveHumanInWithPosition(@PathVariable(value = "username") String username) {
        HashMap<String, Object> result = orgDataService.retrieveHumanInWhatPosition(username);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "查询指定职位有哪些员工")
    @GetMapping(value = "/positions/{id}/humans")
    public ResponseEntity<?> retrieveAllHumanInPosition(@PathVariable(value = "id") String id) {
        HashMap<String, Object> result = orgDataService.retrieveAllHumanInPosition(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "查询用户拥有哪些能力")
    @GetMapping(value = "/humans/{username}/capabilities")
    public ResponseEntity<?> retrieveHumanWithWhatCapability(@PathVariable(value = "username") String username) {
        HashMap<String, Object> result = orgDataService.retrieveHumanWithWhatCapability(username);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "查询指定能力有哪些人")
    @GetMapping(value = "/capabilities/{id}/humans")
    public ResponseEntity<?> retrieveAllHumanWithCapability(@PathVariable(value = "id") String id) {
        HashMap<String, Object> result = orgDataService.retrieveAllHumanWithCapability(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "给指定用户添加到指定职位")
    @PostMapping(value = "connections/humanposition")
    public ResponseEntity<?> addHumanPosition(@RequestParam(value = "username") String username,
                                              @RequestParam(value = "positionId") String positionId) {
        HashMap<String, Object> result = orgDataService.addHumanPosition(username, positionId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "去除指定用户的指定职位")
    @DeleteMapping(value = "/connections/humanposition")
    public ResponseEntity<?> removeHumanPosition(@RequestParam(value = "username") String username,
                                                 @RequestParam(value = "positionId") String positionId) {
        HashMap<String, Object> result = orgDataService.removeHumanPosition(username, positionId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "添加用户指定能力")
    @PostMapping(value = "/connections/humancapability")
    public ResponseEntity<?> addHumanCapability(@RequestParam(value = "username") String username,
                                                @RequestParam(value = "capabilityId") String capabilityId) {
        HashMap<String, Object> result = orgDataService.addHumanCapability(username, capabilityId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
