package org.sysu.bpmmanagementservice.dao;

import org.sysu.bpmmanagementservice.entity.RenCconfigEntity;
import org.sysu.bpmmanagementservice.entity.RenConnectEntity;

import java.util.List;

public interface RenCconfigEntityDao {
    List<RenCconfigEntity> findAll();

    RenCconfigEntity findByRkey(String rkey);
}
