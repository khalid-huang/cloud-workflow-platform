package org.sysu.bpmmanagementservice.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sysu.bpmmanagementservice.dao.RenCapabilityEntityDao;
import org.sysu.bpmmanagementservice.entity.RenCapabilityEntity;
import org.sysu.bpmmanagementservice.repository.RenCapabilityEntityRepository;

import java.util.List;

@Repository
public class RenCapabilityEntityDaoImpl implements RenCapabilityEntityDao {

    @Autowired
    RenCapabilityEntityRepository renCapabilityEntityRepository;

    @Override
    public RenCapabilityEntity saveOrUpdate(RenCapabilityEntity renCapabilityEntity) {
        return renCapabilityEntityRepository.save(renCapabilityEntity);
    }

    @Override
    public RenCapabilityEntity deleteByName(String name) {
        return renCapabilityEntityRepository.deleteByName(name);
    }

    @Override
    public RenCapabilityEntity findByName(String name) {
        return renCapabilityEntityRepository.findByName(name);
    }

    @Override
    public RenCapabilityEntity findById(String id) {
        return renCapabilityEntityRepository.findById(id);
    }

    @Override
    public List<RenCapabilityEntity> findAll() {
        return renCapabilityEntityRepository.findAll();
    }
}
