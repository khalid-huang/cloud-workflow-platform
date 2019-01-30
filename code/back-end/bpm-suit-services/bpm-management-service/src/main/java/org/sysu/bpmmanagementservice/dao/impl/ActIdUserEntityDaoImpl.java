package org.sysu.bpmmanagementservice.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sysu.bpmmanagementservice.dao.ActIdUserEntityDao;
import org.sysu.bpmmanagementservice.entity.ActIdUserEntity;
import org.sysu.bpmmanagementservice.repository.ActIdUserEntityRepository;

import java.util.List;

@Repository
public class ActIdUserEntityDaoImpl implements ActIdUserEntityDao {
    @Autowired
    ActIdUserEntityRepository actIdUserEntityRepository;

    @Override
    public ActIdUserEntity saveOrUpdate(ActIdUserEntity actIdUserEntity) {
        return actIdUserEntityRepository.save(actIdUserEntity);
    }

    @Override
    public ActIdUserEntity findById(String id) {
        return actIdUserEntityRepository.findById(id);
    }

    @Override
    public ActIdUserEntity deleteById(String id) {
        return actIdUserEntityRepository.deleteById(id);
    }

    @Override
    public ActIdUserEntity deleteByFirstNameAndLastName(String firstName, String lastName) {
        return actIdUserEntityRepository.deleteByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public List<ActIdUserEntity> findAll() {
        return actIdUserEntityRepository.findAll();
    }

    @Override
    public ActIdUserEntity findByUsername(String username) {
        return actIdUserEntityRepository.findByUsername(username);
    }
}
