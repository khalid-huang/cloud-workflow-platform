package org.sysu.bpmmanagementservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sysu.bpmmanagementservice.entity.RenPositionEntity;

public interface RenPositionEntityRepository extends JpaRepository<RenPositionEntity, String> {
    RenPositionEntity deleteByName(String name);

    RenPositionEntity findByName(String name);

    RenPositionEntity findById(String id);
}
