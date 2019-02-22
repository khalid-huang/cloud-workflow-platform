package org.sysu.bpmprocessenginesportal.scheduler.rule;

import com.netflix.loadbalancer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

// 自定义的ribbon负载均衡规则
public class ActivitiRule extends BestAvailableRule {

    private static Logger logger = LoggerFactory.getLogger(ActivitiRule.class);

    // 维护服务器组
    private final Map<String, Set<Server>> proDefinitionIdToServerGroup =
            Collections.synchronizedMap(new LinkedHashMap<String, Set<Server>>(200, 0.75f, true));

    public ActivitiRule() {
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
        server = _choose(reachableServers, loadBalancerStats, key);
        if (server == null) {
            return super.choose(key);
        }
        else {
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
            logger.info("第一次执行该流程定义----------");
            result = super.choose(key);
            servers = new HashSet<>();
            servers.add(result);
            proDefinitionIdToServerGroup.put(processDefinitionId, servers);
        }
        else {
            logger.info("从之前执行过的引擎中选择----------");
            List<Server> previousServerList = new ArrayList<>(servers);
            result = chooseMinConcurrentFromServerGroup(reachableServer, previousServerList, stats);
            if (result == null) {
                result = super.choose(key);
            }
            servers.add(result);
            proDefinitionIdToServerGroup.put(processDefinitionId, servers);
        }
        return result;
    }

    private Server chooseMinConcurrentFromServerGroup(List<Server> reachableServer, List<Server> previousServerList, LoadBalancerStats stats) {
        Server result = null;
        int minimalConcurrentConnections = Integer.MAX_VALUE;
        long currentTime = System.currentTimeMillis();
        for (Server server : previousServerList) {
            ServerStats serverStats = stats.getSingleServerStat(server);
            if (!serverStats.isCircuitBreakerTripped(currentTime)) {
                int concurrentConnections = serverStats.getActiveRequestsCount(currentTime);
                if (concurrentConnections < minimalConcurrentConnections) {
                    minimalConcurrentConnections = concurrentConnections;
                    result = server;
                }
            }
        }
        // 设置并发数超过30就从所有服务器中选择并发最小的
        if (minimalConcurrentConnections > 30) {
            List<Server> serverList = new ArrayList<>(reachableServer);
            serverList.removeAll(previousServerList);
            for (Server server: serverList) {
                ServerStats serverStats = stats.getSingleServerStat(server);
                if (!serverStats.isCircuitBreakerTripped(currentTime)) {
                    int concurrentConnections = serverStats.getActiveRequestsCount(currentTime);
                    if (concurrentConnections < minimalConcurrentConnections) {
                        minimalConcurrentConnections = concurrentConnections;
                        result = server;
                    }
                }
            }
        }
        return result;
    }

    public Server choose(Object key) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String uri = request.getRequestURI();

        // 查询直接处理
        if (uri.contains("get")) return super.choose(key);

        //获取processDefinitionId的值
        int startIndex = uri.indexOf('/', 1)+1;
        int endIndex = uri.indexOf('/', startIndex);
        if (endIndex == -1) endIndex = uri.length();
        String processDefinitionId = uri.substring(startIndex, endIndex);
        return chooseServer(getLoadBalancer(), processDefinitionId);
    }

}
