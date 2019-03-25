package org.sysu.bpmprocessengineservice.constant;

public class GlobalContext {
   /** connect里面的类型，0-position, 1-capability */
   public static final int RENCONNECT_TYPE_POSITION = 1;
   public static final int RENCONNECT_TYPE_CAPABILITY = 2;

   /** rolemapping 里面的mapped类型,*/
   public static final int ROLEMAPPING_MAPPEDTYPE_POSITION = 1;
   public static final int ROLEMAPPING_MAPPEDTYPE_CAPABILITY = 2;

   /** 多租户数据库的配置*/
   public static final String DEFAULT_TENANT_ID = "bpm_workflow_default_0";

}
