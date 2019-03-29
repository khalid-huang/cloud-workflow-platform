package org.sysu.bpmprocessengineservice.dao;


import org.sysu.bpmprocessengineservice.entity.BroleMappingEntity;

import java.util.List;

public interface BroleMappingEntityDao {
    List<BroleMappingEntity> findByBroleNameAndProcDefId(String broleName, String procDefId);
}
