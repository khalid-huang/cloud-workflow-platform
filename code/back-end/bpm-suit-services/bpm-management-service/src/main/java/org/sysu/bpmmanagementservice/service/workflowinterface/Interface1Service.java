package org.sysu.bpmmanagementservice.service.workflowinterface;

import java.util.HashMap;

/** 工作流定义接口，与建模工作对接的接口 */
public interface Interface1Service {
    /** 流程编辑器保存模型 */
    void saveModel(String modelId, String name, String json_xml, String svg_xml, String description);
}
