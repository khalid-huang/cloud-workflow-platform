package org.sysu.bpmmanagementservice.dao;

import org.sysu.bpmmanagementservice.entity.ActIdUserInfoEntity;

public interface ActIdUserInfoEntityDao {
    ActIdUserInfoEntity findByUserIdAndKey(String userId, String key);

    ActIdUserInfoEntity saveOrUpdate(ActIdUserInfoEntity actIdUserInfoEntity);

}
