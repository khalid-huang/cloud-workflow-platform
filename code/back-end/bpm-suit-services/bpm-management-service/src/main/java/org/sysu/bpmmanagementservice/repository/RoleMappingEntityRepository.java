package org.sysu.bpmmanagementservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sysu.bpmmanagementservice.entity.BroleMappingEntity;

import java.util.List;

public interface RoleMappingEntityRepository extends JpaRepository<BroleMappingEntity, String> {
    List<BroleMappingEntity> findByMappedIdAndMappedType(String mappedId, int mappedType);

    List<BroleMappingEntity> findByBroleNameId(String broleNameId);

    List<BroleMappingEntity> findByBroleNameIdAndMappedType(String broleNameId, int mappedType);

    void deleteById(String id);

    void deleteByMappedIdAndMappedTypeAndBroleNameId(String mappedId, int mappedType, String broleNameId);
}
