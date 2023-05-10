package ssis.runt.com.co.eurekaServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class MsSsisRuntEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsSsisRuntEurekaServerApplication.class, args);
	}

}
