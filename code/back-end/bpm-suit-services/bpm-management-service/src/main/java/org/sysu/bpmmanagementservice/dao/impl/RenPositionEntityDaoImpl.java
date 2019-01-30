package org.sysu.bpmmanagementservice.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sysu.bpmmanagementservice.dao.RenPositionEntityDao;
import org.sysu.bpmmanagementservice.entity.RenPositionEntity;
import org.sysu.bpmmanagementservice.repository.RenPositionEntityRepository;

import java.util.List;

@Repository
public class RenPositionEntityDaoImpl implements RenPositionEntityDao {
    @Autowired
    RenPositionEntityRepository renPositionEntityRepository;

    @Override
    public RenPositionEntity saveOrUpdate(RenPositionEntity renPositionEntity) {
        return renPositionEntityRepository.save(renPositionEntity);
    }

    @Override
    public RenPositionEntity deleteByName(String name) {
        return renPositionEntityRepository.deleteByName(name);
    }

    @Override
    public List<RenPositionEntity> findAll() {
        return renPositionEntityRepository.findAll();
    }

    @Override
    public RenPositionEntity findByName(String name) {
        return renPositionEntityRepository.findByName(name);
    }

    @Override
    public RenPositionEntity findById(String id) {
        return renPositionEntityRepository.findById(id);
    }
}
