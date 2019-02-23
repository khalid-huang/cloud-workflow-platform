package org.sysu.bpmprocessenginesportal.requestcontext;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/** 面向限流使用的请求封装实体，使用futureTask实现阻塞 */
public class FlowLimitationRequestContext extends  AbstractRequestContext {


    public FlowLimitationRequestContext(RequestMethod method, String url, MultiValueMap<String, Object> variables, RestTemplate restTemplate) {
        this.method = method;
        this.url = url;
        this.variables = variables;
        this.restTemplate = restTemplate;
        this.futureTask = new FutureTask<>(new Task(method, url, variables, restTemplate));
    }

    public FlowLimitationRequestContext(String url, MultiValueMap<String, Object> variables, RestTemplate restTemplate,
                                        FutureTask futureTask) {
        this.url = url;
        this.variables = variables;
        this.restTemplate = restTemplate;
        this.futureTask = futureTask;
    }

}
