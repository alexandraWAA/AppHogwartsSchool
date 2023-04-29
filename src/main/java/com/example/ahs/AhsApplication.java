package com.example.ahs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class AhsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AhsApplication.class, args);
	}

}
