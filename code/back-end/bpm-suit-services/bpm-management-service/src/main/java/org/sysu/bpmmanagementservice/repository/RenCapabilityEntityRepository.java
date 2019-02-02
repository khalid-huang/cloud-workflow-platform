package org.sysu.bpmmanagementservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sysu.bpmmanagementservice.entity.RenCapabilityEntity;

import java.util.List;

public interface RenCapabilityEntityRepository extends JpaRepository<RenCapabilityEntity, String> {
    void deleteById(String id);

    RenCapabilityEntity findById(String id);

}
