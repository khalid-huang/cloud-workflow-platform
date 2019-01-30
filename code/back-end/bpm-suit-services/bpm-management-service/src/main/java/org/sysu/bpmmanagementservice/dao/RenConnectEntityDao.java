package org.sysu.bpmmanagementservice.dao;

import org.sysu.bpmmanagementservice.entity.RenConnectEntity;

import java.util.List;

public interface RenConnectEntityDao {
    RenConnectEntity saveOrUpdate(RenConnectEntity renConnectEntity);

    List<RenConnectEntity> deleteAllByUsername(String username);

    List<RenConnectEntity> findAllByUsernameAndType(String username, int type);

    RenConnectEntity deleteByUsernameAndBelongToOrganizabledIdAndType(String username, String belongToOrganizabledId, int type);

    List<RenConnectEntity> findAll();
}
