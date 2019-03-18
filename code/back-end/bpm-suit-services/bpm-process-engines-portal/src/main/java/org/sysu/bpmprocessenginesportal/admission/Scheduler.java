package org.sysu.bpmprocessenginesportal.admission;

/** 用于实现限流的调度器 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.sysu.bpmprocessenginesportal.admission.responsetimeadmission.RTLScheduler;
import org.sysu.bpmprocessenginesportal.requestcontext.ExecuteRequestContext;
import org.sysu.bpmprocessenginesportal.requestcontext.RequestRateRequestContext;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

//准入控制的入口调度器
/** 限流的策略是：如果不超过租户最大并发数就直接进入准入，否则进入缓存队列，直到租户并发数降下来*/
@Component
public class Scheduler {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RTLScheduler RTLScheduler;

    //如下的缓存都用HashMap实现，也可以基于redis :https://blog.csdn.net/xusheng__zhang/article/details/78856146
    //用于缓存所有租户的每秒最大并发数；
    //可以使用线程来定时更新这个hashmap https://www.jianshu.com/p/920b445b5ef9
    private final HashMap<String, Long> tenantMaxConcurrentNumber = new HashMap<>();

    //每个租户当前的并发数
    private final HashMap<String, LongAdder> tenantCurrentConcurrentNumber = new HashMap<>();

    //线程池用于每秒更新LongAdder
    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);

    //用于缓存租户的过量请求; 要线程安全的
    private final HashMap<String, ConcurrentLinkedQueue<RequestRateRequestContext>> requestPendingQueues = new HashMap<>();

    /** 预设一些数据 */
    @PostConstruct
    void init() {
        tenantMaxConcurrentNumber.put("1", 20L); //租户id为1的用户每秒最多可并发20个请求
        tenantMaxConcurrentNumber.put("2", 30L); //租户id为2的用户每秒最多可并发30个请求
        tenantMaxConcurrentNumber.put("3", 20L); //租户id为3的用户每秒最多可并发30个请求
        tenantMaxConcurrentNumber.put("4", 20L); //租户id为3的用户每秒最多可并发30个请求
        tenantCurrentConcurrentNumber.put("1", new LongAdder());
        tenantCurrentConcurrentNumber.put("2", new LongAdder());
        tenantCurrentConcurrentNumber.put("3", new LongAdder());
        tenantCurrentConcurrentNumber.put("4", new LongAdder());
        requestPendingQueues.put("1", new ConcurrentLinkedQueue<>());
        requestPendingQueues.put("2", new ConcurrentLinkedQueue<>());
        requestPendingQueues.put("3", new ConcurrentLinkedQueue<>());
        requestPendingQueues.put("4", new ConcurrentLinkedQueue<>());
        //启动更新
        Task task = new Task();
        scheduledThreadPoolExecutor.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
    }

    /** 完成请求的调度，需要返回结果 */
    public ResponseEntity<?> scheduler(String tenantId, RequestMethod method, String url, MultiValueMap<String, Object> variables) {
        increase(tenantId);
        RequestRateRequestContext requestRateRequestContext = new RequestRateRequestContext(method, tenantId ,url, variables, restTemplate);
        if(getCurrentConcurrentNumber(tenantId) < getMaxConcurrentNumber(tenantId)) {
            //直接进入准入调度
            transferToRTAdmissonor(requestRateRequestContext);
        } else {
            //进入缓存，等待下一秒开始
            this.requestPendingQueues.get(tenantId).offer(requestRateRequestContext);
        }

        //阻塞同步
        try {
            return requestRateRequestContext.getFutureTask().get();
        } catch (Exception e) {
            return ResponseEntity.ok(e.toString());
        }
    }

    //优先处理缓存中的请求
    private synchronized void handleRequestPendingQueues() {
        for(String tenantId : requestPendingQueues.keySet()) {
            while (this.getCurrentConcurrentNumber(tenantId) < this.getMaxConcurrentNumber(tenantId)
                    && !this.requestPendingQueues.get(tenantId).isEmpty()) {
                transferToRTAdmissonor(this.requestPendingQueues.get(tenantId).poll());
            }
        }
    }

    private void transferToRTAdmissonor(RequestRateRequestContext requestRateRequestContext) {
        //rtl由admmissor自己缓存获取；
        ExecuteRequestContext executeRequestContext = new ExecuteRequestContext(requestRateRequestContext);
        RTLScheduler.admit(requestRateRequestContext);
    }

    public long getCurrentConcurrentNumber(String tenantId) {
        return this.tenantCurrentConcurrentNumber.get(tenantId).longValue();
    }

    public long getMaxConcurrentNumber(String tenantId) {
        return this.tenantMaxConcurrentNumber.get(tenantId);
    }

    public void increase(String tenantId) {
        this.tenantCurrentConcurrentNumber.get(tenantId).increment();
    }

    private class Task implements Runnable {
        @Override
        public void run() {
            for(String tenantId : Scheduler.this.tenantCurrentConcurrentNumber.keySet()) {
                Scheduler.this.tenantCurrentConcurrentNumber.get(tenantId).reset();
                //优先处理掉缓存队列中的请求
                while (Scheduler.this.getCurrentConcurrentNumber(tenantId) < Scheduler.this.getMaxConcurrentNumber(tenantId)
                        && !Scheduler.this.requestPendingQueues.get(tenantId).isEmpty()) {
                    transferToRTAdmissonor(Scheduler.this.requestPendingQueues.get(tenantId).poll());
                }
            }
        }
    }
}
