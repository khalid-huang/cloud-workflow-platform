package org.sysu.bpmmanagementservice.dao;

import org.sysu.bpmmanagementservice.entity.RenGroupEntity;

import java.util.List;

public interface RenGroupEntityDao {
    RenGroupEntity saveOrUpdate(RenGroupEntity renGroupEntity);

    RenGroupEntity deleteByName(String name);

    RenGroupEntity findByName(String name);

    List<RenGroupEntity> findAll();

    RenGroupEntity findById(String id);
}
