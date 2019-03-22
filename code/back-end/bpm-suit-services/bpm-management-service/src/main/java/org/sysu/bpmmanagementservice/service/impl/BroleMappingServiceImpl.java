package org.sysu.bpmmanagementservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.bpmmanagementservice.component.BroleMappingAndOrgDataHelper;
import org.sysu.bpmmanagementservice.constant.GlobalContext;
import org.sysu.bpmmanagementservice.constant.ResponseConstantManager;
import org.sysu.bpmmanagementservice.dao.*;
import org.sysu.bpmmanagementservice.entity.*;
import org.sysu.bpmmanagementservice.service.BroleMappingService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/** 基于Activiti的实现 */
@Service
public class BroleMappingServiceImpl implements BroleMappingService {
    @Autowired
    BusinessRoleEntityDao businessRoleEntityDao;

    @Autowired
    RoleMappingEntityDao roleMappingEntityDao;

    @Autowired
    RenPositionEntityDao renPositionEntityDao;

    @Autowired
    RenCapabilityEntityDao renCapabilityEntityDao;

    @Autowired
    BroleMappingAndOrgDataHelper broleMappingAndOrgDataHelper;

    @Autowired
    ActIdMembershipEntityDao actIdMembershipEntityDao;


    @Override
    public HashMap<String, Object> addBusinessRole(String name) {
        HashMap<String, Object> result = new HashMap<>();
        BusinessRoleEntity businessRoleEntity = new BusinessRoleEntity();
        businessRoleEntity.setId(UUID.randomUUID().toString());
        businessRoleEntity.setName(name);
        businessRoleEntity =  businessRoleEntityDao.saveOrUpdate(businessRoleEntity);
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("data", businessRoleEntity);
        return result;

    }

    @Override
    @Transactional
    public HashMap<String, Object> removeBusinessRoleById(String id) {
        HashMap<String, Object> result = new HashMap<>();
        businessRoleEntityDao.deleteById(id);
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("data", null);
        return result;
    }

    @Override
    public HashMap<String, Object> retrieveBusinessRoleById(String id) {
        HashMap<String, Object> result = new HashMap<>();
        BusinessRoleEntity businessRoleEntity = businessRoleEntityDao.findById(id);
        result.put("data", businessRoleEntity);
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        return result;
    }

    @Override
    public HashMap<String, Object> retrieveAllBusinessRole() {
        HashMap<String, Object> result = new HashMap<>();
        List<BusinessRoleEntity> businessRoleEntities = businessRoleEntityDao.findAll();
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("data", businessRoleEntities);
        return result;
    }

    /** 添加一条组织关系为职业的到业务关系的映射 ；因为引擎是基于Activiti的，添加之后还需要去更新Activiti的act_id_memebership表，从而才可以使activiti在根据组（业务关系）选择用户时，相应的拥有这个组织关系的用户在候选列表中*/
    /** 虽然这个操作耗时，但是这个操作很少用到，因为组织关系很少变动 */
    @Override
    public HashMap<String, Object> addPositionBroleName(String positionId, String broleNameId) {
        HashMap<String, Object> result = new HashMap<>();
        BroleMappingEntity broleMappingEntity = broleMappingAndOrgDataHelper.addRoleMappingBetweenPositionAndBroleName(positionId, broleNameId);

        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("data", broleMappingEntity);
        return result;
    }

    @Override
    @Transactional
    public HashMap<String, Object> removePositionBroleName(String positionId, String broleNameId) {
        HashMap<String, Object> result = new HashMap<>();
        broleMappingAndOrgDataHelper.removeRoleMappingBetweenPositionAndBroleNameId(positionId, broleNameId);
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("data", null);
        return result;
    }

    @Override
    public HashMap<String, Object> retrieveAllPositionsWithBroleName(String broleNameId) {
       HashMap<String, Object> result = new HashMap<>();
       List<BroleMappingEntity> roleMappingEntities = roleMappingEntityDao.findByBroleNameIdAndMappedType(broleNameId, GlobalContext.ROLEMAPPING_MAPPEDTYPE_POSITION);
       List<RenPositionEntity> renPositionEntities = new ArrayList<>();
       for(BroleMappingEntity broleMappingEntity : roleMappingEntities) {
           renPositionEntities.add(renPositionEntityDao.findById(broleMappingEntity.getMappedId()));
       }
       result.put("status", ResponseConstantManager.STATUS_SUCCESS);
       result.put("data", renPositionEntities);
       return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public HashMap<String, Object> addCapabilityBroleName(String capabilityId, String broleNameId) {
        HashMap<String, Object> result = new HashMap<>();
        BroleMappingEntity broleMappingEntity = broleMappingAndOrgDataHelper.addRoleMappingBetweenCapabilityAndBroleName(capabilityId, broleNameId);

        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("data", broleMappingEntity);
        return result;
    }

    @Override
    @Transactional
    public HashMap<String, Object> removeCapabilityBroleName(String capabilityId, String broleNameId) {
        HashMap<String, Object> result = new HashMap<>();
        broleMappingAndOrgDataHelper.removeRoleMappingBetweenCapabilityAndBroleName(capabilityId, broleNameId);
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("data", null);
        return result;
    }

    @Override
    public HashMap<String, Object> retrieveAllCapabilitiesWithBroleName(String broleNameId) {
        HashMap<String, Object> result = new HashMap<>();
        List<BroleMappingEntity> roleMappingEntities = roleMappingEntityDao.findByBroleNameIdAndMappedType(broleNameId, GlobalContext.ROLEMAPPING_MAPPEDTYPE_CAPABILITY);
        List<RenCapabilityEntity> renCapabilityEntities = new ArrayList<>();
        for(BroleMappingEntity broleMappingEntity : roleMappingEntities) {
            renCapabilityEntities.add(renCapabilityEntityDao.findById(broleMappingEntity.getMappedId()));
        }
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("data", renCapabilityEntities);
        return result;
    }

    //函数写错了，应该是返回所有的业务关系映射的
    @Override
    public HashMap<String, Object> retrieveAllRoleMapping() {
        HashMap<String, Object> result = new HashMap<>();
        List<BroleMappingEntity> roleMappingEntities = roleMappingEntityDao.findAll();
        HashMap<String, List> total = new HashMap<>();
        List<RenCapabilityEntity> renCapabilityEntities =  new ArrayList<RenCapabilityEntity>();
        List<RenPositionEntity> renPositionEntities =  new ArrayList<RenPositionEntity>();
        for(BroleMappingEntity broleMappingEntity : roleMappingEntities) {
            if(broleMappingEntity.getMappedType() == GlobalContext.ROLEMAPPING_MAPPEDTYPE_CAPABILITY) {
                renCapabilityEntities.add(renCapabilityEntityDao.findById(broleMappingEntity.getMappedId()));
            } else if(broleMappingEntity.getMappedType() == GlobalContext.ROLEMAPPING_MAPPEDTYPE_POSITION) {
                renPositionEntities.add(renPositionEntityDao.findById(broleMappingEntity.getMappedId()));
            }
        }
        total.put("capability2brole", renCapabilityEntities);
        total.put("position2brole", renPositionEntities);
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("data", total);
        return result;
    }
}
