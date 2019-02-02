package org.sysu.bpmmanagementservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sysu.bpmmanagementservice.entity.RoleMappingEntity;

import java.util.List;

public interface RoleMappingEntityRepository extends JpaRepository<RoleMappingEntity, String> {
    List<RoleMappingEntity> findByMappedIdAndMappedType(String mappedId, int mappedType);

    List<RoleMappingEntity> findByBroleName(String broleName);

    List<RoleMappingEntity> findByBroleNameAndMappedType(String broleName, int mappedType);

    void deleteById(String id);

    void deleteByMappedIdAndMappedTypeAndBroleName(String mappedId, int mappedType, String broleName);
}
