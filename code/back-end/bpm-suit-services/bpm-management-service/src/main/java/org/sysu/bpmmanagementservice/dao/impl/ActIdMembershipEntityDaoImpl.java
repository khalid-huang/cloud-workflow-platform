package org.sysu.bpmmanagementservice.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sysu.bpmmanagementservice.dao.ActIdMembershipEntityDao;
import org.sysu.bpmmanagementservice.entity.ActIdMembershipEntity;
import org.sysu.bpmmanagementservice.repository.ActIdMembershipEntityRepository;

@Repository
public class ActIdMembershipEntityDaoImpl implements ActIdMembershipEntityDao {
    @Autowired
    ActIdMembershipEntityRepository actIdMembershipEntityRepository;

    @Override
    public ActIdMembershipEntity saveOrUpdate(ActIdMembershipEntity actIdMembershipEntity) {
        return actIdMembershipEntityRepository.save(actIdMembershipEntity);
    }

    @Override
    public ActIdMembershipEntity deleteByGroupIdAndUserId(String groupId, String userId) {
        return actIdMembershipEntityRepository.deleteActIdMembershipEntitiyByGroupIdAndUserId(groupId, userId);
    }
}
