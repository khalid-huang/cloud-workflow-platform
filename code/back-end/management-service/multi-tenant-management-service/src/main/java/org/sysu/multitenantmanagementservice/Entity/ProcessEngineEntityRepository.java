package org.sysu.multitenantmanagementservice.Entity;


import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessEngineEntityRepository extends JpaRepository<ProcessEngineEntity, Long> {
    ProcessEngineEntity findByReAndResourceId(Long resourceId);
}
