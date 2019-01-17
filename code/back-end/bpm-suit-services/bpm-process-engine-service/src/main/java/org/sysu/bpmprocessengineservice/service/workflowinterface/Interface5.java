package org.sysu.bpmprocessengineservice.service.workflowinterface;

import java.util.HashMap;
import java.util.Map;

/**
 * 根据工作流参考模型实现
 * 接口5是管理监控接口，这里只实现管理接口部分，主要是实现流程实例的监控管理
 */
public interface Interface5 {
    /** 根据流程模型名称 启动流程实例 */
    HashMap<String, Object> startProcessInstanceByKey(String processModelKey, Map<String, Object> variables);

    /** 根据流程定义ID启动流程; 流程定义是流程模型的解析结果 **/
    HashMap<String, Object> startProcessInstanceById(String processDefinitionId, Map<String, Object> variables);

    /** 根据流程实例ID判断任务是否已经完成 */
    HashMap<String, Object> isEnded(String processInstanceId);

    /** 返回指定页码的流程实例 */
    HashMap<String, Object> getProcessInstancesPage(int pageSize, int pageNumber);

//    /** 计算全部流程实例数 */
//    int countProcessInstance();



}
