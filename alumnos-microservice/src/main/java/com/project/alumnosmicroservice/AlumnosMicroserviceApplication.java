package com.project.alumnosmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.project.commons.alumnos.commonsalumnos.models.entity"})
public class AlumnosMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlumnosMicroserviceApplication.class, args);
	}

}
