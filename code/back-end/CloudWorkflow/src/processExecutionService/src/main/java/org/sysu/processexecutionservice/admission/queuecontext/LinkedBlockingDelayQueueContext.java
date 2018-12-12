package org.sysu.processexecutionservice.admission.queuecontext;

import org.sysu.processexecutionservice.admission.IAdmissionor;
import org.sysu.processexecutionservice.admission.requestcontext.ActivitiExecuteRequestContext;
import org.sysu.processexecutionservice.admission.requestcontext.IRequestContext;

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingDelayQueueContext extends AbstractDelayQueueContext {

//    过时之后的请求的去向；
    private IQueueContext nextQueueContext;

    private IAdmissionor admissionor;

    public LinkedBlockingDelayQueueContext(int minDelayTime, int maxDelayTime, int timeSlice, IQueueContext nextQueueContext){
        this.delayQueue = new LinkedBlockingQueue<IRequestContext>();
        this.minDelayTime = minDelayTime;
        this.maxDelayTime = maxDelayTime;
        this.nextQueueContext = nextQueueContext;
        this.timeSlice = timeSlice;
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
        return this.delayQueue.poll();
    }

    @Override
    public void offer(IRequestContext requestContext) {
        this.delayQueue.offer(requestContext);
    }

    @Override
    public IRequestContext peek() {
        return this.delayQueue.peek();
    }

    //    启动一个线程进行请求检查，准入
//    是用定时任务每毫秒检查所有符合的好，还是线程一直跑的好呢？
    public void startCheck() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LinkedBlockingQueue<IRequestContext> delayQueue = (LinkedBlockingQueue<IRequestContext>) LinkedBlockingDelayQueueContext.this.delayQueue;
                int minDelayTime = LinkedBlockingDelayQueueContext.this.minDelayTime;
                long remain;
                while(true) {
                    if(!delayQueue.isEmpty()) {
                        ActivitiExecuteRequestContext activitiExecuteRequestContext = (ActivitiExecuteRequestContext) delayQueue.peek();
                        // 表示队头请求已经不属于这个时间段了
                        remain = (System.currentTimeMillis() + minDelayTime) - activitiExecuteRequestContext.getStartTime();
                        if(remain <= 0) {
                            delayQueue.poll();
                            nextQueueContext.offer(activitiExecuteRequestContext);
                        } else {
                            try {
                                Thread.sleep(remain);//减少内存占用
                            } catch (InterruptedException e) {
                                System.out.println(e);
                            }
                        }
                    } else {
                        try {
                            Thread.sleep(LinkedBlockingDelayQueueContext.this.timeSlice); //如果当前为空的话，最多可以休眠这么长的时间
                        } catch (InterruptedException e) {
                            System.out.println(e);
                        }
                    }
                }
            }
        }).start();
    }
}
