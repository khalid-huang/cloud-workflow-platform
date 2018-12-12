package org.sysu.activitiservice;


import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.lucene.util.RamUsageEstimator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiServiceApplicationTests {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RepositoryService repositoryService;

    @Test
    public void contextLoads() {
        long count = repositoryService.createProcessDefinitionQuery().count();
        System.out.println(count);
        System.out.println("test");
    }

    @Test
    public void contextDeploy() {
        long count = repositoryService.createProcessDefinitionQuery().count();
        System.out.println(count);

        String r1 = "processes/leave.bpmn20.xml";
        String r2 = "processes/travel-booking-process.bpmn20.xml";

        long startTime1 = System.currentTimeMillis();
        repositoryService.createDeployment().addClasspathResource(r2).deploy();
        long endTime1 = System.currentTimeMillis();
        System.out.println("first: "  + (endTime1 - startTime1));

        long startTime2 = System.currentTimeMillis();
        repositoryService.createDeployment().addClasspathResource(r1).deploy();
        long endTime2 = System.currentTimeMillis();
        System.out.println("second: " + (endTime2 - startTime2));

        long startTime3 = System.currentTimeMillis();
        repositoryService.createDeployment().addClasspathResource(r2).deploy();
        long endTime3 = System.currentTimeMillis();
        System.out.println("third:" + (endTime3 - startTime3));

        long startTime4 = System.currentTimeMillis();
        repositoryService.createDeployment().addClasspathResource(r1).deploy();
        long endTime4 = System.currentTimeMillis();
        System.out.println("fourth: " + (endTime4 - startTime4));
    }


    @Test
    public void contextStart() {
//        long count = repositoryService.createProcessDefinitionQuery().count();
//        System.out.println(count);
        //启动流程leave
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("apply", "zhangsan");
        variables.put("approve", "lisi");
        long startTime, endTime;
        startTime = System.currentTimeMillis();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("leave", variables);
        endTime = System.currentTimeMillis();
        System.out.println("start: " + (endTime - startTime));

        //完成第一步申请
        String processId = processInstance.getId();
        Task task1 = taskService.createTaskQuery().processInstanceId(processId).singleResult();
//        taskService.claim(task1.getId(), "mike");
        startTime = System.currentTimeMillis();
        taskService.complete(task1.getId(), variables);
        endTime = System.currentTimeMillis();
        System.out.println("complete: " + (endTime - startTime));


        //完成第二步请求
        Task task2 = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        variables.put("pass", true);
        startTime = System.currentTimeMillis();
        taskService.complete(task2.getId(), variables);
        endTime = System.currentTimeMillis();
        System.out.print("complete: " + (endTime - startTime));

        System.out.println("完成数：" + historyService.createHistoricProcessInstanceQuery().finished().count());

//        ProcessDefinitionEntity pde = (ProcessDefinitionEntity) repositoryService.getProcessDefinition("online-shopping:1:30");
//        Map<String, ActivityImpl> nameActivities = pde.getNamedActivities();
//        System.out.println(nameActivities);
    }

    /**
     * 测试单个任务的消耗时长是否一致
     */
    @Test
    public void testSingleTaskCost() {
        //启动流程leave
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("apply", "zhangsan");
        variables.put("approve", "lisi");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("leave", variables);
        ExecutionEntity executionEntity = (ExecutionEntity) processInstance;
        System.out.println(executionEntity.getActivity());


        //完成第一步申请
        String processId = processInstance.getId();
        System.out.println("processId: " + processId);
        Task task1 = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        System.out.println("task1_Id: " + task1.getId());
        System.out.println("task1_Name: " + task1.getName());
        taskService.complete(task1.getId(), variables);

        //完成第二步请求
        Task task2 = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        variables.put("pass", true);
        taskService.complete(task2.getId(), variables);
        System.out.println("完成数：" + historyService.createHistoricProcessInstanceQuery().finished().count());
    }

    @Test
    public void testLeave() {
        //验证是否有加载到processes下面的流程文件
        System.out.println("ok>>");
        long count = repositoryService.createProcessDefinitionQuery().count();
        System.out.println(count);

        //启动流程leave
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("apply", "zhangsan");
        variables.put("approve", "lisi");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("leave", variables);
        ProcessInstance processInstance1 = runtimeService.startProcessInstanceByKey("leave", variables);
        ExecutionEntity executionEntity = (ExecutionEntity) processInstance;
        ExecutionEntity executionEntity1 = (ExecutionEntity) processInstance1;
        System.out.println(executionEntity.getActivity());
        System.out.println(executionEntity1.getActivity());

        if(executionEntity.getActivity() == executionEntity1.getActivity()) {
            System.out.println("equal");
        } else {
            System.out.println("no equal");
        }


        //完成第一步申请
        String processId = processInstance.getId();
        System.out.println("processId: " + processId);
        Task task1 = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        System.out.println("task1_Id: " + task1.getId());
        System.out.println("task1_Name: " + task1.getName());
        taskService.complete(task1.getId(), variables);

        //完成第二步请求
        Task task2 = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        variables.put("pass", true);
        taskService.complete(task2.getId(), variables);


        System.out.println("完成数：" + historyService.createHistoricProcessInstanceQuery().finished().count());
    }

    FileWriter writerForClaimTask = null;
    FileWriter writerForCompleteTask = null;
    FileWriter writerForStartTask = null;
    FileWriter writerForIfFile = null;
    FileWriter writerForSequenceFile = null;
    FileWriter writeForTaskNoNeedToPush = null; //记录不需要推动流程执行的任务，主要是并行网关中的
    FileWriter writerForParallelSixFile  = null; //记录一个会推动一个并行网关的请求的时间
    FileWriter writerForLoopFile = null;
    FileWriter writerForParallelJoinFile= null;
    FileWriter writerForGetCurrentTask = null;


    @Test
    public void testTaskType() {
        String ifFile = "E:\\workspace\\temp\\complete\\if.txt";
        String paralleSixFile = "E:\\workspace\\temp\\complete\\paralleSixFile.txt";
        String sequenceFile = "E:\\workspace\\temp\\complete\\sequenceFile.txt";
        String loopFile = "E:\\workspace\\temp\\complete\\loop.txt";
        String taskNoNeedToPush = "E:\\workspace\\temp\\complete\\taskNoNeedToPush.txt";
        String taskNeedToPush = "E:\\workspace\\temp\\complete\\taskNeedToPush.txt";
        String getCurrentTask = "E:\\workspace\\temp\\complete\\getCurrentTask.txt";
        try {
            writerForIfFile = new FileWriter(ifFile);
            writerForSequenceFile = new FileWriter(sequenceFile);
            writerForParallelSixFile = new FileWriter(paralleSixFile);
            writeForTaskNoNeedToPush = new FileWriter(taskNoNeedToPush);
            writerForLoopFile = new FileWriter(loopFile);
            writerForParallelJoinFile = new FileWriter(taskNeedToPush);
            writerForGetCurrentTask = new FileWriter(getCurrentTask);
            int testTime = 60;

            ProcessInstance temp = runtimeService.startProcessInstanceByKey("load-application");

            for(int i = 0; i < testTime; i++) {
                _testTaskType();
            }

            ProcessInstance tempp = runtimeService.startProcessInstanceByKey("load-application");
            writerForLoopFile.close();
            writeForTaskNoNeedToPush.close();
            writerForParallelSixFile.close();
            writerForIfFile.close();
            writerForSequenceFile.close();
            writerForParallelJoinFile.close();
            writerForGetCurrentTask.close();
        } catch (IOException e) {

        }
    }

    @Test
    public void _testTaskType() throws IOException {
        long startTime, endTime;

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("x", 3);
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("a6-model-test-1", variables);

        Task task1 = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        startTime = System.currentTimeMillis();
        taskService.complete(task1.getId(), variables);
        endTime = System.currentTimeMillis();
        writerForIfFile.write("" + (endTime - startTime) + "\r\n");

        startTime = System.currentTimeMillis();
        Task task2 = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        endTime = System.currentTimeMillis();
        writerForGetCurrentTask.write("" + (endTime - startTime) + "\r\n");

//        System.out.println(task2.getName());
        startTime = System.currentTimeMillis();
        taskService.complete(task2.getId());
        endTime = System.currentTimeMillis();
        writerForParallelSixFile.write("" + (endTime -startTime) + "\r\n");

//        List<Task> tasks = taskService.createTaskQuery().processInstanceId(pi.getId()).list();
        List<Task> tasks = taskService.createTaskQuery().taskAssignee("mike").list();
        Task task5 = taskService.createTaskQuery().taskName("5").singleResult();
//        System.out.println("task5: " + task5.getName());
//        System.out.println("分支数：" + tasks.size());

        int size = tasks.size();
        System.out.println("size: " + size);
        for(int i = 0; i < size; i++) {
            startTime = System.currentTimeMillis();
            taskService.complete(tasks.get(i).getId());
            endTime = System.currentTimeMillis();
            if(i == 2) {
                writeForTaskNoNeedToPush.write("" + (endTime - startTime) + "\r\n");
            }
            if(i == size - 1) {
                writerForParallelJoinFile.write("" + (endTime - startTime) + "\r\n");
            }
        }

//        System.out.println("并行之后: " + taskService.createTaskQuery().processInstanceId(pi.getId()).list().size());

        taskService.complete(taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult().getId());

        startTime = System.currentTimeMillis();
        Task task9 = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        endTime = System.currentTimeMillis();
        writerForGetCurrentTask.write("" + (endTime - startTime) + "\r\n");

        startTime = System.currentTimeMillis();
        taskService.complete(task9.getId());
        endTime = System.currentTimeMillis();
        writerForSequenceFile.write("" + (endTime - startTime) + "\r\n");


        taskService.complete(taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult().getId());

        System.out.println(historyService.createHistoricProcessInstanceQuery().finished().count());

    }

    @Test
    public void testCostTime() {
        String startFile = "E:\\workspace\\temp\\start.txt";
        String completeFile = "E:\\workspace\\temp\\complete.txt";
        String claimFile = "E:\\workspace\\temp\\claim.txt";
        try {
            writerForClaimTask = new FileWriter(claimFile);
            writerForCompleteTask = new FileWriter(completeFile);
            writerForStartTask = new FileWriter(startFile);

            ProcessInstance temp = runtimeService.startProcessInstanceByKey("load-application");

            int testTime = 40;
            for(int i = 0; i < testTime; i++) {
                _testCostTime();
//                _testCostTimeWithModel1();
            }

            ProcessInstance tempp = runtimeService.startProcessInstanceByKey("load-application");

            writerForStartTask.close();
            writerForCompleteTask.close();
            writerForClaimTask.close();

        } catch (IOException e) {

        }

    }


    public void _testCostTimeWithModel1() throws IOException {
        long startTime, endTime;
        String assignee = "mike";

        startTime = System.currentTimeMillis();
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("online-shopping");
        endTime = System.currentTimeMillis();
        writerForStartTask.write("" + (endTime - startTime) + "\r\n");

        Task task1 = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();

        startTime = System.currentTimeMillis();
        taskService.claim(task1.getId(), assignee);
        endTime = System.currentTimeMillis();
        writerForClaimTask.write("" + (endTime - startTime) + "\r\n");

        startTime = System.currentTimeMillis();
        taskService.complete(task1.getId());
        endTime = System.currentTimeMillis();
        writerForCompleteTask.write("" + (endTime - startTime) + "\r\n");

        Task task2 = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();

        startTime = System.currentTimeMillis();
        taskService.claim(task2.getId(), assignee);
        endTime = System.currentTimeMillis();
        writerForClaimTask.write("" + (endTime - startTime) + "\r\n");

        startTime = System.currentTimeMillis();
        taskService.complete(task2.getId());
        endTime = System.currentTimeMillis();
        writerForCompleteTask.write("" + (endTime - startTime) + "\r\n");

        Task task3 = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();

        startTime = System.currentTimeMillis();
        taskService.claim(task3.getId(), assignee);
        endTime = System.currentTimeMillis();
        writerForClaimTask.write("" + (endTime - startTime) + "\r\n");

        startTime = System.currentTimeMillis();
        taskService.complete(task3.getId());
        endTime = System.currentTimeMillis();
        writerForCompleteTask.write("" + (endTime - startTime) + "\r\n");

        Task task4 = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();

        startTime = System.currentTimeMillis();
        taskService.claim(task4.getId(), assignee);
        endTime = System.currentTimeMillis();
        writerForClaimTask.write("" + (endTime - startTime) + "\r\n");

        startTime = System.currentTimeMillis();
        taskService.complete(task4.getId());
        endTime = System.currentTimeMillis();
        writerForCompleteTask.write("" + (endTime - startTime) + "\r\n");


        System.out.println(historyService.createHistoricProcessInstanceQuery().finished().count());

    }

    public void _testCostTime() throws IOException {

        //参数设定
        String traveler = "Mike";
        String hotel = "1";
        String flight = "0";
        String car = "0";

        long startTime, endTime;

        //启动流程:
        Map<String, Object> variables = new HashMap<String, Object>();
        Map<String, Object> subVariables = new HashMap<String, Object>();

        startTime = System.currentTimeMillis();
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("travel-booking", variables);
        endTime = System.currentTimeMillis();
        writerForStartTask.write("" + (endTime - startTime) + "\r\n");
//        System.out.println("start: " + (endTime - startTime));

        //完成第一步：register
        Task registerTask = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        // -- traveler认领任务
        startTime = System.currentTimeMillis();
        taskService.claim(registerTask.getId(), traveler);
        endTime = System.currentTimeMillis();
//        System.out.println("claim: " + (endTime - startTime));
        writerForClaimTask.write("" + (endTime - startTime) + "\r\n");

        List<Task> tasks = taskService.createTaskQuery().taskAssignee(traveler).list();
        // -- traveler完成任务
        for(Task task : tasks) {
//            System.out.println("Task for " + task.getAssignee() + ": " + task.getName());
            startTime = System.currentTimeMillis();
            taskService.complete(task.getId());
            endTime = System.currentTimeMillis();
            writerForCompleteTask.write("" + (endTime - startTime) + "\r\n");
//            System.out.println("complete: " + (endTime - startTime));
        }

        //进入子流程
        // -- 完成子流程第一步register
        Task registerItineraryTask = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        // -- -- traveler 认领任务
        startTime = System.currentTimeMillis();
        taskService.claim(registerItineraryTask.getId(), traveler);
        endTime = System.currentTimeMillis();
        writerForClaimTask.write("" + (endTime - startTime) + "\r\n");
//        System.out.println("claimm: " + (endTime - startTime));
        // -- -- 完成任务
        tasks = taskService.createTaskQuery().taskAssignee(traveler).list();
        subVariables.put("hotel", hotel);
        subVariables.put("car", car);
        subVariables.put("flight", flight);
        for(Task task : tasks) {
//            System.out.println("Task for " + task.getAssignee() + ": " + task.getName());
            startTime = System.currentTimeMillis();
            taskService.complete(task.getId(), subVariables);
            endTime = System.currentTimeMillis();
            writerForCompleteTask.write("" + (endTime - startTime) + "\r\n");
//            System.out.println("complete: " + (endTime - startTime));
        }
        // -- 子流程第二步：book
        tasks = taskService.createTaskQuery().processInstanceId(pi.getId()).list();
//        System.out.println("包容网关通过的数量：" + tasks.size());
        // -- -- 认领多个任务，包容网关
        for(Task task : tasks) {
//            System.out.println("Task for " + traveler + ": " + task.getName());
            startTime = System.currentTimeMillis();
            taskService.claim(task.getId(), traveler);
            endTime = System.currentTimeMillis();
            writerForClaimTask.write("" + (endTime - startTime) + "\r\n");
//            System.out.println("claim: " + (endTime - startTime));
        }
        // -- -- 完成子流程任务
        tasks = taskService.createTaskQuery().taskAssignee(traveler).list();
        // -- traveler完成任务
        for(Task task : tasks) {
//            System.out.println("Task for " + task.getAssignee() + ": " + task.getName());
            startTime = System.currentTimeMillis();
            taskService.complete(task.getId());
            endTime = System.currentTimeMillis();
            writerForCompleteTask.write("" + (endTime - startTime) + "\r\n");
//            System.out.println("completef: " + (endTime - startTime));
        }

        // -- 完成子流程第三步：prepare pay
        Task preparePayTask = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        // -- -- 认领prepare pay
        startTime = System.currentTimeMillis();
        taskService.claim(preparePayTask.getId(), traveler);
        endTime = System.currentTimeMillis();
        writerForClaimTask.write("" + (endTime - startTime) + "\r\n");
//        System.out.println("claim: " + (endTime - startTime));
        // -- -- 完成prepare pay
        tasks = taskService.createTaskQuery().taskAssignee(traveler).list();
        // -- traveler完成任务
        for(Task task : tasks) {
//            System.out.println("Task for " + task.getAssignee() + ": " + task.getName());
            startTime = System.currentTimeMillis();
            taskService.complete(task.getId());
            endTime = System.currentTimeMillis();
            writerForCompleteTask.write("" + (endTime - startTime) + "\r\n");
//            System.out.println("completea: " + (endTime - startTime));
        }

        //完成主流程pay任务
        Task payTask = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        // -- -- 认领prepare pay
        startTime = System.currentTimeMillis();
        taskService.claim(payTask.getId(), traveler);
        endTime = System.currentTimeMillis();
        writerForClaimTask.write("" + (endTime - startTime) + "\r\n");
//        System.out.println("claim: " + (endTime - startTime));
        // -- -- 完成prepare pay
        tasks = taskService.createTaskQuery().taskAssignee(traveler).list();
        // -- traveler完成任务
        for(Task task : tasks) {
//            System.out.println("Task for " + task.getAssignee() + ": " + task.getName());
            startTime = System.currentTimeMillis();
            taskService.complete(task.getId());
            endTime = System.currentTimeMillis();
            writerForCompleteTask.write("" + (endTime - startTime) + "\r\n");
//            System.out.println("completeb: " + (endTime - startTime));
        }

        ProcessInstance tempp = runtimeService.startProcessInstanceByKey("load-application");

        //判断是否完成
        System.out.println(historyService.createHistoricProcessInstanceQuery().finished().count());
    }

    //测试将proceddDefinitionEntity从数据库恢复到引擎内存需要花费的时间
    @Test
    public void testRecoverProcessDefinitionEntityTime() {
        //试图排除一切初始化工作
        String modelTwo = "processes/2_model.bpmn20.xml";
        DeploymentBuilder builder1 = repositoryService.createDeployment();
        builder1.addClasspathResource(modelTwo);
        builder1.deploy();
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("load-application");

        System.out.println("before recover");
        long startTime = System.currentTimeMillis();
        ProcessInstance pi1 = runtimeService.startProcessInstanceById("online-shopping:1:4");
        long endTime = System.currentTimeMillis();
        System.out.println("recover: " + (endTime - startTime));
        System.out.println("processDefinitionId:" + pi1.getProcessDefinitionId());

        System.out.println("after recover");
        //直接使用上面流程已经恢复的procssDefinition
        long startTime1 = System.currentTimeMillis();
        ProcessInstance pi2 = runtimeService.startProcessInstanceById("online-shopping:1:4");
        long endTime1 = System.currentTimeMillis();
        System.out.println("no recover: " + (endTime1 - startTime1));
        System.out.println("processDefinitionId:" + pi2.getProcessDefinitionId());

        long startTime2 = System.currentTimeMillis();
        ProcessInstance pi3 = runtimeService.startProcessInstanceById("online-shopping:1:4");
        long endTime2 = System.currentTimeMillis();
        System.out.println("no recover: " + (endTime2 - startTime2));//时间消耗大概是296ms；书本35页也有说到这种缓存带来的性能上的大提升
    }

    @Test
    public void testRAM() {
        //部署全部文档
//        String model1 = "processes/1_model.bpmn20.xml";
//        String model2 = "processes/2_model.bpmn20.xml";
//        String model3 = "processes/3_model.bpmn20.xml";
//        String model4 = "processes/4_model.bpmn20.xml";
//        String model5 = "processes/5_model.bpmn20.xml";
//        String model6 = "processes/6_model.bpmn20.xml";
//        String model7 = "processes/7_model.bpmn20.xml";
//        String model8 = "processes/8_model.bpmn20.xml";
//        String model9 = "processes/9_model.bpmn20.xml";
//        String model10 = "processes/10_model.bpmn20.xml";
//        String model11 = "processes/11_model.bpmn20.xml";
//        DeploymentBuilder builderTestRAM =  repositoryService.createDeployment();
//        builderTestRAM.addClasspathResource(model1);
//        builderTestRAM.addClasspathResource(model2);
//        builderTestRAM.addClasspathResource(model3);
//        builderTestRAM.addClasspathResource(model4);
//        builderTestRAM.addClasspathResource(model5);
//        builderTestRAM.addClasspathResource(model6);
//        builderTestRAM.addClasspathResource(model7);
//        builderTestRAM.addClasspathResource(model8);
//        builderTestRAM.addClasspathResource(model9);
//        builderTestRAM.addClasspathResource(model10);
//        builderTestRAM.addClasspathResource(model11);
//        builderTestRAM.name("testRAM");
//        builderTestRAM.enableDuplicateFiltering();//开启重复过滤
//        builderTestRAM.deploy();

        System.out.println("0: " + RamUsageEstimator.sizeOf(0));
        System.out.println("Integer 0: " + RamUsageEstimator.sizeOf(new Integer(0)));
        //计算模型1 RAM
        System.out.println("model 1 ram test");
        ExecutionEntity executionEntity1 = (ExecutionEntity) runtimeService.startProcessInstanceByKey("online-shopping");
        ActivityImpl initial1 = executionEntity1.getActivity();
        System.out.println(RamUsageEstimator.sizeOf(initial1));
        System.out.println(RamUsageEstimator.humanSizeOf(initial1));

        //计算模型2 RAM
        System.out.println("model 2 ram test");
        ExecutionEntity executionEntity2 = (ExecutionEntity) runtimeService.startProcessInstanceByKey("load-application");
        ActivityImpl initial2 = executionEntity2.getActivity();
        System.out.println(RamUsageEstimator.sizeOf(initial2));
        System.out.println(RamUsageEstimator.humanSizeOf(initial2));

        //计算模型3 RAM
        System.out.println("model 3 ram test");
        ExecutionEntity executionEntity3 = (ExecutionEntity) runtimeService.startProcessInstanceByKey("leave-process");
        ActivityImpl initial3 = executionEntity3.getActivity();
        System.out.println(RamUsageEstimator.sizeOf(initial3));
        System.out.println(RamUsageEstimator.humanSizeOf(initial3));

        //计算模型4 RAM
        System.out.println("model 4 ram test");
        ExecutionEntity executionEntity4 = (ExecutionEntity) runtimeService.startProcessInstanceByKey("a4-model");
        ActivityImpl initial4 = executionEntity4.getActivity();
        System.out.println(RamUsageEstimator.sizeOf(initial4));
        System.out.println(RamUsageEstimator.humanSizeOf(initial4));

        //计算模型5 RAM
        System.out.println("model 5 ram test");
        ExecutionEntity executionEntity5 = (ExecutionEntity) runtimeService.startProcessInstanceByKey("a5-model");
        ActivityImpl initial5 = executionEntity5.getActivity();
        System.out.println(RamUsageEstimator.sizeOf(initial5));
        System.out.println(RamUsageEstimator.humanSizeOf(initial5));

        //计算模型6 RAM
        System.out.println("model 6 ram test");
        ExecutionEntity executionEntity6 = (ExecutionEntity) runtimeService.startProcessInstanceByKey("a6-model");
        ActivityImpl initial6 = executionEntity6.getActivity();
        System.out.println(RamUsageEstimator.sizeOf(initial6));
        System.out.println(RamUsageEstimator.humanSizeOf(initial6));

        //计算模型7 RAM
        System.out.println("model 7 ram test");
        ExecutionEntity executionEntity7 = (ExecutionEntity) runtimeService.startProcessInstanceByKey("a7-model");
        ActivityImpl initial7 = executionEntity7.getActivity();
        System.out.println(RamUsageEstimator.sizeOf(initial7));
        System.out.println(RamUsageEstimator.humanSizeOf(initial7));

        //计算模型8 RAM
        System.out.println("model 8 ram test");
        ExecutionEntity executionEntity8 = (ExecutionEntity) runtimeService.startProcessInstanceByKey("a8-model");
        ActivityImpl initial8 = executionEntity8.getActivity();
        System.out.println(RamUsageEstimator.sizeOf(initial8));
        System.out.println(RamUsageEstimator.humanSizeOf(initial8));

        //计算模型9 RAM
        System.out.println("model 9 ram test");
        ExecutionEntity executionEntity9 = (ExecutionEntity) runtimeService.startProcessInstanceByKey("a9-model");
        ActivityImpl initial9 = executionEntity9.getActivity();
        System.out.println(RamUsageEstimator.sizeOf(initial9));
        System.out.println(RamUsageEstimator.humanSizeOf(initial9));

        //计算模型10 RAM
        System.out.println("model 10 ram test");
        ExecutionEntity executionEntity10 = (ExecutionEntity) runtimeService.startProcessInstanceByKey("a10-model");
        ActivityImpl initial10 = executionEntity10.getActivity();
        System.out.println(RamUsageEstimator.sizeOf(initial10));
        System.out.println(RamUsageEstimator.humanSizeOf(initial10));

        //计算模型11 RAM
        System.out.println("model 11 ram test");
        ExecutionEntity executionEntity11 = (ExecutionEntity) runtimeService.startProcessInstanceByKey("a11-model");
        ActivityImpl initial11 = executionEntity11.getActivity();
        System.out.println(RamUsageEstimator.sizeOf(initial11));
        System.out.println(RamUsageEstimator.humanSizeOf(initial11));

    }




    //multi instance如何设置的问题，先用instance 为1 就可以了
    //有点问题，需要调试
    @Test
    public void testTravelBooking() {
        //验证是否有加载
        long count = repositoryService.createProcessDefinitionQuery().count();
        System.out.println(count);

        //参数设定
        String traveler = "Mike";
        String hotel = "1";
        String flight = "0";
        String car = "1";

        //启动流程:
        Map<String, Object> variables = new HashMap<String, Object>();
        Map<String, Object> subVariables = new HashMap<String, Object>();

        ProcessInstance pi = runtimeService.startProcessInstanceByKey("travel-booking", variables);
        System.out.println(pi);
        //完成第一步：register
        Task registerTask = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        System.out.println(registerTask.getName());
        // -- traveler认领任务
        taskService.claim(registerTask.getId(), traveler);
        System.out.println("taskId:" + registerTask.getId());
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(traveler).list();
        // -- traveler完成任务
        for(Task task : tasks) {
            System.out.println("Task for " + task.getAssignee() + ": " + task.getName());
            taskService.complete(task.getId());
        }

        //进入子流程
        // -- 完成子流程第一步register
         Task registerItineraryTask = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        // -- -- traveler 认领任务
        taskService.claim(registerItineraryTask.getId(), traveler);
        // -- -- 完成任务
        tasks = taskService.createTaskQuery().taskAssignee(traveler).list();
        subVariables.put("hotel", hotel);
        subVariables.put("car", car);
        subVariables.put("flight", flight);
        for(Task task : tasks) {
            System.out.println("Task for " + task.getAssignee() + ": " + task.getName());
            taskService.complete(task.getId(), subVariables);
        }
        // -- 子流程第二步：book
        tasks = taskService.createTaskQuery().processInstanceId(pi.getId()).list();
        System.out.println("包容网关通过的数量：" + tasks.size());
        // -- -- 认领多个任务，包容网关
        for(Task task : tasks) {
            System.out.println("Task for " + traveler + ": " + task.getName());
            taskService.claim(task.getId(), traveler);
        }
        // -- -- 完成子流程任务
        tasks = taskService.createTaskQuery().taskAssignee(traveler).list();
        // -- traveler完成任务
        for(Task task : tasks) {
            System.out.println("Task for " + task.getAssignee() + ": " + task.getName());
            taskService.complete(task.getId());
        }

        // -- 完成子流程第三步：prepare pay
        Task preparePayTask = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        // -- -- 认领prepare pay
        taskService.claim(preparePayTask.getId(), traveler);
        // -- -- 完成prepare pay
        tasks = taskService.createTaskQuery().taskAssignee(traveler).list();
        // -- traveler完成任务
        for(Task task : tasks) {
            System.out.println("Task for " + task.getAssignee() + ": " + task.getName());
            taskService.complete(task.getId());
        }

        //完成主流程pay任务
        Task payTask = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        // -- -- 认领prepare pay
        taskService.claim(payTask.getId(), traveler);
        // -- -- 完成prepare pay
        tasks = taskService.createTaskQuery().taskAssignee(traveler).list();
        // -- traveler完成任务
        for(Task task : tasks) {
            System.out.println("Task for " + task.getAssignee() + ": " + task.getName());
            taskService.complete(task.getId());
        }

        //判断是否完成
        System.out.println(historyService.createHistoricProcessInstanceQuery().finished().count());
    }
}
