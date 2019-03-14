package org.sysu.bpmprocessenginesportal.admission.responsetimeadmission.queuecontext;


import org.sysu.bpmprocessenginesportal.requestcontext.IRequestContext;

import java.util.Queue;

public abstract class AbstractDelayQueueContext implements IQueueContext {
//    这两个参数代表了这个延迟队列的延迟的时段，所有符合这个时间段的请求都会在这个队列中；其实本质只需要一个最小的就可以了
//    因为最大的就是下一个延迟队列的最小
//    单位都是毫秒
    protected int minDelayTime;
    protected int maxDelayTime;
    protected int timeSlice;

    protected Queue<IRequestContext> delayQueue;

    public Queue<IRequestContext> getDelayQueue() {
        return delayQueue;
    }

    public void setDelayQueues(Queue<IRequestContext> delayQueue) {
        this.delayQueue = delayQueue;
    }
}
