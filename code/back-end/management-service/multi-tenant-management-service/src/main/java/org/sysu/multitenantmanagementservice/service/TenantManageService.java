package org.sysu.multitenantmanagementservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.multitenantmanagementservice.Entity.TenantEntityRepository;


@Service
public class TenantManageService {
    private static Logger logger = LoggerFactory.getLogger(TenantManageService.class);

    @Autowired
    private TenantEntityRepository tenantEntityRepository;

}
