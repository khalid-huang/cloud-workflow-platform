package org.sysu.bpmmanagementservice.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sysu.bpmmanagementservice.dao.RenCconfigEntityDao;
import org.sysu.bpmmanagementservice.entity.RenCconfigEntity;
import org.sysu.bpmmanagementservice.entity.RenConnectEntity;
import org.sysu.bpmmanagementservice.repository.RenCconfigEntityRepository;

import java.util.List;

@Repository
public class RenCconfigEntityDaoImpl implements RenCconfigEntityDao {
    @Autowired
    RenCconfigEntityRepository renCconfigEntityRepository;

    @Override
    public List<RenCconfigEntity> findAll() {
        return renCconfigEntityRepository.findAll();
    }

    @Override
    public RenCconfigEntity findByRkey(String rkey) {
        return renCconfigEntityRepository.findByRkey(rkey);
    }
}
