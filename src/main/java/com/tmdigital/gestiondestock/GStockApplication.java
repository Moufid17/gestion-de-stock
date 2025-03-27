package com.tmdigital.gestiondestock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // Pour activer l'auditing
public class GStockApplication {

	public static void main(String[] args) {
		SpringApplication.run(GStockApplication.class, args);
	}

}
