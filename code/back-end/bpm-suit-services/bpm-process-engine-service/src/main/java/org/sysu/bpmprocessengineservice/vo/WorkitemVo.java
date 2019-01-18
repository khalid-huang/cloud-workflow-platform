package org.sysu.bpmprocessengineservice.vo;

import org.activiti.engine.task.Task;

import java.util.Date;

public class WorkitemVo  {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
