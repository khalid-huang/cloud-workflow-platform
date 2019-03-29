package org.sysu.bpmprocessengineservice.activiti.ext;

import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.activiti.engine.impl.task.TaskDefinition;
import org.sysu.bpmprocessengineservice.component.SpringContextUtils;
import org.sysu.bpmprocessengineservice.dao.BroleMappingEntityDao;
import org.sysu.bpmprocessengineservice.dao.RenConnectEntityDao;
import org.sysu.bpmprocessengineservice.entity.BroleMappingEntity;
import org.sysu.bpmprocessengineservice.entity.RenConnectEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 扩展Activiti默认用户节点行为类，支持业务角色映射
 */
public class CloudUserTaskActivitiBehavior extends UserTaskActivityBehavior {
    public CloudUserTaskActivitiBehavior(String userTaskId, TaskDefinition t) {
        super(userTaskId, t);
    }

    /**
     * 提取brole的值，根据brole的值和流程实例编号，获取角色映射关系，根据业务角色映射关系，查找ID，再写入到任务中
     * */
    @Override
    protected void handleAssignments(Expression assigneeExpression, Expression ownerExpression, Set<Expression> candidateUserExpressions, Set<Expression> candidateGroupExpressions, TaskEntity task, ActivityExecution execution) {
        System.out.println("cloudUserTaskActivitiBehavior");
        PvmActivity activity = execution.getActivity();
        Object propety = activity.getProperty("cloudExt"); //获取命名空间
        Map<String, List<ExtensionAttribute>> extensionAttribute = null;
        if(propety != null) {
            extensionAttribute = (Map<String, List<ExtensionAttribute>>) propety;
        }
        System.out.println("extensionAttribute" + extensionAttribute);
        List<ExtensionAttribute> brolesList = extensionAttribute.get("brole");
        System.out.println("broleList:" + brolesList);
        if(brolesList != null && !brolesList.isEmpty()) {
            //获取流程定义编号
            String procDefId = activity.getProcessDefinition().getId();
            //获取hibernate连接
            //获取brole
            BroleMappingEntityDao broleMappingEntityDao = SpringContextUtils.getBean(BroleMappingEntityDao.class);
            RenConnectEntityDao renConnectEntityDao = SpringContextUtils.getBean(RenConnectEntityDao.class);
            List<String> candidates = new ArrayList<>();
            for(ExtensionAttribute broles: brolesList) {
                String value = broles.getValue();
                System.out.println("value: " + value);
                String[] split = value.split(",");
                //获取组织ID
                for(String brole : split) {
                    System.out.println("brole: " + brole);
                    List<BroleMappingEntity> mappings = broleMappingEntityDao.findByBroleNameAndProcDefId(brole, procDefId);
                    for(BroleMappingEntity mapping : mappings) {
                        System.out.println("mappings:" + mapping.getMappedType() + " -" + mapping.getMappedId());
                        List<RenConnectEntity> connections = renConnectEntityDao.findAllByBelongToOrganizabledIdAndType(
                                mapping.getMappedId(),
                                mapping.getMappedType()
                        );
                        for(RenConnectEntity connection: connections) {
                            System.out.println("username:" + connection.getUsername());
                            candidates.add(connection.getUsername());
                        }
                    }
                }
            }

            task.addCandidateUsers(candidates);
        }

        super.handleAssignments(assigneeExpression, ownerExpression, candidateUserExpressions, candidateGroupExpressions, task, execution);
    }
}
