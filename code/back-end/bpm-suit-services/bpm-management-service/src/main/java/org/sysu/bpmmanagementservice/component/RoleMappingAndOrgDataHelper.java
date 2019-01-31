package org.sysu.bpmmanagementservice.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sysu.bpmmanagementservice.constant.GlobalContext;
import org.sysu.bpmmanagementservice.dao.ActIdMembershipEntityDao;
import org.sysu.bpmmanagementservice.dao.RenConnectEntityDao;
import org.sysu.bpmmanagementservice.dao.RoleMappingEntityDao;
import org.sysu.bpmmanagementservice.entity.ActIdMembershipEntity;
import org.sysu.bpmmanagementservice.entity.RenConnectEntity;
import org.sysu.bpmmanagementservice.entity.RoleMappingEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** 基于Activiti的业务关系映射和组织架构的一些辅助函数 */
@Component
public class RoleMappingAndOrgDataHelper {
    @Autowired
    RenConnectEntityDao renConnectEntityDao;

    @Autowired
    ActIdMembershipEntityDao actIdMembershipEntityDao;

    @Autowired
    RoleMappingEntityDao roleMappingEntityDao;

    /**获取指定职位的所有员工的用户名*/
    public List<String> retrieveAllHumanInPosition(String positionId) {
        List<String> usernames = new ArrayList<>();
        List<RenConnectEntity> renConnectEntities = renConnectEntityDao.findAllByBelongToOrganizabledIdAndType(positionId, GlobalContext.RENCONNECT_TYPE_POSITION);
        for(RenConnectEntity renConnectEntity : renConnectEntities) {
            usernames.add(renConnectEntity.getUsername());
        }
        return usernames;
    }

    /** 获取拥有相应能力的全部用户的用户名 */
    public List<String> retrieveAllHumanWithCapability(String capabilityId) {
        List<RenConnectEntity> renConnectEntities = renConnectEntityDao.findAllByBelongToOrganizabledIdAndType(capabilityId, GlobalContext.RENCONNECT_TYPE_POSITION);
        List<String> usernames = new ArrayList<>();
        for(RenConnectEntity renConnectEntity : renConnectEntities) {
            usernames.add(renConnectEntity.getUsername());
        }
        return usernames;
    }

    /** 移除由职位和业务角色名称指定的一条业务关系映射 */
    public RoleMappingEntity removeRoleMappingBetweenPositionAndBroleName(String positionId, String broleName) {
        //获取position里面相关的用户，删除其与broleName对应的group的membership（兼容activiti），再删除rolemapping
        List<String> usernames = retrieveAllHumanInPosition(positionId);
        for(String username : usernames) {
            updateForDecreaseActIdMembershipEntityByGroupIdAndUserId(broleName, username);
        }

        //删除roleMapping表
        RoleMappingEntity roleMappingEntity = roleMappingEntityDao.deleteByMappedIdAndMappedTypeAndBroleName(positionId, GlobalContext.ROLEMAPPING_MAPPEDTYPE_POSITION, broleName);
        return roleMappingEntity;
    }

    public RoleMappingEntity addRoleMappingBetweenPositionAndBroleName(String positionId, String broleName) {
        RoleMappingEntity roleMappingEntity = new RoleMappingEntity();
        roleMappingEntity.setBroleName(broleName);
        roleMappingEntity.setMappedId(positionId);
        roleMappingEntity.setMappedType(GlobalContext.ROLEMAPPING_MAPPEDTYPE_POSITION);
        roleMappingEntity =  roleMappingEntityDao.saveOrUpdate(roleMappingEntity);
        //更新activiti的用户与组表
        //查找有这个职位的所有用户
        List<String> usernames = retrieveAllHumanInPosition(positionId);
        //添加查找到的用户与业务角色的联系
        //添加的要注意bind_num，添加一次就加一
        for(String username : usernames) {
            updateForIncreaseActIdMembershipEntityByGroupIdAndUserId(broleName, username);
        }
        return roleMappingEntity;
    }
    /**
     * 移除这个Position所带来的所有的业务关系映射，包括activiti里面的membership
     * @param positionId
     */
    public List<RoleMappingEntity> removeRoleMappingsOfPosition(String positionId) {
        //获取相关的rolemapping
        List<RoleMappingEntity> roleMappingEntities = roleMappingEntityDao.findByMappedIdAndMappedType(positionId, GlobalContext.ROLEMAPPING_MAPPEDTYPE_POSITION);
        List<RoleMappingEntity> results = new ArrayList<>();
        for(RoleMappingEntity roleMappingEntity : roleMappingEntities) {
            results.add(removeRoleMappingBetweenPositionAndBroleName(positionId, roleMappingEntity.getBroleName()));
        }
        return results;
    }

    public RoleMappingEntity removeRoleMappingBetweenCapabilityAndBroleName(String capabilityId, String broleName) {
        //获取position里面相关的用户，删除其与broleName对应的group的membership（兼容activiti），再删除rolemapping
        List<String> usernames = retrieveAllHumanWithCapability(capabilityId);
        for(String username : usernames) {
            updateForDecreaseActIdMembershipEntityByGroupIdAndUserId(broleName, username);
        }

        //删除roleMapping表
        RoleMappingEntity roleMappingEntity = roleMappingEntityDao.deleteByMappedIdAndMappedTypeAndBroleName(capabilityId, GlobalContext.ROLEMAPPING_MAPPEDTYPE_CAPABILITY, broleName);
        return roleMappingEntity;
    }

