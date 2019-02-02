package org.sysu.bpmmanagementservice.dao;

import org.sysu.bpmmanagementservice.entity.RoleMappingEntity;

import java.util.List;

public interface RoleMappingEntityDao {
    RoleMappingEntity saveOrUpdate(RoleMappingEntity roleMappingEntity);

    void deleteById(String id);

    List<RoleMappingEntity> findAll();

    List<RoleMappingEntity> findByMappedIdAndMappedType(String mappedId, int mappedType);

    List<RoleMappingEntity> findByBroleNameId(String broleNameId);

    List<RoleMappingEntity> findByBroleNameIdAndMappedType(String broleNamIde, int mappedType);

    void deleteByMappedIdAndMappedTypeAndBroleNameId(String mappedId, int mappedType, String broleNameId);

}
