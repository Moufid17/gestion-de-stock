package com.tmdigital.gestiondestock;

import org.springframework.boot.SpringApplication;

public class TestGStockApplication {

	public static void main(String[] args) {
		SpringApplication.from(GStockApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
