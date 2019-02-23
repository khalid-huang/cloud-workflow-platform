package org.sysu.bpmprocessenginesportal.requestcontext;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/** 请求上下文封装实体，基于FutrureTask实现阻塞 */
public abstract class AbstractRequestContext implements IRequestContext {

    //请求方法
    protected RequestMethod method;
    // 请求地址
    protected String url;
    //请求参数
    protected MultiValueMap<String, Object> variables;
    //请求对应的futureTask
    protected FutureTask<ResponseEntity<String>> futureTask;
    //请求对应的restTemplate，用于发送请求
    protected RestTemplate restTemplate;

    public RequestMethod getMethod() {
        return method;
    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MultiValueMap<String, Object> getVariables() {
        return variables;
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

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
