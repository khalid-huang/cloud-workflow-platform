package org.sysu.bpmmanagementservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.bpmmanagementservice.constant.GlobalContext;
import org.sysu.bpmmanagementservice.constant.ResponseConstantManager;
import org.sysu.bpmmanagementservice.dao.*;
import org.sysu.bpmmanagementservice.entity.*;
import org.sysu.bpmmanagementservice.service.OrgDataService;
import org.sysu.bpmmanagementservice.service.RoleMappingService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    OrgDataService orgDataService;

    @Autowired
    ActIdMembershipEntityDao actIdMembershipEntityDao;

    @Override
    public HashMap<String, Object> addBusinessRole(String name) {
        HashMap<String, Object> result = new HashMap<>();
        BusinessRoleEntity businessRoleEntity = new BusinessRoleEntity();
        businessRoleEntity.setName(name);
        businessRoleEntity =  businessRoleEntityDao.saveOrUpdate(businessRoleEntity);
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("data", businessRoleEntity);
        return result;

    }

    @Override
    public HashMap<String, Object> removeBusinessRoleByName(String name) {
        HashMap<String, Object> result = new HashMap<>();
        BusinessRoleEntity businessRoleEntity = businessRoleEntityDao.deleteByName(name);
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("data", businessRoleEntity);
        return result;
    }

    @Override
    public HashMap<String, Object> retrieveBusinessRoleByName(String name) {
        HashMap<String, Object> result = new HashMap<>();
        BusinessRoleEntity businessRoleEntity = businessRoleEntityDao.findByName(name);
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
    @SuppressWarnings("unchecked")
    public HashMap<String, Object> addPositionRoleName(String positionId, String roleName) {
        HashMap<String, Object> result = new HashMap<>();
        RoleMappingEntity roleMappingEntity = new RoleMappingEntity();
        roleMappingEntity.setBroleName(roleName);
        roleMappingEntity.setMappedId(positionId);
        roleMappingEntity.setMappedType(GlobalContext.ROLEMAPPING_MAPPEDTYPE_POSITION);
        roleMappingEntity =  roleMappingEntityDao.saveOrUpdate(roleMappingEntity);
        //更新activiti的用户与组表
        //查找有这个职位的所有用户
        ArrayList<String> usernames = (ArrayList<String>) orgDataService.retrieveAllHumanInPosition(positionId).get("data");
        //添加查找到的用户与业务角色的联系
        //添加的要注意bind_num，添加一次就加一
        for(String username : usernames) {
            ActIdMembershipEntity actIdMembershipEntity = actIdMembershipEntityDao.findByGroupIdAndUserId(roleName, username);
            //新建或更新
            if(actIdMembershipEntity == null) {
                actIdMembershipEntity = new ActIdMembershipEntity();
                actIdMembershipEntity.setBindNum(1);
                actIdMembershipEntity.setGroupId(roleName);
                actIdMembershipEntity.setUserId(username);
            } else {
                actIdMembershipEntity.setBindNum(actIdMembershipEntity.getBindNum() + 1);
            }
            actIdMembershipEntityDao.saveOrUpdate(actIdMembershipEntity);
        }
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("data", roleMappingEntity);
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public HashMap<String, Object> removePositionRoleName(String positionId, String roleName) {
        HashMap<String, Object> result = new HashMap<>();
        //删除actiivti里面的membeship表
        ArrayList<String> usernames = (ArrayList<String>) orgDataService.retrieveAllHumanInPosition(positionId).get("data");
        for(String username : usernames) {
            //删除或更新
            ActIdMembershipEntity actIdMembershipEntity = actIdMembershipEntityDao.findByGroupIdAndUserId(roleName, username);
            if(actIdMembershipEntity.getBindNum() == 1) {
                actIdMembershipEntityDao.deleteByGroupIdAndUserId(roleName, username);
            } else {
                actIdMembershipEntity.setBindNum(actIdMembershipEntity.getBindNum() - 1);
                actIdMembershipEntityDao.saveOrUpdate(actIdMembershipEntity);
            }
        }

        //删除roleMapping表
        RoleMappingEntity roleMappingEntity = roleMappingEntityDao.deleteByMappedIdAndMappedTypeAndBroleName(positionId, GlobalContext.ROLEMAPPING_MAPPEDTYPE_POSITION, roleName);
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("data", roleMappingEntity);
        return result;
    }

    @Override
    public HashMap<String, Object> retrieveAllPositionsWithRoleName(String roleName) {
       HashMap<String, Object> result = new HashMap<>();
       List<RoleMappingEntity> roleMappingEntities = roleMappingEntityDao.findByBroleNameAndMappedType(roleName, GlobalContext.ROLEMAPPING_MAPPEDTYPE_POSITION);
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
    public HashMap<String, Object> addCapabilityRoleName(String capabilityId, String roleName) {
        HashMap<String, Object> result = new HashMap<>();
        RoleMappingEntity roleMappingEntity = new RoleMappingEntity();
        roleMappingEntity.setBroleName(roleName);
        roleMappingEntity.setMappedId(capabilityId);
        roleMappingEntity.setMappedType(GlobalContext.ROLEMAPPING_MAPPEDTYPE_CAPABILITY);
        roleMappingEntity =  roleMappingEntityDao.saveOrUpdate(roleMappingEntity);
        //更新activiti的用户与组表
        //查找有这个能力的所有用户
        ArrayList<String> usernames = (ArrayList<String>) orgDataService.retrieveAllHumanWithCapability(capabilityId).get("data");
        //添加查找到的用户与业务角色的联系
        //添加的要注意bind_num，添加一次就加一
        for(String username : usernames) {
            ActIdMembershipEntity actIdMembershipEntity = actIdMembershipEntityDao.findByGroupIdAndUserId(roleName, username);
            //新建或更新
            if(actIdMembershipEntity == null) {
                actIdMembershipEntity = new ActIdMembershipEntity();
                actIdMembershipEntity.setBindNum(1);
                actIdMembershipEntity.setGroupId(roleName);
                actIdMembershipEntity.setUserId(username);
            } else {
                actIdMembershipEntity.setBindNum(actIdMembershipEntity.getBindNum() + 1);
            }
            actIdMembershipEntityDao.saveOrUpdate(actIdMembershipEntity);
        }
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("data", roleMappingEntity);
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public HashMap<String, Object> removeCapabilityRoleName(String capabilityId, String roleName) {
        HashMap<String, Object> result = new HashMap<>();
        //删除actiivti里面的membeship表
        ArrayList<String> usernames = (ArrayList<String>) orgDataService.retrieveAllHumanWithCapability(capabilityId).get("data");
        for(String username : usernames) {
            //删除或更新
            ActIdMembershipEntity actIdMembershipEntity = actIdMembershipEntityDao.findByGroupIdAndUserId(roleName, username);
            if(actIdMembershipEntity.getBindNum() == 1) {
                actIdMembershipEntityDao.deleteByGroupIdAndUserId(roleName, username);
            } else {
                actIdMembershipEntity.setBindNum(actIdMembershipEntity.getBindNum() - 1);
                actIdMembershipEntityDao.saveOrUpdate(actIdMembershipEntity);
            }
        }

        //删除roleMapping表
        RoleMappingEntity roleMappingEntity = roleMappingEntityDao.deleteByMappedIdAndMappedTypeAndBroleName(capabilityId, GlobalContext.ROLEMAPPING_MAPPEDTYPE_CAPABILITY, roleName);
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("data", roleMappingEntity);
        return result;
    }

    @Override
    public HashMap<String, Object> retrieveAllCapabilitiesWithRoleName(String roleName) {
        HashMap<String, Object> result = new HashMap<>();
        List<RoleMappingEntity> roleMappingEntities = roleMappingEntityDao.findByBroleNameAndMappedType(roleName, GlobalContext.ROLEMAPPING_MAPPEDTYPE_CAPABILITY);
        List<RenCapabilityEntity> renCapabilityEntities = new ArrayList<>();
        for(RoleMappingEntity roleMappingEntity : roleMappingEntities) {
            renCapabilityEntities.add(renCapabilityEntityDao.findById(roleMappingEntity.getMappedId()));
        }
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("data", renCapabilityEntities);
        return result;
    }

    @Override
    public HashMap<String, Object> retrievAllroleMapping() {
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
