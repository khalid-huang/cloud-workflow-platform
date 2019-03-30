package org.sysu.bpmmanagementservice;

import org.activiti.engine.impl.persistence.StrongUuidGenerator;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.sysu.bpmmanagementservice.config.activiti.modeler.explorer.JsonpCallbackFilter;
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

	//解决activiti的id在高并发下主键冲突的问题
	@Bean
	public StrongUuidGenerator generator() {
		return new StrongUuidGenerator();
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
