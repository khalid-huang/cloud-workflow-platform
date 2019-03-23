package org.sysu.multitenantmanagementservice.dao;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sysu.multitenantmanagementservice.Entity.RenDatabaseSchemaEntity;
import org.sysu.multitenantmanagementservice.Entity.RenPrincipleEntity;
import org.sysu.multitenantmanagementservice.Entity.RenTenantEntity;
import org.sysu.multitenantmanagementservice.repository.RenDatabaseSchemaEntityRepository;
import org.sysu.multitenantmanagementservice.repository.RenPrincipleEntityRepository;
import org.sysu.multitenantmanagementservice.repository.RenTenantEntityRepository;
import org.sysu.multitenantmanagementservice.repository.RenTenantServiceLevelEntityRepository;
import org.sysu.multitenantmanagementservice.vo.TenantVo;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TenantDao {
    @Autowired
    RenPrincipleEntityRepository renPrincipleEntityRepository;

    @Autowired
    RenTenantEntityRepository renTenantEntityRepository;

    @Autowired
    RenDatabaseSchemaEntityRepository renDatabaseSchemaEntityRepository;

    @Autowired
    RenTenantServiceLevelEntityRepository renTenantServiceLevelEntityRepository;

    public List<TenantVo> retrieveAllTenant() {
        List<TenantVo> tenants = new ArrayList<>();
        List<RenTenantEntity> renTenantEntities = renTenantEntityRepository.findAll();
        for(RenTenantEntity renTenantEntity : renTenantEntities) {
            TenantVo tenantVo = new TenantVo();
            tenantVo.setOrgName(renTenantEntity.getOrgName());
            tenantVo.setId(renTenantEntity.getId().toString());
            if(renTenantEntity.getStatus() == 1) {
                tenantVo.setStatus("启用中");
            } else {
                tenantVo.setSchemaName("已停用");
            }

            RenPrincipleEntity renPrincipleEntity = renPrincipleEntityRepository.findByTenantId(renTenantEntity.getId().toString());
            tenantVo.setPrincipleUsername(renPrincipleEntity.getUsername());

            RenDatabaseSchemaEntity renDatabaseSchemaEntity = renDatabaseSchemaEntityRepository.findByTenantId(renTenantEntity.getId().toString());
            tenantVo.setDatabaseUrl(renDatabaseSchemaEntity.getDatabaseUrl());
            tenantVo.setSchemaName(renDatabaseSchemaEntity.getSchemaName());
            tenants.add(tenantVo);
        }
        return tenants;
    }

//    boolean addOneTenant(TenantVo tenantVo) {
//        RenTenantEntity renTenantEntity = new RenTenantEntity();
//        renTenantEntity.setOrgName(tenantVo.getOrgName());
//        renTenantEntity.setStatus(1);
//        renTenantEntity = renTenantEntityRepository.save(renTenantEntity);
//
//        RenDatabaseSchemaEntity renDatabaseSchemaEntity = new RenDatabaseSchemaEntity();
//        renDatabaseSchemaEntity.setDatabaseUrl(tenantVo.getDatabaseUrl());
//        renDatabaseSchemaEntity.setSchemaName(tenantVo.getSchemaName());
//        renDatabaseSchemaEntity.setTenantId(tenantVo.getId());
//        renDatabaseSchemaEntityRepository.save(renDatabaseSchemaEntity);
//    }
}
