package org.sysu.bpmprocessengineservice.activiti.ext;

import org.activiti.bpmn.converter.util.BpmnXMLUtil;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.handler.UserTaskParseHandler;
import org.activiti.engine.impl.pvm.process.ActivityImpl;

import java.util.List;
import java.util.Map;

public class CloudUserTaskParseHandler extends UserTaskParseHandler {
    @Override
    protected void executeParse(BpmnParse bpmnParse, UserTask userTask) {
        super.executeParse(bpmnParse, userTask);
        Map<String, List<ExtensionAttribute>> attributes = userTask.getAttributes();
        ActivityImpl activityImpl = findActivity(bpmnParse, userTask.getId());
        System.out.println(bpmnParse.getCurrentFlowElement().getAttributes());
        System.out.println("parse:" + attributes);
        System.out.println(bpmnParse.getBpmnModel().getProcesses().get(0).getFlowElement(userTask.getId()).getAttributes());
        activityImpl.setProperty("cloudExt", attributes);
    }
}
