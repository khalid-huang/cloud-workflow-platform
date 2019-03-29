package org.sysu.bpmprocessengineservice.dao;


import org.sysu.bpmprocessengineservice.entity.RenGroupEntity;

import java.util.List;

public interface RenGroupEntityDao {
    RenGroupEntity saveOrUpdate(RenGroupEntity renGroupEntity);

    void deleteById(String id);

    List<RenGroupEntity> findAll();

    RenGroupEntity findById(String id);
}
