package org.sysu.bpmprocessenginesportal.admission.requestcontext;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

//存放使用restTemplate执行completeTask和startProcess需要的参数信息
//维护一个futureTask，用于获取执行的结果
public class ActivitiExecuteRequestContext implements IRequestContext{
    private String rtl;

    private String url;

    private MultiValueMap<String, Object> variables;

    private FutureTask<ResponseEntity<String>> futureTask;

    private RestTemplate restTemplate;
//    请求到达的时间
    private long startTime;

//    期望的执行时间(由)IRule计算
    private long expectExecuteTime;

    public ActivitiExecuteRequestContext(String rtl, String url, MultiValueMap<String, Object> variables, RestTemplate restTemplate) {
        this.rtl = rtl;
        this.url = url;
        this.variables= variables;
        this.restTemplate = restTemplate;
        futureTask = new FutureTask<>(new Task(this));
        this.startTime = System.currentTimeMillis();
    }

    public long getExpectExecuteTime() {
        return expectExecuteTime;
    }

    public void setExpectExecuteTime(long expectExecuteTime) {
        this.expectExecuteTime = expectExecuteTime;
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

    public MultiValueMap<String, Object> getVariables() {
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

    public void setVariables(MultiValueMap<String, Object> variables) {
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
