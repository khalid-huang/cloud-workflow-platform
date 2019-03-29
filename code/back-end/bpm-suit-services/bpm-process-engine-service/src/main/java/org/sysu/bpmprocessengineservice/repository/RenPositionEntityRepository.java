package org.sysu.bpmprocessengineservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sysu.bpmprocessengineservice.entity.RenPositionEntity;

public interface RenPositionEntityRepository extends JpaRepository<RenPositionEntity, String> {
    void deleteById(String id);

    RenPositionEntity findById(String id);
}
