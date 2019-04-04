package org.sysu.bpmmanagementservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sysu.bpmmanagementservice.entity.ActIdUserEntity;

public interface ActIdUserEntityRepository extends JpaRepository<ActIdUserEntity, String> {
    void deleteByUsername(String username);

    ActIdUserEntity findByUsername(String username);

    void deleteByFirstNameAndLastName(String firstName, String lastName);

}
