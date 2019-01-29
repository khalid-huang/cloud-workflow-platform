package org.sysu.bpmmanagementservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sysu.bpmmanagementservice.entity.ActIdUserEntity;

public interface ActIdUserEntityRepository extends JpaRepository<ActIdUserEntity, String> {
}
