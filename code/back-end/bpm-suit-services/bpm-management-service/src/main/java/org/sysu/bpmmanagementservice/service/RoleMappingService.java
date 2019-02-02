package org.sysu.bpmmanagementservice.service;

import java.util.HashMap;

public interface RoleMappingService {
    /** businessRole */
    HashMap<String, Object> addBusinessRole(String name);

    HashMap<String, Object> removeBusinessRoleById(String id);

    HashMap<String, Object> retrieveBusinessRoleById(String id);

    HashMap<String, Object> retrieveAllBusinessRole();

    /** rolemapping */
    HashMap<String, Object> addPositionBroleName(String positionId, String broleNameId);

    HashMap<String, Object> removePositionBroleName(String positionId, String broleNameId);

    /** 获取所有有相关业务角色的职位*/
    HashMap<String, Object> retrieveAllPositionsWithBroleName(String broleNameId);

    HashMap<String, Object> addCapabilityBroleName(String capabilityId, String broleNameId);

    HashMap<String, Object> removeCapabilityBroleName(String capabilityId, String broleNameId);

    HashMap<String, Object> retrieveAllCapabilitiesWithBroleName(String broleNameId);

    HashMap<String, Object> retrievAllRoleMapping();

}
