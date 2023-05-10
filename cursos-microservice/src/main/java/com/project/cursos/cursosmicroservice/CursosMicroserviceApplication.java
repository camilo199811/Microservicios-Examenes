package com.project.cursos.cursosmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
@EntityScan({
			"com.project.cursos.cursosmicroservice.models.entity",
			"com.project.commonsexamenes.models.entity"})
public class CursosMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CursosMicroserviceApplication.class, args);
	}

}
