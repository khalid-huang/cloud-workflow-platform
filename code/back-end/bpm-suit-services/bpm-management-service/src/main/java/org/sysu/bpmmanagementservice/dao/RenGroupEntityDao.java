package org.sysu.bpmmanagementservice.dao;

import org.sysu.bpmmanagementservice.entity.RenGroupEntity;

import java.util.List;

public interface RenGroupEntityDao {
    RenGroupEntity saveOrUpdate(RenGroupEntity renGroupEntity);

    RenGroupEntity deleteById(String id);

    List<RenGroupEntity> findAll();

    RenGroupEntity findById(String id);
}
