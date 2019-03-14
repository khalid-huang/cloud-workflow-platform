package org.sysu.bpmmanagementservice.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sysu.bpmmanagementservice.constant.GlobalContext;
import org.sysu.bpmmanagementservice.dao.ActIdMembershipEntityDao;
import org.sysu.bpmmanagementservice.dao.RenConnectEntityDao;
import org.sysu.bpmmanagementservice.dao.RoleMappingEntityDao;
import org.sysu.bpmmanagementservice.entity.ActIdMembershipEntity;
import org.sysu.bpmmanagementservice.entity.RenConnectEntity;
import org.sysu.bpmmanagementservice.entity.BroleMappingEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/** 基于Activiti的业务关系映射和组织架构的一些辅助函数 */
@Component
public class BroleMappingAndOrgDataHelper {
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
        List<RenConnectEntity> renConnectEntities = renConnectEntityDao.findAllByBelongToOrganizabledIdAndType(capabilityId, GlobalContext.RENCONNECT_TYPE_CAPABILITY);
        List<String> usernames = new ArrayList<>();
        for(RenConnectEntity renConnectEntity : renConnectEntities) {
            usernames.add(renConnectEntity.getUsername());
        }
        return usernames;
    }

    /** 移除由职位和业务角色名称指定的一条业务关系映射 */
    public void removeRoleMappingBetweenPositionAndBroleNameId(String positionId, String broleNameId) {
        //获取position里面相关的用户，删除其与broleName对应的group的membership（兼容activiti），再删除rolemapping
        List<String> usernames = retrieveAllHumanInPosition(positionId);
        for(String username : usernames) {
            updateForDecreaseActIdMembershipEntityByGroupIdAndUserId(broleNameId, username);
        }

        //删除roleMapping表
        roleMappingEntityDao.deleteByMappedIdAndMappedTypeAndBroleNameId(positionId, GlobalContext.ROLEMAPPING_MAPPEDTYPE_POSITION, broleNameId);
    }

    public BroleMappingEntity addRoleMappingBetweenPositionAndBroleName(String positionId, String broleNameId) {
        BroleMappingEntity broleMappingEntity = new BroleMappingEntity();
        broleMappingEntity.setId(UUID.randomUUID().toString());
        broleMappingEntity.setBroleNameId(broleNameId);
        broleMappingEntity.setMappedId(positionId);
        broleMappingEntity.setMappedType(GlobalContext.ROLEMAPPING_MAPPEDTYPE_POSITION);
        broleMappingEntity =  roleMappingEntityDao.saveOrUpdate(broleMappingEntity);
        //更新activiti的用户与组表
        //查找有这个职位的所有用户
        List<String> usernames = retrieveAllHumanInPosition(positionId);
        //添加查找到的用户与业务角色的联系
        //添加的要注意bind_num，添加一次就加一
        for(String username : usernames) {
            updateForIncreaseActIdMembershipEntityByGroupIdAndUserId(broleNameId, username);
        }
        return broleMappingEntity;
    }
    /**
     * 移除这个Position所带来的所有的业务关系映射，包括activiti里面的membership
     * @param positionId
     */
    public void removeRoleMappingsOfPosition(String positionId) {
        //获取相关的rolemapping
        List<BroleMappingEntity> roleMappingEntities = roleMappingEntityDao.findByMappedIdAndMappedType(positionId, GlobalContext.ROLEMAPPING_MAPPEDTYPE_POSITION);
        List<BroleMappingEntity> results = new ArrayList<>();
        for(BroleMappingEntity broleMappingEntity : roleMappingEntities) {
            removeRoleMappingBetweenPositionAndBroleNameId(positionId, broleMappingEntity.getBroleNameId());
        }
    }

    public void removeRoleMappingBetweenCapabilityAndBroleName(String capabilityId, String broleNameId) {
        //获取position里面相关的用户，删除其与broleName对应的group的membership（兼容activiti），再删除rolemapping
        List<String> usernames = retrieveAllHumanWithCapability(capabilityId);
        for(String username : usernames) {
            updateForDecreaseActIdMembershipEntityByGroupIdAndUserId(broleNameId, username);
        }

        //删除roleMapping表
        roleMappingEntityDao.deleteByMappedIdAndMappedTypeAndBroleNameId(capabilityId, GlobalContext.ROLEMAPPING_MAPPEDTYPE_CAPABILITY, broleNameId);
    }

    public BroleMappingEntity addRoleMappingBetweenCapabilityAndBroleName(String capabilityId, String broleNameId) {
        HashMap<String, Object> result = new HashMap<>();
        BroleMappingEntity broleMappingEntity = new BroleMappingEntity();
        broleMappingEntity.setId(UUID.randomUUID().toString());
        broleMappingEntity.setBroleNameId(broleNameId);
        broleMappingEntity.setMappedId(capabilityId);
        broleMappingEntity.setMappedType(GlobalContext.ROLEMAPPING_MAPPEDTYPE_CAPABILITY);
        broleMappingEntity =  roleMappingEntityDao.saveOrUpdate(broleMappingEntity);
        //更新activiti的用户与组表
        //查找有这个能力的所有用户
        List<String> usernames = retrieveAllHumanWithCapability(capabilityId);
        for(String username : usernames) {
            updateForIncreaseActIdMembershipEntityByGroupIdAndUserId(broleNameId, username);
        }
        return broleMappingEntity;
    }

    public void removeRoleMappingsOfCapability(String capabilityId) {
        //获取相关的rolemapping
        List<BroleMappingEntity> roleMappingEntities = roleMappingEntityDao.findByMappedIdAndMappedType(capabilityId, GlobalContext.ROLEMAPPING_MAPPEDTYPE_CAPABILITY);
        for(BroleMappingEntity broleMappingEntity : roleMappingEntities) {
            removeRoleMappingBetweenCapabilityAndBroleName(capabilityId, broleMappingEntity.getBroleNameId());
        }
    }

    /** 处理将新的人加入到Postition之后的操作，主要是将人添加 到membership里面*/
    public void dealAfterAddHumanPosition(String username, String positionId) {
        List<BroleMappingEntity> roleMappingEntities = roleMappingEntityDao.findByMappedIdAndMappedType(positionId, GlobalContext.ROLEMAPPING_MAPPEDTYPE_POSITION);
        for(BroleMappingEntity broleMappingEntity : roleMappingEntities) {
            updateForIncreaseActIdMembershipEntityByGroupIdAndUserId(broleMappingEntity.getBroleNameId(), username);
        }
    }

    /** 处理将人从Postition删除之后的操作，主要是删除人到 到membership里面*/
    public void dealAfterRemoveHumanPosition(String username, String positionId) {
        List<BroleMappingEntity> roleMappingEntities = roleMappingEntityDao.findByMappedIdAndMappedType(positionId, GlobalContext.ROLEMAPPING_MAPPEDTYPE_POSITION);
        for(BroleMappingEntity broleMappingEntity : roleMappingEntities) {
            updateForDecreaseActIdMembershipEntityByGroupIdAndUserId(broleMappingEntity.getBroleNameId(), username);
        }
    }

    /** 处理将新的人获取到Capability之后的操作，主要是将人添加 到membership里面*/
    public void dealAfterAddHumanCapability(String username, String capabilityId) {
        List<BroleMappingEntity> roleMappingEntities = roleMappingEntityDao.findByMappedIdAndMappedType(capabilityId, GlobalContext.ROLEMAPPING_MAPPEDTYPE_CAPABILITY);
        for(BroleMappingEntity broleMappingEntity : roleMappingEntities) {
            updateForIncreaseActIdMembershipEntityByGroupIdAndUserId(broleMappingEntity.getBroleNameId(), username);
        }
    }

    /** 处理将人除去Capability删除之后的操作，主要是删除人到 到membership里面*/
    public void dealAfterRemoveHumanCapability(String username, String capabilityId) {
        List<BroleMappingEntity> roleMappingEntities = roleMappingEntityDao.findByMappedIdAndMappedType(capabilityId, GlobalContext.ROLEMAPPING_MAPPEDTYPE_CAPABILITY);
        for(BroleMappingEntity broleMappingEntity : roleMappingEntities) {
            updateForDecreaseActIdMembershipEntityByGroupIdAndUserId(broleMappingEntity.getBroleNameId(), username);
        }
    }


    /** groupId就是brolenameId userid就是username*/
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
