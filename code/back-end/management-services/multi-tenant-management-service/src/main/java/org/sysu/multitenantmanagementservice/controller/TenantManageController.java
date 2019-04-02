package org.sysu.multitenantmanagementservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sysu.multitenantmanagementservice.Entity.RenTenantEntity;
import org.sysu.multitenantmanagementservice.service.TenantManageService;
@Api(tags = "TenantManageController", description = "租户管理接口")
@RestController
public class TenantManageController {
    private static Logger logger = LoggerFactory.getLogger(TenantManageController.class);

    @Autowired
    private TenantManageService tenantManageService;

    /**
     * 返回全部的租户信息
     * @return
     */
    @ApiOperation(value = "测试多租户数据库，使用ren_group表测试，返回全部ren_group表的内容 ")
    @RequestMapping(value = "/tenants/",method = RequestMethod.GET)
    public ResponseEntity<?> getTenants() {
        return ResponseEntity.status(HttpStatus.OK).body(tenantManageService.findAllTenant());
    }

//    @RequestMapping(value = "/tenants/{tid}", method = RequestMethod.GET)
//    public ResponseEntity<?> getOneTenant(@PathVariable(value = "tid") Long tid) {
//        return ResponseEntity.status(HttpStatus.OK).body(tenantManageService.findOneById(tid));
//    }

    /**
     * 新建
     * @param renTenantEntity
     * @return
     */
//    @RequestMapping(value = "/tenants", method = RequestMethod.POST)
//    public ResponseEntity<?> postOneTenant(@RequestBody RenTenantEntity renTenantEntity) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(tenantManageService.createOne(renTenantEntity));
//    }

    /**
     * 更新
     * @return
     */
//    @RequestMapping(value = "/tenants/{tid}", method = RequestMethod.PUT)
//    public ResponseEntity<?> putOneTenant(@PathVariable(value = "tid") Long tid,
//                                          @RequestBody RenTenantEntity renTenantEntity) {
////        return ResponseEntity.status(HttpStatus.OK).body(tenantManageService.updateOne(tid, renTenantEntity));
//    }


}
