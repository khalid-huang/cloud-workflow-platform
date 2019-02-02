package org.sysu.bpmmanagementservice.controller.client;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.startup.RealmRuleSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sysu.bpmmanagementservice.service.RoleMappingService;

import java.util.HashMap;

/**
 * 业务关系相关的管理
 */
@Api(tags = "RoleMappingController", description = "业务关系相关的管理")
@RestController
@RequestMapping(value = "/roleMapping")
public class RoleMappingController {
    private static final Logger logger = LoggerFactory.getLogger(RoleMappingController.class);

    @Autowired
    RoleMappingService roleMappingService;

    @ApiOperation(value = "添加业务角色")
    @PostMapping(value = "/businessRoles/")
    public ResponseEntity<?> addBusinessRole(@RequestParam(value = "name") String name) {
        HashMap<String, Object> result = roleMappingService.addBusinessRole(name);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "根据业务角色Id删除业务角色")
    @DeleteMapping(value = "/businessRoles/{id}")
    public ResponseEntity<?> removeBusinessRoleById(@PathVariable(value = "id") String id) {
        HashMap<String, Object> result = roleMappingService.removeBusinessRoleById(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "根据业务角色Id获取业务角色相关信息")
    @GetMapping(value = "/businessRoles/{id}")
    public ResponseEntity<?> retrieveBusinessRoleById(@PathVariable(value = "id") String id) {
        HashMap<String, Object> result = roleMappingService.retrieveBusinessRoleById(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "获取全部业务角色")
    @GetMapping(value = "/businessRoles/")
    public ResponseEntity<?> retrieveAllBusinessRole() {
        HashMap<String, Object> result = roleMappingService.retrieveAllBusinessRole();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "为职位添加业务角色")
    @PostMapping(value = "roleMappings/", params = {"mappedType=position"})
    public ResponseEntity<?> addPositionBroleName(@RequestParam(value = "positionId") String positionId,
                                                  @RequestParam(value = "broleNameId") String broleNameId) {
        HashMap<String, Object> result = roleMappingService.addPositionBroleName(positionId, broleNameId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "删除指定职位的业务角色")
    @DeleteMapping(value = "roleMappings", params = {"mappedType=position"})
    public ResponseEntity<?> removePositionBroleName(@RequestParam(value = "positionId") String positionId,
                                                     @RequestParam(value = "broleNameId") String broleNameId) {
        HashMap<String, Object> result = roleMappingService.removePositionBroleName(positionId, broleNameId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "获取与业务角色建立映射的所有职位")
    @GetMapping(value = "roleMappings/positions")
    public ResponseEntity<?> retrieveAllPositionsWithBroleName(@RequestParam(value = "broleNameId") String broleNameId) {
        HashMap<String, Object> result = roleMappingService.retrieveAllPositionsWithBroleName(broleNameId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "给能力建立与业务角色的映射")
    @PostMapping(value = "roleMappings", params = {"mappedType=capability"})
    public ResponseEntity<?> addCapabilityBroleName(@RequestParam(value = "capabilityId") String capabilityId,
                                                    @RequestParam(value = "broleNameId") String broleNameId) {
        HashMap<String, Object> result = roleMappingService.addCapabilityBroleName(capabilityId, broleNameId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "删除指定能力与指定业务角色之间的映射")
    @DeleteMapping(value = "roleMappings", params = {"mappedType=Capability"})
    public ResponseEntity<?> removeCapabilityBroleName(@RequestParam(value = "capabilityId") String capabilityId,
                                                       @RequestParam(value = "broleNameId") String broleNameId) {
        HashMap<String, Object> result = roleMappingService.removeCapabilityBroleName(capabilityId, broleNameId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "获取与指定业务角色建立映射的所有能力")
    @GetMapping(value = "roleMappings/capabilities")
    public ResponseEntity<?> retrieveAllCapabilityiesWithBroleName(@RequestParam(value = "broleNameId") String broleNameId) {
        HashMap<String, Object> result = roleMappingService.retrieveAllCapabilitiesWithBroleName(broleNameId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "获取所有的映射")
    @GetMapping(value = "roleMappings")
    public ResponseEntity<?> retrieveAllRoleMapping() {
        HashMap<String, Object> result = roleMappingService.retrievAllRoleMapping();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
