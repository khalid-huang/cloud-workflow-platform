package org.sysu.bpmprocessenginesportal.scheduler.rule;

import com.google.common.util.concurrent.AtomicDouble;
import com.netflix.loadbalancer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  Locality-Based minimum busyness with Replication
 *  基于局部性的带复制功能的最小繁忙度调度算法
 *  超载设置：繁忙度超过多少时就从全部引擎实例上选择繁忙度最小的，可以做为一个在均衡性和命中率中的权衡因子
 *  选择策略：
 *      1）查询请求直接获取繁忙度最小的服务器
 *      2）第一次执行流程定义的执行请求直接获取繁忙度最小的服务器
 *      3）非第一次执行流程定义的执行请求从历史执行引擎获取繁忙度最小的服务器，如果判断超过超载设置，就从全局获取繁忙度最小的服务器
 */
public class LBMBRRule extends BestAvailableRule {

    private static Logger logger = LoggerFactory.getLogger(LBMBRRule.class);

    public static ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(5);

    public FileWriter writerForBusyness = null;

    // 维护服务器组
    private final Map<String, Set<Server>> proDefinitionIdToServerGroup =
            Collections.synchronizedMap(new LinkedHashMap<String, Set<Server>>(100, 0.75f, true));

    private final Map<Server, AtomicInteger> serverRequestCounts = new ConcurrentHashMap<>();

    //服务器的综合繁忙度（类似ER，包含了历史数据，每秒更新一次）
    private final Map<Server, AtomicDouble> serverBusyness = new ConcurrentHashMap<>();

    private final Map<Server, AtomicInteger> serverRequestCountsIn5seconds = new ConcurrentHashMap<>();

    private final double historyRate = 0.6;
    private final int engineMaxRequest = 60;
    private final double threshold = 0.8;
    private boolean flag = false; //是否初始化过init

    public void init() {
        List<Server> servers =  getLoadBalancer().getAllServers();
        for(Server server : servers) {
            this.serverRequestCounts.put(server, new AtomicInteger());
            this.serverBusyness.put(server, new AtomicDouble());
            this.serverRequestCountsIn5seconds.put(server, new AtomicInteger());
        }
        try {
            writerForBusyness = new FileWriter("D:\\lb\\LBLMB_busyness.txt");
//            writerForBusyness = new FileWriter("LBLMB_busyness.txt");
        } catch (IOException e) {

        }
        LBMBRRule.Task task = new LBMBRRule.Task();
        LBMBRRule.WriteLogTask writeLogTask = new LBMBRRule.WriteLogTask();
        scheduledThreadPoolExecutor.scheduleAtFixedRate(task, 1, 1, TimeUnit.SECONDS);
        scheduledThreadPoolExecutor.scheduleAtFixedRate(writeLogTask, 5, 5, TimeUnit.SECONDS);
    }

    public LBMBRRule() {
        super();
    }

    public Server chooseServer(ILoadBalancer lb, Object key) {
        if(lb == null) {
            logger.warn("no load balancer");
            return null;
        }
        LoadBalancerStats loadBalancerStats = ((AbstractLoadBalancer)lb).getLoadBalancerStats();
        if (loadBalancerStats == null) {
            return super.choose(key);
        }

        Server server = null;
        List<Server> reachableServers = lb.getReachableServers();
        int upCount = reachableServers.size();
        if((upCount == 0)) {
            logger.warn("no up servers available from load balancer");
            return null;
        }
//        查询请求直接从全局获取最小的繁忙度
        if ("getCurrentSingleTask".equals(key)) {
            server = getMinBusynessServer(getLoadBalancer().getReachableServers());
        } else {
            server = _choose(reachableServers, loadBalancerStats, key);
        }
        if (server == null) {
            return super.choose(key);
        } else {
            return server;
        }
    }

