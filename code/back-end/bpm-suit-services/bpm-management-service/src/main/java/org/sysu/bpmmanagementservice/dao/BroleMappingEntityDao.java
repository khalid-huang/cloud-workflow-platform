package org.sysu.bpmmanagementservice.dao;

import org.sysu.bpmmanagementservice.entity.BroleMappingEntity;

import java.util.List;

public interface BroleMappingEntityDao {
    BroleMappingEntity saveOrUpdate(BroleMappingEntity broleMappingEntity);

    void deleteById(String id);

    List<BroleMappingEntity> findAll();

    List<BroleMappingEntity> findByBroleNameAndMappedTypeAndProcDefId(String broleName, int mappedType, String procDefId);

    List<BroleMappingEntity> findByProcDefId(String procDefId);



}
