package org.sysu.bpmprocessengineservice;

import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.sysu.bpmprocessengineservice.constant.ActivitiSQLConstantManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BpmProcessEngineServiceApplicationTests {

    @Autowired
    TaskService taskService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    ManagementService managementService;

    @Test
    public void deployModel() {
//        String r1 = "processes/1_model.bpmn20.xml";
//        String r2 = "processes/2_model.bpmn20.xml";
//        String r3 = "processes/3_model.bpmn20.xml";
        String r4 = "processes/5_model.bpmn20.xml";
//        repositoryService.createDeployment().addClasspathResource(r1).deploy();
//        repositoryService.createDeployment().addClasspathResource(r2).deploy();
//        repositoryService.createDeployment().addClasspathResource(r3).deploy();
        repositoryService.createDeployment().addClasspathResource(r4).deploy();
    }

    @Test
    public void contextLoads() {
//        ProcessInstance processInstance1 = runtimeService.startProcessInstanceByKey("online-shopping");
//        ProcessInstance processInstance2 = runtimeService.startProcessInstanceByKey("load-application");
//        ProcessInstance processInstance3 = runtimeService.startProcessInstanceByKey("leave-process");
//       ProcessInstance processInstance4 = runtimeService.startProcessInstanceByKey("a4-model");

//        有代理人/委托人或是没有代理但是有候选组/角色/人
        String worklistedSQL = "SELECT task.* FROM " + managementService.getTableName(TaskEntity.class)
                + " task LEFT JOIN " + managementService.getTableName(IdentityLinkEntity.class)
                + " identitylink ON task.ID_ = identitylink.TASK_ID_ WHERE"
                + " (task.ASSIGNEE_ IS NULL AND identitylink.TYPE_ = 'candidate')"
                + " OR task.ASSIGNEE_ IS NOT NULL";

        //没有代理人/委托人，也没有候选组/角色/人
        //https://www.cnblogs.com/xwdreamer/archive/2012/06/01/2530597.html; 使用连接解决
        String unofferedSQL = "SELECT task.* FROM " + managementService.getTableName(TaskEntity.class)
                + " task LEFT JOIN " + managementService.getTableName(IdentityLinkEntity.class)
                + " identitylink ON task.ID_ = identitylink.TASK_ID_ WHERE identitylink.TASK_ID_ is null AND task.ASSIGNEE_ is null";

        List<Task> worklisted = taskService.createNativeTaskQuery().sql(ActivitiSQLConstantManager.TASKSERVICE_TASKNATIVETASKQUERY_WORKLISTED).list();
        for (Task task: worklisted) {
            System.out.println(task);
        }
        System.out.println("-----------------------");
        List<Task> unoffered = taskService.createNativeTaskQuery().sql(ActivitiSQLConstantManager.TASKSERVICE_TASKNATIVETASKQUERY_UNOFFERED).list();
        for (Task task : unoffered) {
            System.out.println(task);
        }
    }

    @Test
    public void suspendContext() {
    }

}