    private Server _choose(List<Server> reachableServer, LoadBalancerStats stats, Object key) {
        if (stats == null) {
            logger.warn("no statistics, nothing to do so");
            return null;
        }

        String processDefinitionId = (String) key;
        Set<Server> servers = proDefinitionIdToServerGroup.get(processDefinitionId);
        Server result = null;
        // 第一次执行流程定义
        if (servers == null || servers.size() == 0) {
//            logger.info("第一次执行该流程定义----------");
            //获取负载最小
            result = getMinBusynessServer(getLoadBalancer().getReachableServers());
            servers = new HashSet<>();
            servers.add(result);
            proDefinitionIdToServerGroup.put(processDefinitionId, servers);
        }
        else {
//            logger.info("从之前执行过的引擎中选择----------");
            List<Server> previousServerList = new ArrayList<>(servers);
            result = chooseMinBusynessServerFromServerGroup(reachableServer, previousServerList, stats);
            if (result == null) {
                result = super.choose(key);
            }
            servers.add(result);
            proDefinitionIdToServerGroup.put(processDefinitionId, servers);
        }
        return result;
    }

    public Server chooseMinBusynessServerFromServerGroup(List<Server> reachableServers, List<Server> previousServers, LoadBalancerStats stats) {
//       Server result = getMinBusynessServer(previousServers);
        //debug代码，打印数据
        Server result = null;
        double min = Integer.MAX_VALUE;
        for(Server server : previousServers) {
            Double busyness = this.serverBusyness.get(server).doubleValue();
            if(min > busyness) {
                result = server;
                min = busyness;
            }
        }
        //如果最小的繁忙度大于0.9的话，就从全局选取
        if(min > this.threshold) {
            logger.info("最小繁忙度大于阀值，全局获取");
            result = getMinBusynessServer(reachableServers);
        }
        return result;
    }

    public Server choose(Object key) {
        if (!flag) {
            synchronized ((Object) flag) {
                if(!flag) {
                    init();
                    flag = true;
                }
            }
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String uri = request.getRequestURI();

        String processDefinitionId = "";
        // 查询处理
        if (uri.contains("getCurrentSingleTask")) {
            processDefinitionId = "getCurrentSingleTask";
        }
        else {
            //获取processDefinitionId的值
            int startIndex = uri.indexOf('/', 1)+1;
            int endIndex = uri.indexOf('/', startIndex);
            if (endIndex == -1) endIndex = uri.length();
            processDefinitionId = uri.substring(startIndex, endIndex);
        }

        Server server =  chooseServer(getLoadBalancer(), processDefinitionId);
        this.serverRequestCounts.get(server).incrementAndGet();
        return server;
    }

    //获取繁忙度最小的引擎
    public Server getMinBusynessServer(List<Server> servers) {
        Server target = null;
        double min = Integer.MAX_VALUE;
        for(Server server : servers) {
            double busyness = this.serverBusyness.get(server).doubleValue();
            if(min > busyness) {
                min = busyness;
                target = server;
            }
        }
        return target;
    }

    public synchronized void  updateServerHistoryBusyness() {
        List<Server> servers = getLoadBalancer().getAllServers();
        String log2 = "lblmb:";
        for(Server server : servers) {
            AtomicInteger requestCount = this.serverRequestCounts.get(server);
            double curBusyness = requestCount.doubleValue() / this.engineMaxRequest;
            double busyness = this.serverBusyness.get(server).doubleValue() * historyRate
                    + curBusyness * (1 - historyRate);
            log2 += server.getHostPort() + ": " + requestCount.intValue() + " - " + busyness + "  ";
            this.serverRequestCountsIn5seconds.get(server).addAndGet(requestCount.intValue());
            this.serverRequestCounts.get(server).set(0);
            this.serverBusyness.get(server).set(busyness);
        }
        logger.info(log2);
    }

    public synchronized void writeBusyness() {
        List<Server> servers = getLoadBalancer().getAllServers();
        String log = "";
        for(Server server : servers) {
            Double requestCount = this.serverRequestCountsIn5seconds.get(server).doubleValue()/(5*this.engineMaxRequest);
            log += new BigDecimal(requestCount + "").toString() + "   ";
            this.serverRequestCountsIn5seconds.get(server).set(0);
        }
        try {
            this.writerForBusyness.write(log + "\r\n");
            this.writerForBusyness.flush();
        } catch (IOException e ) {
            e.printStackTrace();
        }
    }

    private class Task implements  Runnable {
        @Override
        public void run() {
            LBMBRRule.this.updateServerHistoryBusyness();
        }
    }

    private class WriteLogTask implements  Runnable {
        @Override
        public void run() {
            LBMBRRule.this.writeBusyness();
        }
    }

}
