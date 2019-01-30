package org.sysu.bpmmanagementservice.service;

import java.util.HashMap;

public interface RoleMappingService {
    /** businessRole */
    HashMap<String, Object> addBusinessRole(String name);

    HashMap<String, Object> removeBusinessRoleByName(String name);

    HashMap<String, Object> retrieveBusinessRoleByName(String name);

    HashMap<String, Object> retrieveAllBusinessRole();

    /** rolemapping */
    HashMap<String, Object> addPositionRoleName(String positionId, String roleName);

    HashMap<String, Object> removePositionRoleName(String positionId, String roleName);

    /** 获取所有有相关业务角色的职位*/
    HashMap<String, Object> retrieveAllPositionsWithRoleName(String roleName);

    HashMap<String, Object> addCapabilityRoleName(String capabilityId, String roleName);

    HashMap<String, Object> removeCapabilityRoleName(String capabilityId, String roleName);

    HashMap<String, Object> retrieveAllCapabilitiesWithRoleName(String roleName);

    HashMap<String, Object> retrievAllroleMapping();

}
