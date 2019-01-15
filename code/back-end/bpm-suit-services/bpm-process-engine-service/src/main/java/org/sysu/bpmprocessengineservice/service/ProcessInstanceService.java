package org.sysu.bpmprocessengineservice.service;

import java.util.HashMap;
import java.util.Map;

public interface ProcessInstanceService {
    /** 根据流程模型名称 启动流程实例 */
    HashMap<String, String> startProcessInstanceByKey(String processModelKey, Map<String, Object> variables);

    /** 根据流程定义ID启动流程; 流程定义是流程模型的解析结果 **/
    HashMap<String, String> startProcessInstanceById(String processDefinitionId, Map<String, Object> variables);

}
