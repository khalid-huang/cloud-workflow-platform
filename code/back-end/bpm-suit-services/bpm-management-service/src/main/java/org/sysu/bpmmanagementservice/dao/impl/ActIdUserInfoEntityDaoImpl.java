package org.sysu.bpmmanagementservice.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sysu.bpmmanagementservice.dao.ActIdUserInfoEntityDao;
import org.sysu.bpmmanagementservice.entity.ActIdUserInfoEntity;
import org.sysu.bpmmanagementservice.repository.ActIdUserInfoEntityRepository;

@Repository
public class ActIdUserInfoEntityDaoImpl implements ActIdUserInfoEntityDao {
    @Autowired
    ActIdUserInfoEntityRepository actIdUserInfoEntityRepository;

    @Override
    public ActIdUserInfoEntity findByUserIdAndKey(String userId, String key) {
        return actIdUserInfoEntityRepository.findByUserIdAndKey(userId, key);
    }

    @Override
    public ActIdUserInfoEntity saveOrUpdate(ActIdUserInfoEntity actIdUserInfoEntity) {
        return actIdUserInfoEntityRepository.save(actIdUserInfoEntity);
    }
}
