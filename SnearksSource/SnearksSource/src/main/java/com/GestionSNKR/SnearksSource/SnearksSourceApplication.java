package com.GestionSNKR.SnearksSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SnearksSourceApplication {
	public static void main(String[] args) {
		SpringApplication.run(SnearksSourceApplication.class, args);
	}
}