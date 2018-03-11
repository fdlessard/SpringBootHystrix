package io.fdlessard.codebites.hystrix;

import lombok.Builder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestOperations;

@SpringBootApplication
@EnableCircuitBreaker
@EnableHystrixDashboard
public class SpringBootHystrixApplication {

	@Bean("customerRestTemplate")
	public RestOperations customerRestTemplate() {
		return new RestTemplateBuilder().build();
	}

	@Bean("productRestTemplate")
	public RestOperations productRestTemplate() {
		return new RestTemplateBuilder().build();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootHystrixApplication.class, args);
	}
}
