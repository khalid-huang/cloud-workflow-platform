package org.sysu.bpmmanagementservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sysu.bpmmanagementservice.entity.BusinessRoleEntity;

public interface BusinessRoleEntityRepository extends JpaRepository<BusinessRoleEntity, String> {
    void deleteById(String id);

    BusinessRoleEntity findById(String id);
}
