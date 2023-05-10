package ssis.runt.com.co.gateway.msssisruntgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MsSsisRuntGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsSsisRuntGatewayApplication.class, args);
	}

}
