package org.sysu.bpmprocessengineservice.activiti.ext;

import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.task.TaskDefinition;

public class CloudActivitiBehaviorFactory extends DefaultActivityBehaviorFactory {
    @Override
    public UserTaskActivityBehavior createUserTaskActivityBehavior(UserTask userTask, TaskDefinition taskDefinition) {
        return new CloudUserTaskActivitiBehavior(userTask.getId(), taskDefinition);
    }
}
