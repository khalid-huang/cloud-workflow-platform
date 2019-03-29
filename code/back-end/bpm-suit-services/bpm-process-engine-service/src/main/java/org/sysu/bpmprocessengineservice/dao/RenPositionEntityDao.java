package org.sysu.bpmprocessengineservice.dao;


import org.sysu.bpmprocessengineservice.entity.RenPositionEntity;

import java.util.List;

public interface RenPositionEntityDao {
    RenPositionEntity saveOrUpdate(RenPositionEntity renPositionEntity);

    void deleteById(String id);

    List<RenPositionEntity> findAll();

    RenPositionEntity findById(String id);
}
