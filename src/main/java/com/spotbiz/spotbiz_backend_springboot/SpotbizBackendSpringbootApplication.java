package com.spotbiz.spotbiz_backend_springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpotbizBackendSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpotbizBackendSpringbootApplication.class, args);
	}

}
