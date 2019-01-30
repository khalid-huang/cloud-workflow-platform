package org.sysu.bpmmanagementservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sysu.bpmmanagementservice.entity.RenCapabilityEntity;

import java.util.List;

public interface RenCapabilityEntityRepository extends JpaRepository<RenCapabilityEntity, String> {
    RenCapabilityEntity deleteByName(String name);

    RenCapabilityEntity findByName(String name);

    RenCapabilityEntity findById(String id);

}
