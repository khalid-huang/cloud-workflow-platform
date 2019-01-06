package org.sysu.multitenantmanagementservice.Entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceEntityRepository extends JpaRepository<ResourceEntity, Long> {
    ResourceEntity findByTenantId(Long tenantId);
}
