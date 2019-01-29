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
    public List<RenCapabilityEntity> findAll() {
        return renCapabilityEntityRepository.findAll();
    }
}
