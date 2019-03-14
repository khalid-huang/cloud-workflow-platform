package org.sysu.bpmprocessenginesportal.admission.responsetimeadmission;

import java.io.FileWriter;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

//用于计算每秒钟的个数
public class Counter {
    LongAdder longAdder;
    FileWriter writer;
    ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);

    Boolean flag; //开始写的标志；当有加数之后地开始写;

    private ResponseTimeAdmissionScheduler responseTimeAdmissionScheduler;

    public Counter(String fileName, ResponseTimeAdmissionScheduler responseTimeAdmissionScheduler) {
        this.flag = false;
        this.responseTimeAdmissionScheduler = responseTimeAdmissionScheduler;
        longAdder = new LongAdder();
        try {
            writer = new FileWriter(fileName);
            Task task = new Task();
            scheduledThreadPoolExecutor.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
        } catch (Exception e) {
        }
    }

    public void increase() {
        this.flag = true;
        this.longAdder.increment();
    }

    public void reset() {
        this.longAdder.reset();
    }

    private class Task implements Runnable {
        @Override
        public void run() {
            if(!Counter.this.flag) {
                return;
            }
            try {
//                更新ActivitiExecuteAdmissionor的averageHistoryRequestNumber
                Counter.this.responseTimeAdmissionScheduler.computerAverageHistoryRequestNumber(Counter.this.longAdder.intValue());
                Counter.this.writer.write(Counter.this.longAdder.toString() + "\r\n");
//                每5秒写入硬盘,先不这样吧
                Counter.this.writer.flush();
                Counter.this.longAdder.reset();
            } catch (Exception e) {

            }
        }
    }



}
