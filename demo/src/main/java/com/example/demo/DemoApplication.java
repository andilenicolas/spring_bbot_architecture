package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	@Autowired
	private Service<FooService, Integer> fooService;
	@Autowired
	private Service<BarService, Integer> barService;
	@Autowired
	private Service<BazService, Integer> bazService;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	ApplicationRunner init() {
		return args -> {
			fooService.methodA(); // prints "Handling type: String"
			barService.methodA(); // prints "Handling type: Integer"
			bazService.methodA(); // prints "Handling type: Integer"
		};
	}

}
