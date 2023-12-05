package com.gateway.demo;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
					.route(p -> p.path("/rdgpcampos/bankaccount/**")
					.filters(f -> f.rewritePath("/rdgpcampos/bankaccount/(?<segment>.*)","/${segment}")
						.addResponseHeader("X-Response-Time",new Date().toString()))
					.uri("lb://BANKACCOUNT"))
					.route(p -> p.path("/rdgpcampos/loan/**")
					.filters(f -> f.rewritePath("/rdgpcampos/loan/(?<segment>.*)","/${segment}")
						.addResponseHeader("X-Response-Time",new Date().toString()))
					.uri("lb://LOAN"))
					.route(p -> p.path("/rdgpcampos/cards/**")
					.filters(f -> f.rewritePath("/rdgpcampos/cards/(?<segment>.*)","/${segment}")
						.addResponseHeader("X-Response-Time",new Date().toString()))
					.uri("lb://CARDS")).build();
	}
}
