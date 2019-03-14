package org.sysu.bpmmanagementservice.dao;

import org.sysu.bpmmanagementservice.entity.BroleMappingEntity;

import java.util.List;

public interface RoleMappingEntityDao {
    BroleMappingEntity saveOrUpdate(BroleMappingEntity broleMappingEntity);

    void deleteById(String id);

    List<BroleMappingEntity> findAll();

    List<BroleMappingEntity> findByMappedIdAndMappedType(String mappedId, int mappedType);

    List<BroleMappingEntity> findByBroleNameId(String broleNameId);

    List<BroleMappingEntity> findByBroleNameIdAndMappedType(String broleNamIde, int mappedType);

    void deleteByMappedIdAndMappedTypeAndBroleNameId(String mappedId, int mappedType, String broleNameId);

}
