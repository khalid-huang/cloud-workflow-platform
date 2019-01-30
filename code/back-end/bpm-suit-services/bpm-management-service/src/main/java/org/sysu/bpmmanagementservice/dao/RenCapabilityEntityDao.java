package org.sysu.bpmmanagementservice.dao;

import org.sysu.bpmmanagementservice.entity.RenCapabilityEntity;

import java.util.List;

public interface RenCapabilityEntityDao {
    RenCapabilityEntity saveOrUpdate(RenCapabilityEntity renCapabilityEntity);

    RenCapabilityEntity deleteByName(String name);

    RenCapabilityEntity findByName(String name);

    RenCapabilityEntity findById(String id);

    List<RenCapabilityEntity> findAll();
}
