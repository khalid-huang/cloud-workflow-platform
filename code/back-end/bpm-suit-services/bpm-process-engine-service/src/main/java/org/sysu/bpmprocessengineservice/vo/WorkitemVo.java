package org.sysu.bpmprocessengineservice.vo;

import org.activiti.engine.task.Task;

import java.util.Date;

public class WorkitemVo {
    private String id;

    private String name;

    private Date createTime;

    private String assignee;

    private String processInstanceId;//流程实例id

    private String processDefinitionId;//流程定义id

    private String description;

    private String category;

    public WorkitemVo(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.createTime = task.getCreateTime();
        this.assignee = task.getAssignee();
        this.processDefinitionId = task.getProcessDefinitionId();
        this.processInstanceId = task.getProcessInstanceId();
        this.category = task.getCategory();
        this.description = task.getDescription();
    }
}
