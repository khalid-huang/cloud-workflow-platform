package org.sysu.bpmmanagementservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sysu.bpmmanagementservice.entity.ActIdMembershipEntity;
import org.sysu.bpmmanagementservice.entity.multikeyclass.ActIdMembershipMKC;

public interface ActIdMembershipEntityRepository extends JpaRepository<ActIdMembershipEntity, ActIdMembershipMKC> {
}
