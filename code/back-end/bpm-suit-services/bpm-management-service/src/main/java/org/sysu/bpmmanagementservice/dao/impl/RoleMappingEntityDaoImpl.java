package org.sysu.bpmmanagementservice.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sysu.bpmmanagementservice.dao.RoleMappingEntityDao;
import org.sysu.bpmmanagementservice.entity.BroleMappingEntity;
import org.sysu.bpmmanagementservice.repository.RoleMappingEntityRepository;

import java.util.List;

@Repository
public class RoleMappingEntityDaoImpl implements RoleMappingEntityDao {

    @Autowired
    RoleMappingEntityRepository roleMappingEntityRepository;

    @Override
    public BroleMappingEntity saveOrUpdate(BroleMappingEntity broleMappingEntity) {
        return roleMappingEntityRepository.save(broleMappingEntity);
    }

    @Override
    public void deleteById(String id) {
        roleMappingEntityRepository.deleteById(id);
    }

    @Override
    public List<BroleMappingEntity> findAll() {
        return roleMappingEntityRepository.findAll();
    }

    @Override
    public List<BroleMappingEntity> findByMappedIdAndMappedType(String mappedId, int mappedType) {
        return roleMappingEntityRepository.findByMappedIdAndMappedType(mappedId, mappedType);
    }

    @Override
    public List<BroleMappingEntity> findByBroleNameId(String broleNameId) {
        return roleMappingEntityRepository.findByBroleNameId(broleNameId);
    }

    @Override
    public List<BroleMappingEntity> findByBroleNameIdAndMappedType(String broleNameId, int mappedType) {
        return roleMappingEntityRepository.findByBroleNameIdAndMappedType(broleNameId, mappedType);
    }

    @Override
    public void deleteByMappedIdAndMappedTypeAndBroleNameId(String mappedId, int mappedType, String broleNameId) {
        roleMappingEntityRepository.deleteByMappedIdAndMappedTypeAndBroleNameId(mappedId, mappedType, broleNameId);
    }
}
