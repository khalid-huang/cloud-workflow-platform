package org.sysu.bpmmanagementservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sysu.bpmmanagementservice.entity.RoleMappingEntity;

import java.util.List;

public interface RoleMappingEntityRepository extends JpaRepository<RoleMappingEntity, String> {
    List<RoleMappingEntity> findByMappedIdAndMappedType(String mappedId, int mappedType);

    List<RoleMappingEntity> findByBroleNameId(String broleNameId);

    List<RoleMappingEntity> findByBroleNameIdAndMappedType(String broleNameId, int mappedType);

    void deleteById(String id);

    void deleteByMappedIdAndMappedTypeAndBroleNameId(String mappedId, int mappedType, String broleNameId);
}
