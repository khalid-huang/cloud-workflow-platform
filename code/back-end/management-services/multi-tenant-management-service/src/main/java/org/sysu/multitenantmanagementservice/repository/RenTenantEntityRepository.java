package org.sysu.multitenantmanagementservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sysu.multitenantmanagementservice.Entity.RenTenantEntity;

import java.util.List;

//转DTO可以参考：https://blog.csdn.net/liuchuanhong1/article/details/61198896
public interface RenTenantEntityRepository extends JpaRepository<RenTenantEntity, Long> {
    RenTenantEntity findById(Long id);
}
