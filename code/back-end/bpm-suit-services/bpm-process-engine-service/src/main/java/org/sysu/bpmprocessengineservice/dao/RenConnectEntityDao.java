package org.sysu.bpmprocessengineservice.dao;


import org.sysu.bpmprocessengineservice.entity.RenConnectEntity;

import java.util.List;

public interface RenConnectEntityDao {
    RenConnectEntity saveOrUpdate(RenConnectEntity renConnectEntity);

    void deleteAllByUsername(String username);

    List<RenConnectEntity> findAllByUsernameAndType(String username, int type);

    void deleteByUsernameAndBelongToOrganizabledIdAndType(String username, String belongToOrganizabledId, int type);

    List<RenConnectEntity> findAllByBelongToOrganizabledIdAndType(String belongToOrganizabled, int type);

    List<RenConnectEntity> findAll();
}
