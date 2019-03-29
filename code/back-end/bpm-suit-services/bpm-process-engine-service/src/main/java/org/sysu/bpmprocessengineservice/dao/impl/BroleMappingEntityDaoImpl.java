package org.sysu.bpmprocessengineservice.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sysu.bpmprocessengineservice.dao.BroleMappingEntityDao;
import org.sysu.bpmprocessengineservice.entity.BroleMappingEntity;
import org.sysu.bpmprocessengineservice.repository.BroleMappingEntityRepository;

import java.util.List;

@Repository
public class BroleMappingEntityDaoImpl implements BroleMappingEntityDao {

    @Autowired
    BroleMappingEntityRepository broleMappingEntityRepository;

    @Override
    public List<BroleMappingEntity> findByBroleNameAndProcDefId(String broleName, String procDefId) {
        return broleMappingEntityRepository.findByBroleNameAndProcDefId(broleName, procDefId);
    }
}
