package org.sysu.bpmprocessengineservice.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sysu.bpmprocessengineservice.dao.RenPositionEntityDao;
import org.sysu.bpmprocessengineservice.entity.RenPositionEntity;
import org.sysu.bpmprocessengineservice.repository.RenPositionEntityRepository;

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
    public void deleteById(String id) {
        renPositionEntityRepository.deleteById(id);
    }

    @Override
    public List<RenPositionEntity> findAll() {
        return renPositionEntityRepository.findAll();
    }

    @Override
    public RenPositionEntity findById(String id) {
        return renPositionEntityRepository.findById(id);
    }
}
