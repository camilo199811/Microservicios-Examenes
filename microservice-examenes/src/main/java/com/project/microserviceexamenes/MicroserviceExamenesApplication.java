package com.project.microserviceexamenes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.project.commonsexamenes.models.entity"})
public class MicroserviceExamenesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceExamenesApplication.class, args);
	}

}
