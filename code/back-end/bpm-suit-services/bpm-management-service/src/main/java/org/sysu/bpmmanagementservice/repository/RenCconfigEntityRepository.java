package org.sysu.bpmmanagementservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sysu.bpmmanagementservice.entity.RenCconfigEntity;

public interface RenCconfigEntityRepository extends JpaRepository<RenCconfigEntity, String> {
    RenCconfigEntity findByRkey(String rkey);
}