    public RoleMappingEntity addRoleMappingBetweenCapabilityAndBroleName(String capabilityId, String broleName) {
        HashMap<String, Object> result = new HashMap<>();
        RoleMappingEntity roleMappingEntity = new RoleMappingEntity();
        roleMappingEntity.setBroleName(broleName);
        roleMappingEntity.setMappedId(capabilityId);
        roleMappingEntity.setMappedType(GlobalContext.ROLEMAPPING_MAPPEDTYPE_CAPABILITY);
        roleMappingEntity =  roleMappingEntityDao.saveOrUpdate(roleMappingEntity);
        //更新activiti的用户与组表
        //查找有这个能力的所有用户
        List<String> usernames = retrieveAllHumanWithCapability(capabilityId);
        for(String username : usernames) {
            updateForIncreaseActIdMembershipEntityByGroupIdAndUserId(broleName, username);
        }
        return roleMappingEntity;
    }

    public List<RoleMappingEntity> removeRoleMappingsOfCapability(String capabilityId) {
        //获取相关的rolemapping
        List<RoleMappingEntity> roleMappingEntities = roleMappingEntityDao.findByMappedIdAndMappedType(capabilityId, GlobalContext.ROLEMAPPING_MAPPEDTYPE_CAPABILITY);
        List<RoleMappingEntity> results = new ArrayList<>();
        for(RoleMappingEntity roleMappingEntity : roleMappingEntities) {
            results.add(removeRoleMappingBetweenCapabilityAndBroleName(capabilityId, roleMappingEntity.getBroleName()));
        }
        return results;
    }

    /** 处理将新的人加入到Postition之后的操作，主要是将人添加 到membership里面*/
    public void dealAfterAddHumanPosition(String username, String positionId) {
        List<RoleMappingEntity> roleMappingEntities = roleMappingEntityDao.findByMappedIdAndMappedType(positionId, GlobalContext.ROLEMAPPING_MAPPEDTYPE_POSITION);
        for(RoleMappingEntity roleMappingEntity : roleMappingEntities) {
            updateForIncreaseActIdMembershipEntityByGroupIdAndUserId(roleMappingEntity.getBroleName(), username);
        }
    }

    /** 处理将人从Postition删除之后的操作，主要是删除人到 到membership里面*/
    public void dealAfterRemoveHumanPosition(String username, String positionId) {
        List<RoleMappingEntity> roleMappingEntities = roleMappingEntityDao.findByMappedIdAndMappedType(positionId, GlobalContext.ROLEMAPPING_MAPPEDTYPE_POSITION);
        for(RoleMappingEntity roleMappingEntity : roleMappingEntities) {
            updateForDecreaseActIdMembershipEntityByGroupIdAndUserId(roleMappingEntity.getBroleName(), username);
        }
    }

    /** 处理将新的人获取到Capability之后的操作，主要是将人添加 到membership里面*/
    public void dealAfterAddHumanCapability(String username, String capabilityId) {
        List<RoleMappingEntity> roleMappingEntities = roleMappingEntityDao.findByMappedIdAndMappedType(capabilityId, GlobalContext.ROLEMAPPING_MAPPEDTYPE_CAPABILITY);
        for(RoleMappingEntity roleMappingEntity : roleMappingEntities) {
            updateForIncreaseActIdMembershipEntityByGroupIdAndUserId(roleMappingEntity.getBroleName(), username);
        }
    }

    /** 处理将人除去Capability删除之后的操作，主要是删除人到 到membership里面*/
    public void dealAfterRemoveHumanCapability(String username, String capabilityId) {
        List<RoleMappingEntity> roleMappingEntities = roleMappingEntityDao.findByMappedIdAndMappedType(capabilityId, GlobalContext.ROLEMAPPING_MAPPEDTYPE_CAPABILITY);
        for(RoleMappingEntity roleMappingEntity : roleMappingEntities) {
            updateForDecreaseActIdMembershipEntityByGroupIdAndUserId(roleMappingEntity.getBroleName(), username);
        }
    }


    /** groupId就是brolename, userid就是username*/
    /** 主要是更新bind_num，如果为1就直接删除，否则减1*/
    private void updateForDecreaseActIdMembershipEntityByGroupIdAndUserId(String groupId, String userId) {
        ActIdMembershipEntity actIdMembershipEntity = actIdMembershipEntityDao.findByGroupIdAndUserId(groupId, userId);
        //删除或更新
        if(actIdMembershipEntity.getBindNum() == 1) {
            actIdMembershipEntityDao.deleteByGroupIdAndUserId(groupId, userId);
        } else {
            actIdMembershipEntity.setBindNum(actIdMembershipEntity.getBindNum() - 1);
            actIdMembershipEntityDao.saveOrUpdate(actIdMembershipEntity);
        }
    }

    /** groupId就是brolename, userid就是username*/
    /** 主要是更新bind_num，如果记录不存在就直接创建并设置为1，否则加1*/
    private void updateForIncreaseActIdMembershipEntityByGroupIdAndUserId(String groupId, String userId) {
        ActIdMembershipEntity actIdMembershipEntity = actIdMembershipEntityDao.findByGroupIdAndUserId(groupId, userId);
        //新建或更新
        if(actIdMembershipEntity == null) {
            actIdMembershipEntity = new ActIdMembershipEntity();
            actIdMembershipEntity.setBindNum(1);
            actIdMembershipEntity.setGroupId(groupId);
            actIdMembershipEntity.setUserId(userId);
        } else {
            actIdMembershipEntity.setBindNum(actIdMembershipEntity.getBindNum() + 1);
        }
        actIdMembershipEntityDao.saveOrUpdate(actIdMembershipEntity);
    }

}
