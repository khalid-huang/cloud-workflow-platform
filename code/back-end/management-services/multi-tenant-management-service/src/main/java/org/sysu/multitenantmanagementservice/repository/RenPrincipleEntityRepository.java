package org.sysu.multitenantmanagementservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sysu.multitenantmanagementservice.Entity.RenPrincipleEntity;

public interface RenPrincipleEntityRepository extends JpaRepository<RenPrincipleEntity, Long> {
    RenPrincipleEntity findByTenantId(String tenantId);
}
