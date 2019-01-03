package org.sysu.multitenantmanagementservice.Entity;

import org.springframework.data.jpa.repository.JpaRepository;

//转DTO可以参考：https://blog.csdn.net/liuchuanhong1/article/details/61198896
public interface TenantEntityRepository extends JpaRepository<TenantEntity, Long> {
    TenantEntity findById(Long id);
}
