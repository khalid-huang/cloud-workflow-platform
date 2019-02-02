package org.sysu.bpmmanagementservice.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sysu.bpmmanagementservice.dao.BusinessRoleEntityDao;
import org.sysu.bpmmanagementservice.entity.BusinessRoleEntity;
import org.sysu.bpmmanagementservice.repository.BusinessRoleEntityRepository;

import java.util.List;

@Repository
public class BusinessRoleEntityDaoImpl implements BusinessRoleEntityDao {
    @Autowired
    BusinessRoleEntityRepository businessRoleEntityRepository;

    @Override
    public BusinessRoleEntity saveOrUpdate(BusinessRoleEntity businessRoleEntity) {
        return businessRoleEntityRepository.save(businessRoleEntity);
    }

    @Override
    public BusinessRoleEntity findByName(String name) {
        return businessRoleEntityRepository.findByName(name);
    }

    @Override
    public void deleteByName(String name) {
        businessRoleEntityRepository.deleteByName(name);
    }

    @Override
    public List<BusinessRoleEntity> findAll() {
        return businessRoleEntityRepository.findAll();
    }
}
