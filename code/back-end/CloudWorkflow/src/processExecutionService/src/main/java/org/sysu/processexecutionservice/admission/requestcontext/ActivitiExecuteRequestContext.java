package org.sysu.processexecutionservice.admission.requestcontext;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.sysu.processexecutionservice.admission.ActivitiExecuteAdmissionor;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

//存放使用restTemplate执行completeTask和startProcess需要的参数信息
//维护一个futureTask，用于获取执行的结果
public class ActivitiExecuteRequestContext implements IRequestContext{
    private String rtl;

    private String url;

    private Map<String, Object> variables;

    private FutureTask<ResponseEntity<String>> futureTask;

    private RestTemplate restTemplate;

// 开始执行请求的时间
    private long startTime;

    public ActivitiExecuteRequestContext(String rtl, String url, Map<String, Object> variables, RestTemplate restTemplate) {
        this.rtl = rtl;
        this.url = url;
        this.variables= variables;
        this.restTemplate = restTemplate;
        futureTask = new FutureTask<>(new Task(this));
    }

    public String getRtl() {
        return rtl;
    }

    public void setRtl(String rtl) {
        this.rtl = rtl;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    public FutureTask<ResponseEntity<String>> getFutureTask() {
        return futureTask;
    }

    public void setFutureTask(FutureTask<ResponseEntity<String>> futureTask) {
        this.futureTask = futureTask;
    }

    class Task implements Callable<ResponseEntity<String>> {
        private IRequestContext requestContext;

        public Task(IRequestContext requestContext) {
            this.requestContext = requestContext;
        }

        @Override
        public ResponseEntity<String> call() throws Exception {
            ActivitiExecuteRequestContext activitiExecuteRequestContext = (ActivitiExecuteRequestContext) this.requestContext;
            return ActivitiExecuteRequestContext.this.restTemplate.postForEntity(activitiExecuteRequestContext.getUrl(),activitiExecuteRequestContext.getVariables(), String.class);
        }
    }
}
