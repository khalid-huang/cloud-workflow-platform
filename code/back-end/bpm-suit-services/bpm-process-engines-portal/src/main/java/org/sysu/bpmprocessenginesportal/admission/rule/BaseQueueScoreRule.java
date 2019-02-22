package org.sysu.bpmprocessenginesportal.admission.rule;


import org.sysu.bpmprocessenginesportal.admission.ActivitiExecuteAdmissionor;
import org.sysu.bpmprocessenginesportal.admission.IAdmissionor;
import org.sysu.bpmprocessenginesportal.admission.queuecontext.LinkedBlockingDelayQueueContext;
import org.sysu.bpmprocessenginesportal.admission.requestcontext.ActivitiExecuteRequestContext;
import org.sysu.bpmprocessenginesportal.admission.requestcontext.IRequestContext;

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
        } else {
            int index = calculateIndex(activitiExecuteRequestContext.getRtl());
            long expectExecuteTime = activitiExecuteRequestContext.getStartTime() + (index + 1) * this.activitiExecuteAdmissionor.getTimeSlice();
            activitiExecuteRequestContext.setExpectExecuteTime(expectExecuteTime);
            this.activitiExecuteAdmissionor.getDelayQueueContexts()[index].offer(requestContext);
        }
    }

    public int calculateIndex(String rtl) {
        int index = 0;
        if(rtl.equals("1")) {
            index = rtl1; //rtl表示最多延迟2个时间片
        } else if(rtl.equals("2")) {
            index = rtl2;
        }
        double maxScore = Double.NEGATIVE_INFINITY;
        int maxIndex = -1;
        double score;
        for(int i = 0; i < index; i++) {
            //i+1是因为执行队列才是0，延迟队列是从1开始
            score = calculateScore((LinkedBlockingDelayQueueContext) this.activitiExecuteAdmissionor.getDelayQueueContexts()[i], i + 1);
            if(maxScore < score) {
                maxScore = score;
                maxIndex = i;
            }
            System.out.println("score + i: " + score + "+" + i);
        }
        System.out.println("maxIndex: " + maxIndex);
        return maxIndex;
    }

    private double calculateScore(LinkedBlockingDelayQueueContext linkedBlockingDelayQueueContext, int index) {
        int size = linkedBlockingDelayQueueContext.getDelayQueue().size();
        return (this.activitiExecuteAdmissionor.getAverageHistoryRequestNumber() - size) * (1.0 / (index + 1));
    }

    public String getDelayQueueContextClassName() {
        return "LinkedBlockingDelayQueueContext";
    }

    public String getExecuteQueueClassName() {
        return "LinkedBockingExecuteQueueContext";
    }

}
