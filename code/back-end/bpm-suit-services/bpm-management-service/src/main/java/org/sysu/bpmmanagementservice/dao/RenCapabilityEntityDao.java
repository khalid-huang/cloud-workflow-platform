package org.sysu.bpmmanagementservice.dao;

import org.sysu.bpmmanagementservice.entity.RenCapabilityEntity;

import java.util.List;

public interface RenCapabilityEntityDao {
    RenCapabilityEntity saveOrUpdate(RenCapabilityEntity renCapabilityEntity);

    RenCapabilityEntity deleteByName(String name);

    List<RenCapabilityEntity> findAll();
}
