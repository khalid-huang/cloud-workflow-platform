package org.sysu.bpmmanagementservice;

import org.activiti.engine.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BpmManagementServiceApplicationTest {

    @Autowired
    TaskService taskService;

    @Test
    public void textContext() {
        System.out.println(taskService);

    }
}
