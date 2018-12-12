package org.sysu.processexecutionservice.admission.rule;

import org.sysu.processexecutionservice.admission.ActivitiExecuteAdmissionor;
import org.sysu.processexecutionservice.admission.IAdmissionor;
import org.sysu.processexecutionservice.admission.queuecontext.LinkedBlockingDelayQueueContext;
import org.sysu.processexecutionservice.admission.requestcontext.ActivitiExecuteRequestContext;
import org.sysu.processexecutionservice.admission.requestcontext.IRequestContext;

//基于延迟队列评分的准入算法
//
public class BaseQueueScoreRule extends AbstractAdmissionRule {

    private ActivitiExecuteAdmissionor activitiExecuteAdmissionor;
    private int rtl1 = 2; //表示rtl 1下最多延迟2t，也就是最多就放到第二个延迟队列上
    private int rtl2 = 4;


    public BaseQueueScoreRule() {

    }

    public BaseQueueScoreRule(IAdmissionor admissionor) {
        this();
        this.activitiExecuteAdmissionor = (ActivitiExecuteAdmissionor) admissionor;
        setAdmissionor(admissionor);
    }

    @Override
    public void admit(IRequestContext requestContext) {
        ActivitiExecuteRequestContext activitiExecuteRequestContext = (ActivitiExecuteRequestContext) requestContext;
        //计算之后放入到相应的队列中;
        //因为目前是调度层的消费大于准入层的生产，所以不用考虑执行队列

        if(activitiExecuteRequestContext.getRtl().equals("0")) {
           this.activitiExecuteAdmissionor.getExecuteQueueContext().offer(requestContext);
        } else if(activitiExecuteRequestContext.getRtl().equals("1")) {
//            rtl等于1表示最多延迟2t的时长;
            int index = calculateIndex(rtl1);
            this.activitiExecuteAdmissionor.getDelayQueueContexts()[index].offer(requestContext);
        } else if(activitiExecuteRequestContext.getRtl().equals("2")) {
            int index = calculateIndex(rtl2);
            this.activitiExecuteAdmissionor.getDelayQueueContexts()[index].offer(requestContext);
        }
    }

    public int calculateIndex(int index) {
        double maxScore = Double.MIN_VALUE;
        int maxIndex = -1;
        double score;
        for(int i = 0; i < index; i++) {
            //i+1是因为执行队列才是0，延迟队列是从1开始
            score = calculateScore((LinkedBlockingDelayQueueContext) this.activitiExecuteAdmissionor.getDelayQueueContexts()[i], i + 1);
            if(maxScore < score) {
                maxScore = score;
                maxIndex = index;
            }
        }
        return maxIndex;
    }

    private double calculateScore(LinkedBlockingDelayQueueContext linkedBlockingDelayQueueContext, int index) {
        int size = linkedBlockingDelayQueueContext.getDelayQueues().size();
        return (this.activitiExecuteAdmissionor.getAverageHistoryRequestNumber() - size) * (1.0 / (index + 1));
    }

    public String getDelayQueueContextClassName() {
        return "LinkedBlockingDelayQueueContext";
    }

    public String getExecuteQueueClassName() {
        return "LinkedBockingExecuteQueueContext";
    }

}
