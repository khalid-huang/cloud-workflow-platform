package org.sysu.bpmmanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"org.sysu.bpmmanagementservice","org.activiti"})
public class BpmManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BpmManagementServiceApplication.class, args);
	}
}
