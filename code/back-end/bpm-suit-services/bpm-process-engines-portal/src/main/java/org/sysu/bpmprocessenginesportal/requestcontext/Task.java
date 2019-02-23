package org.sysu.bpmprocessenginesportal.requestcontext;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;

public class Task implements Callable<ResponseEntity<String>> {
    private RequestMethod method;
    private String url;
    private MultiValueMap<String, Object> variables;
    private RestTemplate restTemplate;

    public Task(RequestMethod method, String url, MultiValueMap<String, Object> variables, RestTemplate restTemplate) {
        this.method = method;
        this.url = url;
        this.variables = variables;
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<String> call() throws Exception {
        ResponseEntity<String> result = null;
        switch (this.method) {
            case GET:
                result =  this.restTemplate.getForEntity(this.url, String.class);
                break;
            case POST:
                result = this.restTemplate.postForEntity(this.url, this.variables, String.class);
                break;
        }
        return result;
    }
}
