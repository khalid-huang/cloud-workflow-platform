package org.sysu.bpmmanagementservice.constant;

public class GlobalContext {
   /** connect里面的类型，0-position, 1-capability */
   public static final int RENCONNECT_TYPE_POSITION = 1;
   public static final int RENCONNECT_TYPE_CAPABILITY = 2;

   /** rolemapping 里面的mapped类型,*/
   public static final int ROLEMAPPING_MAPPEDTYPE_POSITION = 1;
   public static final int ROLEMAPPING_MAPPEDTYPE_CAPABILITY = 2;

   /** 多租户数据库的配置*/
   public static final String DEFAULT_TENANT_ID = "default";
   public static final String DEFAULT_DATASOURCE_URL = "jdbc:mysql://222.200.180.59:3306/bpm_workflow_default?characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true";
   public static final String DEFAULT_DATASOURCE_USERNAME = "root";
   public static final String DEFAULT_DATASOURCE_PASSWORD = "workflow";
   public static final String DEFAULT_DATASOURCE_DRIVERCLASSNAME = "com.mysql.jdbc.Driver";


   //两种授权用户身份
   public static final String AUTH_ACCOUNT_ROLE = "role";
   public static final String AUTH_ACCOUNT_ROLE_NOMAL = "normal"; //普通员工
   public static final String AUTH_ACCOUNT_ROLE_MANAGER = "manager";

}
