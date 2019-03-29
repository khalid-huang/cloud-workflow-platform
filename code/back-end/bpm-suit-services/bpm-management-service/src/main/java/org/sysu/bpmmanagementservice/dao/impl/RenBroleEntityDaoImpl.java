package org.sysu.bpmmanagementservice.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sysu.bpmmanagementservice.dao.RenBroleEntityDao;
import org.sysu.bpmmanagementservice.entity.RenBroleEntity;
import org.sysu.bpmmanagementservice.repository.RenBroleEntityRepository;

import java.util.List;

@Repository
public class RenBroleEntityDaoImpl implements RenBroleEntityDao {
    @Autowired
    RenBroleEntityRepository renBroleEntityRepository;

    @Override
    public RenBroleEntity saveOrUpdate(RenBroleEntity renBroleEntity) {
        return renBroleEntityRepository.save(renBroleEntity);
    }

    @Override
    public RenBroleEntity findById(String id) {
        return renBroleEntityRepository.findById(id);
    }

    @Override
    public void deleteById(String id) {
        renBroleEntityRepository.deleteById(id);
    }

    @Override
    public List<RenBroleEntity> findAll() {
        return renBroleEntityRepository.findAll();
    }

    @Override
    public RenBroleEntity findByName(String name) {
        return renBroleEntityRepository.findByName(name);
    }

    @Override
    public void deleteByName(String name) {
        renBroleEntityRepository.deleteByName(name);
    }
}
