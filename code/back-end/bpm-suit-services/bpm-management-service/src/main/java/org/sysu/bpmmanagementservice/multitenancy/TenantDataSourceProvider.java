package org.sysu.bpmmanagementservice.multitenancy;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.sysu.bpmmanagementservice.constant.GlobalContext;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 这个类负责根据租户ID来提供对应的数据源
 * @author lanyuanxiaoyao
 * @version 1.0
 */
public class TenantDataSourceProvider {

    // 使用一个map来存储我们租户和对应的数据源，租户和数据源的信息就是从我们的tenant_info表中读出来
    private static Map<String, DataSource> dataSourceMap = new HashMap<>();

    /**
     * 静态建立一个数据源，也就是我们的默认数据源，假如我们的访问信息里面没有指定tenantId，就使用默认数据源。
     * 在我这里默认数据源是cloud_config，实际上你可以指向你们的公共信息的库，或者拦截这个操作返回错误。
     */
    static {
        //默认数据源
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(GlobalContext.DEFAULT_DATASOURCE_URL);
        dataSourceBuilder.username(GlobalContext.DEFAULT_DATASOURCE_USERNAME);
        dataSourceBuilder.password(GlobalContext.DEFAULT_DATASOURCE_PASSWORD);
        dataSourceBuilder.driverClassName(GlobalContext.DEFAULT_DATASOURCE_DRIVERCLASSNAME);
        dataSourceMap.put(GlobalContext.DEFAULT_TENANT_ID, dataSourceBuilder.build());

        //其他租户数据源配置
        TenantInfo t1 = new TenantInfo();
        t1.setTenantId("1");
        t1.setUrl("jdbc:mysql://222.200.180.59:3306/bpm_workflow_1?characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true");
        t1.setPassword("workflow");
        t1.setUsername("root");

        TenantInfo t2 = new TenantInfo();
        t2.setTenantId("2");
        t2.setUrl("jdbc:mysql://localhost:3306/bpm_workflow_2?characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true");
        t2.setPassword("root");
        t2.setUsername("root");

        TenantInfo t3 = new TenantInfo();
        t3.setTenantId("3");
        t3.setUrl("jdbc:mysql://222.200.180.59:3306/bpm_workflow_3?characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true");
        t3.setPassword("workflow");
        t3.setUsername("root");

        addDataSource(t1);
        addDataSource(t2);
        addDataSource(t3);
    }

    // 根据传进来的tenantId决定返回的数据源
    public static DataSource getTenantDataSource(String tenantId) {
        if (dataSourceMap.containsKey(tenantId)) {
            System.out.println("GetDataSource:" + tenantId);
            return dataSourceMap.get(tenantId);
        } else {
            System.out.println("GetDataSource:" + GlobalContext.DEFAULT_TENANT_ID);
            return dataSourceMap.get(GlobalContext.DEFAULT_TENANT_ID);
        }
    }

    // 初始化的时候用于添加数据源的方法
    public static void addDataSource(TenantInfo tenantInfo) {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(tenantInfo.getUrl());
        dataSourceBuilder.username(tenantInfo.getUsername());
        dataSourceBuilder.password(tenantInfo.getPassword());
        dataSourceBuilder.driverClassName("com.mysql.jdbc.Driver");
        dataSourceMap.put(tenantInfo.getTenantId(), dataSourceBuilder.build());
    }

}
