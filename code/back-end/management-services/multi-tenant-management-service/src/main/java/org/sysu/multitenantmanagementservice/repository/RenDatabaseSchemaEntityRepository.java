package org.sysu.multitenantmanagementservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sysu.multitenantmanagementservice.Entity.RenDatabaseSchemaEntity;

public interface RenDatabaseSchemaEntityRepository extends JpaRepository<RenDatabaseSchemaEntity, Long> {
    RenDatabaseSchemaEntity findByTenantId(String tenantId);
}
