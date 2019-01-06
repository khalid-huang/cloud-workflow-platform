package org.sysu.multitenantmanagementservice.controller;

import com.netflix.discovery.converters.Auto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sysu.multitenantmanagementservice.Entity.TenantEntity;
import org.sysu.multitenantmanagementservice.service.TenantManageService;

@RestController
public class TenantManageController {
    private static Logger logger = LoggerFactory.getLogger(TenantManageController.class);

    @Autowired
    private TenantManageService tenantManageService;

    /**
     * 返回全部的租户信息
     * @return
     */
    @RequestMapping(value = "/tenants/",method = RequestMethod.GET)
    public ResponseEntity<?> getTenants() {
        return ResponseEntity.status(HttpStatus.OK).body(tenantManageService.findAllTenant());
    }

    @RequestMapping(value = "/tenants/{tid}", method = RequestMethod.GET)
    public ResponseEntity<?> getOneTenant(@PathVariable(value = "tid") Long tid) {
        return ResponseEntity.status(HttpStatus.OK).body(tenantManageService.findOneById(tid));
    }

    /**
     * 新建
     * @param tenantEntity
     * @return
     */
    @RequestMapping(value = "/tenants", method = RequestMethod.POST)
    public ResponseEntity<?> postOneTenant(@RequestBody TenantEntity tenantEntity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tenantManageService.createOne(tenantEntity));
    }

    /**
     * 更新
     * @return
     */
    @RequestMapping(value = "/tenants/{tid}", method = RequestMethod.PUT)
    public ResponseEntity<?> putOneTenant(@PathVariable(value = "tid") Long tid,
                                          @RequestBody TenantEntity tenantEntity) {
        return ResponseEntity.status(HttpStatus.OK).body(tenantManageService.updateOne(tid, tenantEntity));
    }


}
