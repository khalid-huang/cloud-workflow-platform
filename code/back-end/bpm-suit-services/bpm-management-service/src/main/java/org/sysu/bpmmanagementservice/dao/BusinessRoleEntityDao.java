package org.sysu.bpmmanagementservice.dao;

import org.sysu.bpmmanagementservice.entity.BusinessRoleEntity;

import java.util.BitSet;
import java.util.List;

public interface BusinessRoleEntityDao {
    BusinessRoleEntity saveOrUpdate(BusinessRoleEntity businessRoleEntity);

    BusinessRoleEntity deleteByName(String name);

    BusinessRoleEntity findByName(String name);

    List<BusinessRoleEntity> findAll();
}
