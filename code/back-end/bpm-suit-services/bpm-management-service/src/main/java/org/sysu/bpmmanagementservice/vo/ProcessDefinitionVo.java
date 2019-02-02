package org.sysu.bpmmanagementservice.vo;

import org.activiti.engine.repository.ProcessDefinition;

public class ProcessDefinitionVo {
    private String processDefintionId;

    public ProcessDefinitionVo(ProcessDefinition processDefinition) {
        this.processDefintionId = processDefinition.getId();
    }

    public String getProcessDefintionId() {
        return processDefintionId;
    }

    public void setProcessDefintionId(String processDefintionId) {
        this.processDefintionId = processDefintionId;
    }
}
