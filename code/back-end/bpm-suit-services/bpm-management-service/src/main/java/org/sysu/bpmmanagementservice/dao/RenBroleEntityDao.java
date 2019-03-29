package org.sysu.bpmmanagementservice.dao;

import org.sysu.bpmmanagementservice.entity.BroleMappingEntity;
import org.sysu.bpmmanagementservice.entity.RenBroleEntity;

import java.util.List;

public interface RenBroleEntityDao {
    RenBroleEntity saveOrUpdate(RenBroleEntity renBroleEntity);

    void deleteById(String id);

    RenBroleEntity findById(String id);

    List<RenBroleEntity> findAll();

    RenBroleEntity findByName(String name);

    void deleteByName(String name);


}
