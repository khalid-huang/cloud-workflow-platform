package org.sysu.bpmprocessenginesportal.scheduler.rule;

import com.netflix.loadbalancer.BestAvailableRule;

/**
 * Locality-Based minimum busyness with Replication
 * 基于局部性的带复制功能的最小繁忙度调度算法
 * 超载设置 60%
 * 初始选择 历史繁忙度 * 50% + 流程定义数繁忙数 * 50% ？？或是就用历史繁忙度；用前面的；流程定义繁忙度用流程定义数除以缓存数就可以了
 * 繁忙度的计算 getActiveRequestsCount/maxConnections
 */
public class LBLMBRule extends BestAvailableRule {

}
