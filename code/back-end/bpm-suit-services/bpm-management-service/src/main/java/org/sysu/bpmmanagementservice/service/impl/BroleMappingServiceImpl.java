package org.sysu.bpmmanagementservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.bpmmanagementservice.constant.GlobalContext;
import org.sysu.bpmmanagementservice.constant.ResponseConstantManager;
import org.sysu.bpmmanagementservice.dao.*;
import org.sysu.bpmmanagementservice.entity.*;
import org.sysu.bpmmanagementservice.service.BroleMappingService;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class BroleMappingServiceImpl implements BroleMappingService {
    @Autowired
    RenBroleEntityDao renBroleEntityDao;

    @Autowired
    BroleMappingEntityDao broleMappingEntityDao;

    @Autowired
    RenPositionEntityDao renPositionEntityDao;

    @Autowired
    RenCapabilityEntityDao renCapabilityEntityDao;

    @Override
    public HashMap<String, Object> addBrole(String name) {
        HashMap<String, Object> result = new HashMap<>();
        RenBroleEntity renBroleEntity = new RenBroleEntity();
        renBroleEntity.setId(UUID.randomUUID().toString());
        renBroleEntity.setName(name);
        renBroleEntity =  renBroleEntityDao.saveOrUpdate(renBroleEntity);
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("data", renBroleEntity);
        return result;

    }

    @Override
    @Transactional
    public HashMap<String, Object> removeBroleByName(String name) {
        HashMap<String, Object> result = new HashMap<>();
        renBroleEntityDao.deleteByName(name);
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("data", null);
        return result;
    }

    @Override
    public HashMap<String, Object> retrieveBroleByName(String name) {
        HashMap<String, Object> result = new HashMap<>();
        RenBroleEntity renBroleEntity = renBroleEntityDao.findByName(name);
        result.put("data", renBroleEntity);
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        return result;
    }

    @Override
    public HashMap<String, Object> retrieveAllBrole() {
        HashMap<String, Object> result = new HashMap<>();
        List<RenBroleEntity> renBroleEntities = renBroleEntityDao.findAll();
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("data", renBroleEntities);
        return result;
    }

    @Override
    public HashMap<String, Object> addPositionBroleNameOfProcDef(String positionId, String broleName, String procDefId) {
        HashMap<String, Object> result = new HashMap<>();
        BroleMappingEntity broleMappingEntity = new BroleMappingEntity();
        broleMappingEntity.setId(UUID.randomUUID().toString());
        broleMappingEntity.setBroleName(broleName);
        broleMappingEntity.setMappedType(GlobalContext.RENCONNECT_TYPE_POSITION);
        broleMappingEntity.setMappedId(positionId);
        broleMappingEntity.setProcDefId(procDefId);
        broleMappingEntity = broleMappingEntityDao.saveOrUpdate(broleMappingEntity);
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("data", broleMappingEntity);
        return result;
    }

    @Override
    public HashMap<String, Object> retrieveAllbroleMapping() {
        HashMap<String, Object> result = new HashMap<>();
        List<BroleMappingEntity> broleMappingEntities = broleMappingEntityDao.findAll();
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("data", broleMappingEntities);
        return result;
    }

    @Override
    public HashMap<String, Object> retrieveAllPositionsWithBroleNameOfProcDef(String broleName, String procDefId) {
        HashMap<String, Object> result = new HashMap<>();
        List<BroleMappingEntity> broleMappingEntities = broleMappingEntityDao.findByBroleNameAndMappedTypeAndProcDefId(
                broleName, GlobalContext.ROLEMAPPING_MAPPEDTYPE_POSITION, procDefId
        );
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("data", broleMappingEntities);
        return result;
    }

    @Override
    public HashMap<String, Object> addCapabilityBroleNameOfProcDef(String capabilityId, String broleName, String procDefId) {
        HashMap<String, Object> result = new HashMap<>();
        BroleMappingEntity broleMappingEntity = new BroleMappingEntity();
        broleMappingEntity.setId(UUID.randomUUID().toString());
        broleMappingEntity.setBroleName(broleName);
        broleMappingEntity.setMappedType(GlobalContext.RENCONNECT_TYPE_CAPABILITY);
        broleMappingEntity.setMappedId(capabilityId);
        broleMappingEntity.setProcDefId(procDefId);
        broleMappingEntity = broleMappingEntityDao.saveOrUpdate(broleMappingEntity);
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("data", broleMappingEntity);
        return result;
    }

    @Override
    public HashMap<String, Object> retrieveAllCapabilitiesWithBroleNameOfProcDefId(String broleName, String procDefId) {
        HashMap<String, Object> result = new HashMap<>();
        List<BroleMappingEntity> broleMappingEntities = broleMappingEntityDao.findByBroleNameAndMappedTypeAndProcDefId(
                broleName, GlobalContext.ROLEMAPPING_MAPPEDTYPE_POSITION, procDefId
        );
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("data", broleMappingEntities);
        return result;
    }

    @Override
    public HashMap<String, Object> retrieveAllbroleMappingOfProcDef(String procDefId) {
        HashMap<String, Object> result = new HashMap<>();
        List<BroleMappingEntity> broleMappingEntities = broleMappingEntityDao.findByProcDefId(procDefId);
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("data", broleMappingEntities);
        return result;
    }

    @Override
    public HashMap<String, Object> removeBroleMappingById(String id) {
        HashMap<String, Object> result = new HashMap<>();
        broleMappingEntityDao.deleteById(id);
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("data", null);
        return result;
    }

}
