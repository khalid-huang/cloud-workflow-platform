package org.sysu.bpmprocessenginesportal.admission;


import org.sysu.bpmprocessenginesportal.admission.queuecontext.IQueueContext;
import org.sysu.bpmprocessenginesportal.admission.queuecontext.LinkedBlockingDelayQueueContext;

import java.io.FileWriter;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

//用于给ActivitiExecuteAdmissionor做一些更新工作，比如更新historySize，写入延迟队列的长度，记录波形
public class ActivitiExecuteAdmissionorUpdater {
//    用于记录波形的计数器
    LongAdder originalWaveFormCounter;
    LongAdder smoothWaveFormCounter;

    FileWriter writerForOriginalWaveForm;
    FileWriter writerForSmoothWaveForm;
    FileWriter writerForDelayQueuesSize;

    ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);

    Boolean flag =  false; //开始写的标志；当有加数之后地开始写;

    private ActivitiExecuteAdmissionor activitiExecuteAdmissionor;

    public ActivitiExecuteAdmissionorUpdater(String fileNameForOriginalWaveForm,
            String fileNameForSmoothWaveForm, String fileNameForDelayQueuesSize, ActivitiExecuteAdmissionor activitiExecuteAdmissionor) {
        this.activitiExecuteAdmissionor = activitiExecuteAdmissionor;
        try {
            originalWaveFormCounter = new LongAdder();
            smoothWaveFormCounter = new LongAdder();
            writerForOriginalWaveForm = new FileWriter(fileNameForOriginalWaveForm);
            writerForSmoothWaveForm = new FileWriter(fileNameForSmoothWaveForm);
            writerForDelayQueuesSize = new FileWriter(fileNameForDelayQueuesSize);
            ActivitiExecuteAdmissionorUpdater.Task task = new ActivitiExecuteAdmissionorUpdater.Task();
            scheduledThreadPoolExecutor.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
        } catch (Exception e) {
        }
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public void increaseOriginalWaveFormCounter() {
        this.originalWaveFormCounter.increment();
    }


    public void increaseSmoothWaveFormCounter() {
        this.smoothWaveFormCounter.increment();
    }

    private class Task implements Runnable {
        ActivitiExecuteAdmissionor activitiExecuteAdmissionor = ActivitiExecuteAdmissionorUpdater.this.activitiExecuteAdmissionor;

        FileWriter writerForOriginalWaveForm = ActivitiExecuteAdmissionorUpdater.this.writerForOriginalWaveForm;
        FileWriter writerForSmoothWaveForm = ActivitiExecuteAdmissionorUpdater.this.writerForSmoothWaveForm;
        FileWriter writerForDelayQueuesSize = ActivitiExecuteAdmissionorUpdater.this.writerForDelayQueuesSize;

        LongAdder originalWaveFormCounter = ActivitiExecuteAdmissionorUpdater.this.originalWaveFormCounter;
        LongAdder smoothWaveFormCounter = ActivitiExecuteAdmissionorUpdater.this.smoothWaveFormCounter;

        @Override
        public void run() {
            try {
                if(!flag) {
                    return;
                }

//                更新ActivitiExecuteAdmissionor的averageHistoryRequestNumber
                this.activitiExecuteAdmissionor.computerAverageHistoryRequestNumber(smoothWaveFormCounter.intValue());
//                写入原始请求波形计数
                writerForOriginalWaveForm.write(originalWaveFormCounter.toString() + "\r\n");
                writerForOriginalWaveForm.flush();
                originalWaveFormCounter.reset();

//                写入平滑请求波形计数
                writerForSmoothWaveForm.write(smoothWaveFormCounter.toString() + "\r\n");
                writerForSmoothWaveForm.flush();
                smoothWaveFormCounter.reset();

//                记录当前四个延迟队列的大小
                if(activitiExecuteAdmissionor.getUsingRule().equals("BaseQueueScoreRule")) {
                    String sizeStr = "";
                    for (IQueueContext queueContext : activitiExecuteAdmissionor.getDelayQueueContexts()) {
                        LinkedBlockingDelayQueueContext temp = (LinkedBlockingDelayQueueContext) queueContext;
                        sizeStr += temp.getDelayQueue().size() + " ";
                    }
                    writerForDelayQueuesSize.write(sizeStr + "\r\n");
                    writerForDelayQueuesSize.flush();
                }
            } catch (Exception e) {

            }
        }
    }
}
