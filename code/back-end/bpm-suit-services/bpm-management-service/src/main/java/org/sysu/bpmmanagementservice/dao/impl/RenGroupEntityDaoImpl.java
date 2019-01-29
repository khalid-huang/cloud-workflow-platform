package org.sysu.bpmmanagementservice.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sysu.bpmmanagementservice.dao.RenGroupEntityDao;
import org.sysu.bpmmanagementservice.entity.RenGroupEntity;
import org.sysu.bpmmanagementservice.repository.RenGroupEntityRepository;

import java.util.List;

@Repository
public class RenGroupEntityDaoImpl implements RenGroupEntityDao {
    @Autowired
    RenGroupEntityRepository renGroupEntityRepository;

    @Override
    public RenGroupEntity saveOrUpdate(RenGroupEntity renGroupEntity) {
        return renGroupEntityRepository.save(renGroupEntity);
    }

    @Override
    public RenGroupEntity deleteByName(String name) {
        return renGroupEntityRepository.deleteByName(name);
    }

    @Override
    public List<RenGroupEntity> findAll() {
        return renGroupEntityRepository.findAll();
    }
}
