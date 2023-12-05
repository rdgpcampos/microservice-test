package com.eurekaserver.servicebalancer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServicebalancerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicebalancerApplication.class, args);
	}

}
