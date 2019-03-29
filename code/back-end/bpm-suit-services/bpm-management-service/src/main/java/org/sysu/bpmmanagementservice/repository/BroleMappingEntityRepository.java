package org.sysu.bpmmanagementservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sysu.bpmmanagementservice.entity.BroleMappingEntity;

import java.util.List;

public interface BroleMappingEntityRepository extends JpaRepository<BroleMappingEntity, String> {
    List<BroleMappingEntity> findByMappedIdAndMappedType(String mappedId, int mappedType);

    List<BroleMappingEntity> findByBroleNameAndProcDefId(String broleName, String procDefId);

    List<BroleMappingEntity> findByBroleNameAndMappedTypeAndProcDefId(String broleName, int mappedType, String procDefId);

    List<BroleMappingEntity> findByProcDefId(String procDefId);

    void deleteById(String id);

}
