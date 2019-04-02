package org.sysu.multitenantmanagementservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sysu.multitenantmanagementservice.Entity.RenTenantServiceLevelEntity;

public interface RenTenantServiceLevelEntityRepository extends JpaRepository<RenTenantServiceLevelEntity, Long> {
    RenTenantServiceLevelEntity findByTenantId(String tenantId);
}
