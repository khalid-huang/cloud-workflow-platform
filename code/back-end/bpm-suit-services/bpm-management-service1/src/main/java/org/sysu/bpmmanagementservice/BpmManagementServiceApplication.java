package org.sysu.bpmmanagementservice;

import org.sysu.bpmmanagementservice.modeler.activiti.modeler.JsonpCallbackFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@ComponentScan({"org.activiti.rest.diagram", "org.sysu.bpmmanagementservice"})
@EnableAutoConfiguration(exclude = {
		org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class,
		org.activiti.spring.boot.SecurityAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration.class
})
public class BpmManagementServiceApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(BpmManagementServiceApplication.class, args);
	}

	@Bean
	public JsonpCallbackFilter filter(){
		return new JsonpCallbackFilter();
	}
}
