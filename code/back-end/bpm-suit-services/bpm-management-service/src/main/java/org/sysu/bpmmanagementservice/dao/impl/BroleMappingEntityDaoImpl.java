package org.sysu.bpmmanagementservice.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sysu.bpmmanagementservice.dao.BroleMappingEntityDao;
import org.sysu.bpmmanagementservice.entity.BroleMappingEntity;
import org.sysu.bpmmanagementservice.repository.BroleMappingEntityRepository;

import java.util.List;

@Repository
public class BroleMappingEntityDaoImpl implements BroleMappingEntityDao {

    @Autowired
    BroleMappingEntityRepository broleMappingEntityRepository;

    @Override
    public BroleMappingEntity saveOrUpdate(BroleMappingEntity broleMappingEntity) {
        return broleMappingEntityRepository.save(broleMappingEntity);
    }

    @Override
    public void deleteById(String id) {
        broleMappingEntityRepository.deleteById(id);
    }

    @Override
    public List<BroleMappingEntity> findAll() {
        return broleMappingEntityRepository.findAll();
    }

    @Override
    public List<BroleMappingEntity> findByMappedIdAndMappedType(String mappedId, int mappedType) {
        return broleMappingEntityRepository.findByMappedIdAndMappedType(mappedId, mappedType);
    }

    @Override
    public List<BroleMappingEntity> findByBroleNameAndProcDefId(String broleName, String procDefId) {
        return broleMappingEntityRepository.findByBroleNameAndProcDefId(broleName, procDefId);
    }

    @Override
    public List<BroleMappingEntity> findByBroleNameAndMappedTypeAndProcDefId(String broleName, int mappedType, String procDefId) {
        return broleMappingEntityRepository.findByBroleNameAndMappedTypeAndProcDefId(broleName, mappedType, procDefId);
    }

    @Override
    public List<BroleMappingEntity> findByProcDefId(String procDefId) {
        return broleMappingEntityRepository.findByProcDefId(procDefId);
    }
}
