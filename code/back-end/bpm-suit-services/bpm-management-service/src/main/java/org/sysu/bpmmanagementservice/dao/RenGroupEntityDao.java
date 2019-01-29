package org.sysu.bpmmanagementservice.dao;

import org.sysu.bpmmanagementservice.entity.RenGroupEntity;

import java.util.List;

public interface RenGroupEntityDao {
    RenGroupEntity saveOrUpdate(RenGroupEntity renGroupEntity);

    RenGroupEntity deleteByName(String name);

    List<RenGroupEntity> findAll();
}
