package org.sysu.bpmprocessenginesportal.admission.queuecontext;

import org.sysu.bpmprocessenginesportal.admission.IAdmissionor;
import org.sysu.bpmprocessenginesportal.requestcontext.IRequestContext;

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingExecuteQueueContext extends AbstractExecuteQueueContext {

    private IAdmissionor admissionor;

    public LinkedBlockingExecuteQueueContext(IAdmissionor admissionor) {
        this.admissionor = admissionor;
        this.executeQueue = new LinkedBlockingQueue<IRequestContext>();
    }

    @Override
    public IAdmissionor getAdmission() {
        return this.admissionor;
    }

    @Override
    public void setAdmission(IAdmissionor admission) {
        this.admissionor = admission;
    }

    @Override
    public IRequestContext poll() {
        return this.executeQueue.poll();
    }

    @Override
    public void offer(IRequestContext requestContext) {
        this.executeQueue.offer(requestContext);
//      这里需要接到一个调度器去，也就是生产消费模型;
//       目前的算法是生产速率一定小于消费速率的情况，所以即来即处理就可以了
        this.admissionor.dispatch(this.executeQueue.peek());
        this.executeQueue.poll();
    }

    @Override
    public IRequestContext peek() {
        return this.executeQueue.peek();
    }


}
