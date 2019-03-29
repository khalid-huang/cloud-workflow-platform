package org.sysu.bpmmanagementservice.controller.client;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sysu.bpmmanagementservice.service.BroleMappingService;

import java.util.HashMap;

/**
 * 业务关系相关的管理
 */
@Api(tags = "RoleMappingController", description = "业务关系相关的管理")
@RestController
@RequestMapping(value = "/roleMapping")
public class BroleMappingController {
    private static final Logger logger = LoggerFactory.getLogger(BroleMappingController.class);

    @Autowired
    BroleMappingService broleMappingService;

    @ApiOperation(value = "添加业务角色")
    @PostMapping(value = "/brole/")
    public ResponseEntity<?> addBrole(@RequestParam(value = "name") String name) {
        HashMap<String, Object> result = broleMappingService.addBrole(name);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "根据业务角色Id删除业务角色")
    @DeleteMapping(value = "/brole/{name}")
    public ResponseEntity<?> removeBusinessRoleById(@PathVariable(value = "name") String name) {
        HashMap<String, Object> result = broleMappingService.removeBroleByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "根据业务角色Id获取业务角色相关信息")
    @GetMapping(value = "/brole/{name}")
    public ResponseEntity<?> retrieveBusinessRoleById(@PathVariable(value = "name") String name) {
        HashMap<String, Object> result = broleMappingService.retrieveBroleByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "获取全部业务角色")
    @GetMapping(value = "/broles/")
    public ResponseEntity<?> retrieveAllBusinessRole() {
        HashMap<String, Object> result = broleMappingService.retrieveAllBrole();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "为职位添加业务角色")
    @PostMapping(value = "roleMappings/", params = {"mappedType=position"})
    public ResponseEntity<?> addPositionBroleNameOfProcDef(@RequestParam(value = "positionId") String positionId,
                                                  @RequestParam(value = "broleNameId") String broleNameId,
                                                  @RequestParam(value = "procDefId") String procDefId) {
        HashMap<String, Object> result = broleMappingService.addPositionBroleNameOfProcDef(positionId, broleNameId, procDefId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "删除业务角色映射")
    @DeleteMapping(value = "broleMapping/{id}")
    public ResponseEntity<?> removeBroleMapping(@PathVariable(name = "id") String id) {
        HashMap<String, Object> result = broleMappingService.removeBroleMappingById(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "获取与业务角色建立映射的所有职位")
    @GetMapping(value = "roleMappings/positions")
    public ResponseEntity<?> retrieveAllPositionsWithBroleNameOfProcDef(@RequestParam(value = "broleNameId") String broleNameId,
                                                                        @RequestParam(value = "procDefId") String procDefId) {
        HashMap<String, Object> result = broleMappingService.retrieveAllPositionsWithBroleNameOfProcDef(broleNameId, procDefId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "给能力建立与业务角色的映射")
    @PostMapping(value = "roleMappings", params = {"mappedType=capability"})
    public ResponseEntity<?> addCapabilityBroleNameOfProcDefId(@RequestParam(value = "capabilityId") String capabilityId,
                                                               @RequestParam(value = "broleNameId") String broleNameId,
                                                               @RequestParam(value = "procDefId") String procDefId) {
        HashMap<String, Object> result = broleMappingService.addCapabilityBroleNameOfProcDef(capabilityId, broleNameId, procDefId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    @ApiOperation(value = "获取所有的映射")
    @GetMapping(value = "roleMappings")
    public ResponseEntity<?> retrieveAllRoleMapping() {
        HashMap<String, Object> result = broleMappingService.retrieveAllbroleMapping();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "获取指定流程定义的所有的映射")
    @GetMapping(value = "roleMappings/{procDefId}")
    public ResponseEntity<?> retrieveAllRoleMapping(@PathVariable(value = "procDefId") String procDefId) {
        HashMap<String, Object> result = broleMappingService.retrieveAllbroleMappingOfProcDef(procDefId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
