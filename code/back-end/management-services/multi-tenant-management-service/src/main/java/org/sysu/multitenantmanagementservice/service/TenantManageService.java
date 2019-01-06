package org.sysu.multitenantmanagementservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.multitenantmanagementservice.Entity.TenantEntity;
import org.sysu.multitenantmanagementservice.Entity.TenantEntityRepository;

import java.util.List;


@Service
public class TenantManageService {
    private static Logger logger = LoggerFactory.getLogger(TenantManageService.class);

    @Autowired
    private TenantEntityRepository tenantEntityRepository;

    /**
     * @param tenantEntity: 需要包含的字段是name和password
     * @return
     */
    public TenantEntity createOne(TenantEntity tenantEntity) {
        //设置db数据，暂时全部默认值; 可以由客户端传入；由系统管理员设置

        tenantEntity.setId(null);
        tenantEntity.setStatus(1);
        return tenantEntityRepository.save(tenantEntity);
    }

    /**
     * 根据用户名和密码查找租户
     * @return
     */
    public TenantEntity findOneByNameAndPassword(String name, String password) {
        return tenantEntityRepository.findByNameAndPassword(name, password);
    }

    public TenantEntity findOneById(Long id) {
        return tenantEntityRepository.findById(id);
    }

    /**
     * 返回全部的租户信息
     * @return
     */
    public List<TenantEntity> findAllTenant() {
        return tenantEntityRepository.findAll();
    }

    /**
     * @param tid
     * @param tenantEntity，如果里面有值的话就更新
     * @return
     */
    public TenantEntity updateOne(Long tid, TenantEntity tenantEntity) {
        TenantEntity updateTenantEntity = tenantEntityRepository.findById(tid);
        if(null != updateTenantEntity) {
            replace(updateTenantEntity, tenantEntity);
            tenantEntityRepository.save(updateTenantEntity);
        }
        return null;
    }

    /**
     *  id和租户名不可修改
     * @param updateTenantEntity
     * @param tenantEntity
     */
    private void replace(TenantEntity updateTenantEntity, TenantEntity tenantEntity) {
        if(null != tenantEntity.getStatus()) {
            updateTenantEntity.setStatus(tenantEntity.getStatus());
        }
        if(null != tenantEntity.getPassword()) {
            updateTenantEntity.setPassword(tenantEntity.getPassword());
        }
        if(null != tenantEntity.getDburl()) {
            updateTenantEntity.setDburl(tenantEntity.getDburl());
        }
        if(null != tenantEntity.getDbusername()) {
            updateTenantEntity.setDbusername(tenantEntity.getDbusername());
        }
        if(null != tenantEntity.getDbscheam()) {
            updateTenantEntity.setDbscheam(tenantEntity.getDbscheam());
        }
    }

}
