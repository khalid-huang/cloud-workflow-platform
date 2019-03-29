package org.sysu.bpmprocessengineservice.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sysu.bpmprocessengineservice.dao.RenConnectEntityDao;
import org.sysu.bpmprocessengineservice.entity.RenConnectEntity;
import org.sysu.bpmprocessengineservice.repository.RenConnectEntityRepository;

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
    public void deleteAllByUsername(String username) {
        renConnectEntityRepository.deleteAllByUsername(username);
    }

    @Override
    public List<RenConnectEntity> findAllByUsernameAndType(String username, int type) {
        return renConnectEntityRepository.findAllByUsernameAndType(username, type);
    }

    @Override
    public void deleteByUsernameAndBelongToOrganizabledIdAndType(String username, String belongToOrganizabledId, int type) {
        renConnectEntityRepository.deleteByUsernameAndBelongToOrganizabledIdAndType(username, belongToOrganizabledId, type);
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
