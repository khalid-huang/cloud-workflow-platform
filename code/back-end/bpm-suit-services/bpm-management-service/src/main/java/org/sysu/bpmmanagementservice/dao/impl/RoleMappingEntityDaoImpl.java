package org.sysu.bpmmanagementservice.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sysu.bpmmanagementservice.dao.RoleMappingEntityDao;
import org.sysu.bpmmanagementservice.entity.RoleMappingEntity;
import org.sysu.bpmmanagementservice.repository.RoleMappingEntityRepository;

import java.util.List;

@Repository
public class RoleMappingEntityDaoImpl implements RoleMappingEntityDao {

    @Autowired
    RoleMappingEntityRepository roleMappingEntityRepository;

    @Override
    public RoleMappingEntity saveOrUpdate(RoleMappingEntity roleMappingEntity) {
        return roleMappingEntityRepository.save(roleMappingEntity);
    }

    @Override
    public void deleteById(String id) {
        roleMappingEntityRepository.deleteById(id);
    }

    @Override
    public List<RoleMappingEntity> findAll() {
        return roleMappingEntityRepository.findAll();
    }

    @Override
    public List<RoleMappingEntity> findByMappedIdAndMappedType(String mappedId, int mappedType) {
        return roleMappingEntityRepository.findByMappedIdAndMappedType(mappedId, mappedType);
    }

    @Override
    public List<RoleMappingEntity> findByBroleName(String broleName) {
        return roleMappingEntityRepository.findByBroleName(broleName);
    }

    @Override
    public List<RoleMappingEntity> findByBroleNameAndMappedType(String broleName, int mappedType) {
        return roleMappingEntityRepository.findByBroleNameAndMappedType(broleName, mappedType);
    }

    @Override
    public void deleteByMappedIdAndMappedTypeAndBroleName(String mappedId, int mappedType, String broleName) {
        roleMappingEntityRepository.deleteByMappedIdAndMappedTypeAndBroleName(mappedId, mappedType, broleName);
    }
}
