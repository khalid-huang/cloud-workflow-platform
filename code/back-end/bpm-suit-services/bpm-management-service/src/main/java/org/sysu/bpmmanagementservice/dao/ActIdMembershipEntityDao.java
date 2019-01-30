package org.sysu.bpmmanagementservice.dao;


import org.sysu.bpmmanagementservice.entity.ActIdMembershipEntity;

public interface ActIdMembershipEntityDao {
    ActIdMembershipEntity saveOrUpdate(ActIdMembershipEntity actIdMembershipEntity);

    ActIdMembershipEntity deleteByGroupIdAndUserId(String groupId, String userId);

    ActIdMembershipEntity findByGroupIdAndUserId(String groupId, String userId);
}
