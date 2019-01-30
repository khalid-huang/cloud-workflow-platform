package org.sysu.bpmmanagementservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sysu.bpmmanagementservice.entity.BusinessRoleEntity;

public interface BusinessRoleEntityRepository extends JpaRepository<BusinessRoleEntity, String> {
    BusinessRoleEntity deleteByName(String name);

    BusinessRoleEntity findByName(String name);
}
