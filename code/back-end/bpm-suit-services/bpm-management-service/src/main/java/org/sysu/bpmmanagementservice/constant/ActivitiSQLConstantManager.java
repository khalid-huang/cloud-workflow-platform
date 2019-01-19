package org.sysu.bpmmanagementservice.constant;

/**
 * 保存了activiti的一些原生查询的语句
 */
public class ActivitiSQLConstantManager {
    /** taskService */
    //查询unoffered，unoffered表示没有assignee，没有候选人的任务组成的列表
    public static final String TASKSERVICE_TASKNATIVETASKQUERY_UNOFFERED = "SELECT task.* "
        + "FROM act_ru_task task LEFT JOIN act_ru_identitylink identitylink "
        + "ON task.ID_ = identitylink.TASK_ID_ "
        + "WHERE identitylink.TASK_ID_ IS NULL "
        + "AND task.ASSIGNEE_ IS NULL";

    //查询worklisted，表示有assignee，或是有候选人的；
    //对应到workitem的生命周期就是非unoffered
    public static final String TASKSERVICE_TASKNATIVETASKQUERY_WORKLISTED = "SELECT task.* "
            + "FROM act_ru_task task LEFT JOIN act_ru_identitylink identitylink "
            + "ON task.ID_ = identitylink.TASK_ID_ "
            + "WHERE ( task.ASSIGNEE_ IS NULL AND identitylink.TYPE_ = 'candidate' ) "
            + "OR task.ASSIGNEE_ IS NOT NULL";
}
