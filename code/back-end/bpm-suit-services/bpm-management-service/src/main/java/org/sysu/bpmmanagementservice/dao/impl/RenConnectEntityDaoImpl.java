package org.sysu.bpmmanagementservice.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sysu.bpmmanagementservice.dao.RenConnectEntityDao;
import org.sysu.bpmmanagementservice.entity.RenConnectEntity;
import org.sysu.bpmmanagementservice.repository.RenConnectEntityRepository;

import java.util.List;

@Repository
public class RenConnectEntityDaoImpl implements RenConnectEntityDao {
    @Autowired
    RenConnectEntityRepository renConnectEntityRepository;

    @Override
    public RenConnectEntity saveOrUpdate(RenConnectEntity renConnectEntity) {
        return renConnectEntityRepository.save(renConnectEntity);
    }

    @Override
    public List<RenConnectEntity> deleteAllByUsername(String username) {
        return renConnectEntityRepository.deleteAllByUsername(username);
    }

    @Override
    public List<RenConnectEntity> findAllByUsernameAndType(String username, int type) {
        return renConnectEntityRepository.findAllByUsernameAndType(username, type);
    }

    @Override
    public RenConnectEntity deleteByUsernameAndBelongToOrganizabledIdAndType(String username, String belongToOrganizabledId, int type) {
        return renConnectEntityRepository.deleteByUsernameAndBelongToOrganizabledIdAAndType(username, belongToOrganizabledId, type);
    }

    @Override
    public List<RenConnectEntity> findAll() {
        return renConnectEntityRepository.findAll();
    }

    @Override
    public List<RenConnectEntity> findAllByBelongToOrganizabledIdAndType(String belongToOrganizabled, int type) {
        return renConnectEntityRepository.findAllByBelongToOrganizabledIdAndType(belongToOrganizabled, type);
    }
}
