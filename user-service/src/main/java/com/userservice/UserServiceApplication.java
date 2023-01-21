package com.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Configuration
@EnableJpaRepositories({ "com.userservice.repository" })
@ComponentScan({ "com.userservice.controller", "com.userservice.service", "com.userservice.configuration" })
@EntityScan("com.userservice.entity")
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	// Todo: Comunicate the microservice with FeignClient
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
