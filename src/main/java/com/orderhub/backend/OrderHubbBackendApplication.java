package com.orderhub.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderHubbBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderHubbBackendApplication.class, args);
		System.out.println("Hello OrderHubb Backend is running!");
	}

}
