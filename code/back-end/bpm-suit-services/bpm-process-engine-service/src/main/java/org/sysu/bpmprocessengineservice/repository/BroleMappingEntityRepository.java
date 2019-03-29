package org.sysu.bpmprocessengineservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sysu.bpmprocessengineservice.entity.BroleMappingEntity;

import java.util.List;

public interface BroleMappingEntityRepository extends JpaRepository<BroleMappingEntity, String> {
    List <BroleMappingEntity> findByBroleNameAndProcDefId(String broleName, String procDefId);
}
