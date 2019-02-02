package org.sysu.bpmmanagementservice.dao;

import org.sysu.bpmmanagementservice.entity.RoleMappingEntity;

import java.util.List;

public interface RoleMappingEntityDao {
    RoleMappingEntity saveOrUpdate(RoleMappingEntity roleMappingEntity);

    void deleteById(String id);

    List<RoleMappingEntity> findAll();

    List<RoleMappingEntity> findByMappedIdAndMappedType(String mappedId, int mappedType);

    List<RoleMappingEntity> findByBroleName(String broleName);

    List<RoleMappingEntity> findByBroleNameAndMappedType(String broleName, int mappedType);

    void deleteByMappedIdAndMappedTypeAndBroleName(String mappedId, int mappedType, String broleName);

}
