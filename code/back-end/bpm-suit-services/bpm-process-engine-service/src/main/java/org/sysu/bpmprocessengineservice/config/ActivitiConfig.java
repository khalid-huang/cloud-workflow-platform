package org.sysu.bpmprocessengineservice.config;

import org.activiti.engine.*;
import org.activiti.engine.impl.bpmn.parser.factory.ActivityBehaviorFactory;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.parse.BpmnParseHandler;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.sysu.bpmprocessengineservice.activiti.ext.CloudActivitiBehaviorFactory;
import org.sysu.bpmprocessengineservice.activiti.ext.CloudUserTaskParseHandler;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

//要继承下面这个类才能起作用
@Configuration
public class ActivitiConfig extends AbstractProcessEngineAutoConfiguration {
    //https://www.jeejava.com/spring-boot-activiti-process-engine-configuration/ 设置独立数据库；可以用方法名决定bean的名字
    //当然也可以指定bean的名称其实
    @Bean
    public ProcessEngineConfiguration processEngineConfiguration(DataSource dataSource,
                                                                 PlatformTransactionManager platformTransactionManager,
                                                                 CloudActivitiBehaviorFactory cloudActivitiBehaviorFactory,
                                                                 CloudUserTaskParseHandler cloudUserTaskParseHandler) {
        SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();

        processEngineConfiguration.setDataSource(dataSource);
        processEngineConfiguration.setDatabaseSchemaUpdate("true");
        processEngineConfiguration.setTransactionManager(platformTransactionManager);
        processEngineConfiguration.setActivityBehaviorFactory(cloudActivitiBehaviorFactory);
        List<BpmnParseHandler> bpmnParseHandlers = new ArrayList<>();
        bpmnParseHandlers.add(cloudUserTaskParseHandler);
        processEngineConfiguration.setCustomDefaultBpmnParseHandlers(bpmnParseHandlers);
        System.out.println("cloudActivitiBehaviorFactory: " + cloudActivitiBehaviorFactory);
        System.out.println("processConfiguration:" + processEngineConfiguration);
        return processEngineConfiguration;
    }
    //方法名决定了bean的名称，这里只可以使用processEngine，否则下面会匹配不到正确的processEngine
    //或者给Bean注入一个bean name
    @Bean(name = "processEngine")
    public ProcessEngineFactoryBean processEngineFactoryBean(ProcessEngineConfiguration processEngineConfiguration) {
        ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
        processEngineFactoryBean.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);
        return processEngineFactoryBean;
    }

    @Bean
    public RepositoryService repositoryService(ProcessEngine processEngine) {
        return processEngine.getRepositoryService();
    }

    @Bean
    public RuntimeService runtimeService(ProcessEngine processEngine)
    {
        return processEngine.getRuntimeService();

    }
    @Bean
    public TaskService taskService(ProcessEngine processEngine) {
        return processEngine.getTaskService();
    }

    @Bean
    public HistoryService historyService(ProcessEngine processEngine) {
                                                                    return processEngine.getHistoryService();
                                                                                                             }

    @Bean
    public FormService formService(ProcessEngine processEngine) {
                                                              return processEngine.getFormService();
                                                                                                    }

    @Bean
    public IdentityService identityService(ProcessEngine processEngine) {
        return processEngine.getIdentityService();
    }

    @Bean
    public ManagementService managementService(ProcessEngine processEngine) {
        return processEngine.getManagementService();
    }

    @Bean
    public DynamicBpmnService dynamicBpmnService(ProcessEngine processEngine) {
        return processEngine.getDynamicBpmnService();
    }
}
