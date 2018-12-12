package org.sysu.processexecutionservice.admission;

import org.springframework.stereotype.Component;
import org.sysu.processexecutionservice.admission.queuecontext.IQueueContext;
import org.sysu.processexecutionservice.admission.queuecontext.LinkedBlockingDelayQueueContext;
import org.sysu.processexecutionservice.admission.queuecontext.LinkedBlockingExecuteQueueContext;
import org.sysu.processexecutionservice.admission.requestcontext.ActivitiExecuteRequestContext;
import org.sysu.processexecutionservice.admission.requestcontext.IRequestContext;
import org.sysu.processexecutionservice.admission.rule.BaseQueueScoreRule;
import org.sysu.processexecutionservice.admission.rule.BaseRule;
import org.sysu.processexecutionservice.admission.rule.IRule;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

@Component
public class ActivitiExecuteAdmissionor implements IAdmissionor {

//    每个时间片的长度
    private int timeSlice = 1000; //单位毫秒
//    请求最高可以延迟的时间片个数
    private int maxsSliceNum = 4;

//    队列初始化要交由相应的admit算法来进行，因为不同的算法可能有不同的队列需要【使用反射】
    private IQueueContext[] delayQueueContexts;

    private IQueueContext executeQueueContext;

    private IRule admissionRule;

    private ExecutorService executorService = Executors.newCachedThreadPool();

    private Counter originalCounter; //原始波形的计算器
    private Counter smoothCounter; //平滑之后的计算器
    private String originalCounterFile = "E:\\workspace\\temp\\counter\\originalCounter.txt";
    private String smoothCounterFile = "E:\\workspace\\temp\\counter\\smoothCounter.txt";

//    历史每秒请求数的均值
    private double averageHistoryRequestNumber = 0;
    private double historyRate = 0.4; //历史值占的比重

//    for test
    private String usingRule = "BaseQueueScoreRule";
//    private String usingRule = "BaseRule";


    public double getAverageHistoryRequestNumber() {
        return averageHistoryRequestNumber;
    }

    public void setAverageHistoryRequestNumber(double averageHistoryRequestNumber) {
        this.averageHistoryRequestNumber = averageHistoryRequestNumber;
    }

//    基于新的过去一秒的值计算历史均值
    public void computerAverageHistoryRequestNumber(int current) {
        double newValue = this.historyRate * this.averageHistoryRequestNumber + (1 - this.historyRate) * current;
        this.averageHistoryRequestNumber = Math.floor(newValue);
    }

    @PostConstruct
    @SuppressWarnings("unchecked")
    private void init() {
//     需要使用反射进行处理的，这里是实验代码，先简单处理
        int minDelayTime, maxDelayTime;
        if(usingRule.equals("BaseQueueScoreRule")) {
            admissionRule = new BaseQueueScoreRule(this);
            delayQueueContexts = new LinkedBlockingDelayQueueContext[this.maxsSliceNum];//java数组这里只是分配了引用空间，还需要进行实例化对象
            IQueueContext nextQueueContext;
            executeQueueContext = new LinkedBlockingExecuteQueueContext(this);
            for(int i = 0; i < this.maxsSliceNum; i++) {
                minDelayTime = i * this.timeSlice;
                maxDelayTime = (i+1) * this.timeSlice;
                if(i == 0) {
                    nextQueueContext = executeQueueContext;
                } else {
                    nextQueueContext = delayQueueContexts[i-1];
                }
                delayQueueContexts[i] = new LinkedBlockingDelayQueueContext(minDelayTime, maxDelayTime, timeSlice, nextQueueContext);
            }
//           启动循环检查
            LinkedBlockingDelayQueueContext temp;
            for(int i = 0; i < this.maxsSliceNum; i++) {
                temp = (LinkedBlockingDelayQueueContext) delayQueueContexts[i];
                temp.startCheck();
            }
        }

        if(usingRule.equals("BaseRule")) {
            admissionRule = new BaseRule(this);
            executeQueueContext = new LinkedBlockingExecuteQueueContext(this);
            System.out.println("BaseRule"+ executeQueueContext);
        }

//        生成计算器
        originalCounter = new Counter(originalCounterFile, this);
        smoothCounter = new Counter(smoothCounterFile, this);
    }

    @Override
    public void admit(IRequestContext requestContext) {
//        在这里可以统计请求的原始波形
        originalCounter.increase();
        this.admissionRule.admit(requestContext);
    }

//    表示分派到调度器进行调度
//    这个方法由LinkedBlockingExecuteQueue调用，需要异步处理
    @Override
    public void dispatch(IRequestContext requestContext) {
//        在这里可以统计平滑之后的波形
        smoothCounter.increase();
        ActivitiExecuteRequestContext activitiExecuteRequestContext = (ActivitiExecuteRequestContext) requestContext;
        this.executorService.execute(activitiExecuteRequestContext.getFutureTask());
    }

    public IQueueContext[] getDelayQueueContexts() {
        return delayQueueContexts;
    }

    public void setDelayQueueContexts(IQueueContext[] delayQueueContexts) {
        this.delayQueueContexts = delayQueueContexts;
    }

    public IQueueContext getExecuteQueueContext() {
        return executeQueueContext;
    }

    public void setExecuteQueueContext(IQueueContext executeQueueContext) {
        this.executeQueueContext = executeQueueContext;
    }
}
