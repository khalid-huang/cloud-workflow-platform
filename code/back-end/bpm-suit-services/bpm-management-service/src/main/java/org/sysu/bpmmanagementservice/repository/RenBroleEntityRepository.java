package org.sysu.bpmmanagementservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sysu.bpmmanagementservice.entity.RenBroleEntity;

import java.util.List;

public interface RenBroleEntityRepository extends JpaRepository<RenBroleEntity, String> {
    RenBroleEntity findById(String id);

    RenBroleEntity findByName(String name);

    void deleteById(String id);

    List<RenBroleEntity> findAll();

    void deleteByName(String name);
}
