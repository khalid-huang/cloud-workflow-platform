package org.sysu.bpmmanagementservice.service;

import java.util.HashMap;

public interface RoleMappingService {
    /** businessRole */
    HashMap<String, Object> addBusinessRole(String name);

    HashMap<String, Object> removeBusinessRoleByName(String name);

    HashMap<String, Object> retrieveBusinessRoleByName(String name);

    HashMap<String, Object> retrieveAllBusinessRole();

    /** rolemapping */
    HashMap<String, Object> addPositionBroleName(String positionId, String broleName);

    HashMap<String, Object> removePositionBroleName(String positionId, String broleName);

    /** 获取所有有相关业务角色的职位*/
    HashMap<String, Object> retrieveAllPositionsWithBroleName(String broleName);

    HashMap<String, Object> addCapabilityBroleName(String capabilityId, String broleName);

    HashMap<String, Object> removeCapabilityBroleName(String capabilityId, String broleName);

    HashMap<String, Object> retrieveAllCapabilitiesWithBroleName(String broleName);

    HashMap<String, Object> retrievAllroleMapping();

}
