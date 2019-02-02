package org.sysu.bpmmanagementservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.bpmmanagementservice.component.RoleMappingAndOrgDataHelper;
import org.sysu.bpmmanagementservice.constant.GlobalContext;
import org.sysu.bpmmanagementservice.constant.ResponseConstantManager;
import org.sysu.bpmmanagementservice.dao.*;
import org.sysu.bpmmanagementservice.entity.*;
import org.sysu.bpmmanagementservice.service.RoleMappingService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/** 基于Activiti的实现 */
@Service
public class RoleMappingServiceImpl implements RoleMappingService {
    @Autowired
    BusinessRoleEntityDao businessRoleEntityDao;

    @Autowired
    RoleMappingEntityDao roleMappingEntityDao;

    @Autowired
    RenConnectEntityDao renConnectEntityDao;

    @Autowired
    RenPositionEntityDao renPositionEntityDao;

    @Autowired
    RenCapabilityEntityDao renCapabilityEntityDao;

    @Autowired
    RoleMappingAndOrgDataHelper roleMappingAndOrgDataHelper;

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
        RoleMappingEntity roleMappingEntity = roleMappingAndOrgDataHelper.addRoleMappingBetweenPositionAndBroleName(positionId, broleNameId);

        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("data", roleMappingEntity);
        return result;
    }

    @Override
    @Transactional
    public HashMap<String, Object> removePositionBroleName(String positionId, String broleNameId) {
        HashMap<String, Object> result = new HashMap<>();
        roleMappingAndOrgDataHelper.removeRoleMappingBetweenPositionAndBroleNameId(positionId, broleNameId);
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("data", null);
        return result;
    }

    @Override
    public HashMap<String, Object> retrieveAllPositionsWithBroleName(String broleNameId) {
       HashMap<String, Object> result = new HashMap<>();
       List<RoleMappingEntity> roleMappingEntities = roleMappingEntityDao.findByBroleNameIdAndMappedType(broleNameId, GlobalContext.ROLEMAPPING_MAPPEDTYPE_POSITION);
       List<RenPositionEntity> renPositionEntities = new ArrayList<>();
       for(RoleMappingEntity roleMappingEntity : roleMappingEntities) {
           renPositionEntities.add(renPositionEntityDao.findById(roleMappingEntity.getMappedId()));
       }
       result.put("status", ResponseConstantManager.STATUS_SUCCESS);
       result.put("data", renPositionEntities);
       return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public HashMap<String, Object> addCapabilityBroleName(String capabilityId, String broleNameId) {
        HashMap<String, Object> result = new HashMap<>();
        RoleMappingEntity roleMappingEntity = roleMappingAndOrgDataHelper.addRoleMappingBetweenCapabilityAndBroleName(capabilityId, broleNameId);

        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("data", roleMappingEntity);
        return result;
    }

    @Override
    @Transactional
    public HashMap<String, Object> removeCapabilityBroleName(String capabilityId, String broleNameId) {
        HashMap<String, Object> result = new HashMap<>();
        roleMappingAndOrgDataHelper.removeRoleMappingBetweenCapabilityAndBroleName(capabilityId, broleNameId);
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("data", null);
        return result;
    }

    @Override
    public HashMap<String, Object> retrieveAllCapabilitiesWithBroleName(String broleNameId) {
        HashMap<String, Object> result = new HashMap<>();
        List<RoleMappingEntity> roleMappingEntities = roleMappingEntityDao.findByBroleNameIdAndMappedType(broleNameId, GlobalContext.ROLEMAPPING_MAPPEDTYPE_CAPABILITY);
        List<RenCapabilityEntity> renCapabilityEntities = new ArrayList<>();
        for(RoleMappingEntity roleMappingEntity : roleMappingEntities) {
            renCapabilityEntities.add(renCapabilityEntityDao.findById(roleMappingEntity.getMappedId()));
        }
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("data", renCapabilityEntities);
        return result;
    }

    @Override
    public HashMap<String, Object> retrievAllRoleMapping() {
        HashMap<String, Object> result = new HashMap<>();
        List<RoleMappingEntity> roleMappingEntities = roleMappingEntityDao.findAll();
        HashMap<String, List> total = new HashMap<>();
        List<RenCapabilityEntity> renCapabilityEntities =  new ArrayList<RenCapabilityEntity>();
        List<RenPositionEntity> renPositionEntities =  new ArrayList<RenPositionEntity>();
        for(RoleMappingEntity roleMappingEntity : roleMappingEntities) {
            if(roleMappingEntity.getMappedType() == GlobalContext.ROLEMAPPING_MAPPEDTYPE_CAPABILITY) {
                renCapabilityEntities.add(renCapabilityEntityDao.findById(roleMappingEntity.getMappedId()));
            } else if(roleMappingEntity.getMappedType() == GlobalContext.ROLEMAPPING_MAPPEDTYPE_POSITION) {
                renPositionEntities.add(renPositionEntityDao.findById(roleMappingEntity.getMappedId()));
            }
        }
        total.put("capability2brole", renCapabilityEntities);
        total.put("position2brole", renPositionEntities);
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("data", total);
        return result;
    }
}
