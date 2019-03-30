package org.sysu.bpmprocessengineservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.impl.util.io.InputStreamSource;
import org.junit.Test;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplication {

    @Test
    public void test() {
        // 流程文档
        String resource = "processes/2_model.bpmn20.xml";
        // 获取流程文档数据流
        InputStream xmlStream = this.getClass().getClassLoader()
                .getResourceAsStream(resource);
        InputStreamSource xmlSource = new InputStreamSource(xmlStream);
        // 实例化BpmnXMLConverter
        BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
        // 转换为流程模型
        BpmnModel bpmnModel = bpmnXMLConverter.convertToBpmnModel(xmlSource,
                true, true, "UTF-8");
        // 获取id为operationTask的任务节点所有信息
        //debug
        try {
            byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnModel);
            System.out.println(new String(bpmnBytes, "UTF-8"));
        } catch (Exception e) {

        }


        FlowElement flowElement = bpmnModel.getProcesses().get(0)
                .getFlowElement("usertask1");

        Map<String, List<ExtensionAttribute>> attributes = flowElement.getAttributes();
        List<ExtensionAttribute> list = attributes.get("brole");
        System.out.println(list.get(0).getName() + " - " + list.get(0).getValue());
    }
}
