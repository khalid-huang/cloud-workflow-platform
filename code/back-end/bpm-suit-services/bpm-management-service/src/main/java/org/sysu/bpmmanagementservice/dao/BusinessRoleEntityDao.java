package org.sysu.bpmmanagementservice.dao;

import org.sysu.bpmmanagementservice.entity.BusinessRoleEntity;

import java.util.BitSet;
import java.util.List;

public interface BusinessRoleEntityDao {
    BusinessRoleEntity saveOrUpdate(BusinessRoleEntity businessRoleEntity);

    void deleteById(String id);

    BusinessRoleEntity findById(String id);

    List<BusinessRoleEntity> findAll();
}
