package org.sysu.bpmmanagementservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sysu.bpmmanagementservice.entity.RenConnectEntity;

import java.util.List;

public interface RenConnectEntityRepository extends JpaRepository<RenConnectEntity,String> {
    List<RenConnectEntity> deleteAllByUsername(String userName);

    List<RenConnectEntity> findAllByUsernameAndType(String username, int type);

    RenConnectEntity deleteByUsernameAndBelongToOrganizabledIdAAndType(String username, String belongToOrganizabledId, int type);


}
