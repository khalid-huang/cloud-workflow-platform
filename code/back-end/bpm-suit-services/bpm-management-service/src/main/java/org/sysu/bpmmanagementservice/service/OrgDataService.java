package org.sysu.bpmmanagementservice.service;

import java.util.HashMap;

/**
 * 组织架构管理服务
 */
public interface OrgDataService {
    /** 人类 */
    HashMap<String, Object> addHuman(String firstName, String lastName, String email, String password);

    HashMap<String, Object> removeHuman(String id);

    HashMap<String, Object> updateHuman(String id, HashMap<String, String> pairs);

    HashMap<String, Object> retrieveHumanById(String id);

    HashMap<String, Object> retrieveHumanByUsername(String username);

    HashMap<String, Object> findAllHuman();

    /** group */
    HashMap<String, Object> addGroup(String name, String description, String note, String belongToId, int groupType);

    HashMap<String, Object> removeGroupByName(String name);

    HashMap<String, Object> updateGroup(String name, HashMap<String, String> pairs);

    HashMap<String, Object> retrieveGroup(String id);

    HashMap<String, Object> retrieveAllGroup();

    /** 职位 */
    HashMap<String, Object> addPosition(String name, String description, String note, String belongToId, String reportToId);

    HashMap<String, Object> removePositionByName(String name);

    HashMap<String, Object> updatePosition(String name, HashMap<String, String> pairs);

    HashMap<String, Object> retrievePositionById(String id);

    HashMap<String, Object> retrieveAllPosition();

    /** capability */
    HashMap<String, Object> addCapability(String name, String description, String note);

    HashMap<String, Object> removeCapabilityByName(String name);

    HashMap<String, Object> updateCapability(String name, HashMap<String, String> pairs);

    HashMap<String, Object> retrieveCapabilityById(String id);

    HashMap<String, Object> retrieveAllCapability();

    /** connection */
    //移除用户所有的连接
    HashMap<String, Object> removeHumanConnection(String username);

    HashMap<String, Object> retrieveHumanInWhatPosition(String username);

    HashMap<String, Object> retrieveHumanWithWhatCapability(String username);

    HashMap<String, Object> addHumanPosition(String username, String positionId);

    HashMap<String, Object> removeHumanPosition(String username, String positionId);

    HashMap<String, Object> addHumanCapability(String username, String capabilityId);

    HashMap<String, Object> removeHumanCapability(String username, String capabilityId);

    HashMap<String, Object> retriveAllConnection();
}
