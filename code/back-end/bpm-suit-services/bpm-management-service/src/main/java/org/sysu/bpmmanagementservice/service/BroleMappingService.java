package org.sysu.bpmmanagementservice.service;

import java.util.HashMap;

public interface BroleMappingService {
    /** businessRole */
    HashMap<String, Object> addBrole(String name);

    HashMap<String, Object> removeBroleByName(String name);

    HashMap<String, Object> retrieveBroleByName(String name);

    HashMap<String, Object> retrieveAllBrole();

    /** rolemapping */
    HashMap<String, Object> addPositionBroleNameOfProcDef(String positionId, String broleNameId, String procDefId);

    HashMap<String, Object> retrieveAllPositionsWithBroleNameOfProcDef(String broleName, String procDefId);

    HashMap<String, Object> addCapabilityBroleNameOfProcDef(String capabilityId, String broleName, String procDefId);

    HashMap<String, Object> retrieveAllCapabilitiesWithBroleNameOfProcDefId(String broleName, String procDefId);

    HashMap<String, Object> retrieveAllbroleMapping();

    HashMap<String, Object> retrieveAllbroleMappingOfProcDef(String procDefId);

    HashMap<String, Object> removeBroleMappingById(String id);

}
