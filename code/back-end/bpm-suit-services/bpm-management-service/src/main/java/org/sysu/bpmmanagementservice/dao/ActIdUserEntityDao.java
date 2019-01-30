package org.sysu.bpmmanagementservice.dao;

import org.sysu.bpmmanagementservice.entity.ActIdUserEntity;

import java.util.List;

public interface ActIdUserEntityDao {
    ActIdUserEntity saveOrUpdate(ActIdUserEntity actIdUserEntity);

    ActIdUserEntity deleteByUsername(String username);

    ActIdUserEntity deleteByFirstNameAndLastName(String firstName, String lastName);

    List<ActIdUserEntity> findAll();

    ActIdUserEntity findByUsername(String username);
}
