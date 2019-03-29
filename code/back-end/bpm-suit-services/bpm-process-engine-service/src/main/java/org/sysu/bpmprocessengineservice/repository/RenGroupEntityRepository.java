package org.sysu.bpmprocessengineservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sysu.bpmprocessengineservice.entity.RenGroupEntity;

public interface RenGroupEntityRepository extends JpaRepository<RenGroupEntity, String> {
    void deleteById(String id);

    RenGroupEntity findById(String id);
}
