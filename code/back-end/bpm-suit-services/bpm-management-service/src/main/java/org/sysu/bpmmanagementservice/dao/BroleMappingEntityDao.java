package org.sysu.bpmmanagementservice.dao;

import org.sysu.bpmmanagementservice.entity.BroleMappingEntity;

import java.util.List;

public interface BroleMappingEntityDao {
    BroleMappingEntity saveOrUpdate(BroleMappingEntity broleMappingEntity);

    void deleteById(String id);

    List<BroleMappingEntity> findAll();

    List<BroleMappingEntity> findByMappedIdAndMappedType(String mappedId, int mappedType);

    List<BroleMappingEntity> findByBroleNameAndProcDefId(String broleName, String procDefId);

    List<BroleMappingEntity> findByBroleNameAndMappedTypeAndProcDefId(String broleName, int mappedType, String procDefId);

    List<BroleMappingEntity> findByProcDefId(String procDefId);



}
