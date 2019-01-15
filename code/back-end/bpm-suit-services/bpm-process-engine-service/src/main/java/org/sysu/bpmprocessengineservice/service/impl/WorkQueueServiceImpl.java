package org.sysu.bpmprocessengineservice.service.impl;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.sysu.bpmprocessengineservice.constant.ActivitiSQLConstantManager;
import org.sysu.bpmprocessengineservice.constant.ResponseConstantManager;
import org.sysu.bpmprocessengineservice.service.WorkQueueService;

import java.util.HashMap;
import java.util.List;

/**
 * 基于Activiti实现的工作列表服务; Activiti中的Task就是workitem
 */
public class WorkQueueServiceImpl implements WorkQueueService {
    private final static Logger logger = LoggerFactory.getLogger(WorkQueueServiceImpl.class);

    @Autowired
    TaskService taskService;

    @Override
    public HashMap<String, Object> getWorkQueue(String username, String role) {
        if(role.equals("admin")) {
            //如果是系统管理员,返回所有的工作列表
            return getWorkQueueForAdmin(username);
        } else {
            //如果是普通用户，返回对应的工作列表
            return getWorkQueueForUser(username);
        }
    }

    //需要返回unoffered列表和worklisted列表
    @Override
    public HashMap<String, Object> getWorkQueueForAdmin(String username) {
        HashMap<String, Object> response = new HashMap<>();
        //获取Unoffered列表;没有委托人或是签收人就表示是unoffered的
        List<Task> unoffered = taskService.createNativeTaskQuery().sql(ActivitiSQLConstantManager.TASKSERVICE_TASKNATIVETASKQUERY_UNOFFERED).list();
        //获取已经worklisted列表,也就是已经被offer或是allocated的任务
        List<Task> worklisted = taskService.createNativeTaskQuery().sql(ActivitiSQLConstantManager.TASKSERVICE_TASKNATIVETASKQUERY_WORKLISTED).list();
        worklisted.removeAll(unoffered);
        response.put("status", ResponseConstantManager.STATUS_SUCCESS);
        response.put("unoffered", unoffered);
        response.put("worklisted", worklisted);
        return response;
    }

    //需要返回offered，allocated，started,suspended; (兼容Activiti，去掉allocated，所有的allocated都自动变成start)
    @Override
    public HashMap<String, Object> getWorkQueueForUser(String username) {
        HashMap<String, Object> response = new HashMap<>();
        //offered（这里其实还应该加上那个角色的，username属于哪个组，但目前角色，人员还没有实现完全，所以先不做）
        List<Task> offered = taskService.createTaskQuery().taskCandidateUser(username).list();
        //started，而且是活动的
        List<Task> started = taskService.createTaskQuery().taskAssignee(username).active().list();
        //suspended
        List<Task> suspended = taskService.createTaskQuery().taskAssignee(username).suspended().list();

        response.put("status", ResponseConstantManager.STATUS_SUCCESS);
        response.put("offered", offered);
        response.put("started", started);
        response.put("suspended", suspended);
        return response;
    }
}
