package org.sysu.multitenantmanagementservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.multitenantmanagementservice.Entity.RenTenantEntity;
import org.sysu.multitenantmanagementservice.dao.TenantDao;
import org.sysu.multitenantmanagementservice.repository.RenTenantEntityRepository;
import org.sysu.multitenantmanagementservice.vo.TenantVo;

import java.util.List;


@Service
public class TenantManageService {
    private static Logger logger = LoggerFactory.getLogger(TenantManageService.class);

    @Autowired
    private RenTenantEntityRepository renTenantEntityRepository;

    @Autowired
    private TenantDao tenantDao;

    /**
     * @param renTenantEntity: 需要包含的字段是name和password
     * @return
     */
    public RenTenantEntity createOne(RenTenantEntity renTenantEntity) {
        //设置db数据，暂时全部默认值; 可以由客户端传入；由系统管理员设置

        renTenantEntity.setId(null);
        renTenantEntity.setStatus(1);
        return renTenantEntityRepository.save(renTenantEntity);
    }

    /**
     * 根据用户名和密码查找租户
     * @return
     */
//    public RenTenantEntity findOneByNameAndPassword(String name, String password) {
//        return renTenantEntityRepository.findByNameAndPassword(name, password);
//    }

    public RenTenantEntity findOneById(Long id) {
        return renTenantEntityRepository.findById(id);
    }

    /**
     * 返回全部的租户信息
     * @return
     */
    public List<TenantVo> findAllTenant() {
        return tenantDao.retrieveAllTenant();
//        return renTenantEntityRepository.findAll();
    }

}
