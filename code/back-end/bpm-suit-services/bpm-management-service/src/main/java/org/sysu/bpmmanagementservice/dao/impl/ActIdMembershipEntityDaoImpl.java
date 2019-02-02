package org.sysu.bpmmanagementservice.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sysu.bpmmanagementservice.dao.ActIdMembershipEntityDao;
import org.sysu.bpmmanagementservice.entity.ActIdMembershipEntity;
import org.sysu.bpmmanagementservice.repository.ActIdMembershipEntityRepository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ActIdMembershipEntityDaoImpl implements ActIdMembershipEntityDao {
    @Autowired
    ActIdMembershipEntityRepository actIdMembershipEntityRepository;

    @Override
    public ActIdMembershipEntity saveOrUpdate(ActIdMembershipEntity actIdMembershipEntity) {
        return actIdMembershipEntityRepository.save(actIdMembershipEntity);
    }

    @Override
    public void deleteByGroupIdAndUserId(String groupId, String userId) {
        actIdMembershipEntityRepository.deleteByGroupIdAndUserId(groupId, userId);
    }

    @Override
    public ActIdMembershipEntity findByGroupIdAndUserId(String groupId, String userId) {
        return actIdMembershipEntityRepository.findByGroupIdAndUserId(groupId, userId);
    }

    @Override
    public void deleteAllByUserId(String userId) {
         actIdMembershipEntityRepository.deleteAllByUserId(userId);
    }
}
