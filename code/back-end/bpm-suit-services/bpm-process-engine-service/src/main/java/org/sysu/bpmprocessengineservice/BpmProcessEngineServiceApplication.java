package org.sysu.bpmprocessengineservice;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.sysu.bpmprocessengineservice.activiti.ext.CloudActivitiBehaviorFactory;
import org.sysu.bpmprocessengineservice.activiti.ext.CloudUserTaskParseHandler;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class BpmProcessEngineServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BpmProcessEngineServiceApplication.class, args);
    }

    @Bean
    public CloudActivitiBehaviorFactory activitiBehaviorFactory() {
        return new CloudActivitiBehaviorFactory();
    }

    @Bean
    public CloudUserTaskParseHandler cloudUserTaskParseHandler() {
        return new CloudUserTaskParseHandler();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}

