package com.tmdigital.gestiondestock;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

	@Bean
	@ServiceConnection
	MySQLContainer<?> mysqlContainer() {
		return new MySQLContainer<>(DockerImageName.parse("mysql:latest"));
		// MySQLContainer<?> mysql = new MySQLContainer<>(DockerImageName.parse("mysql:latest"))
		// 	.withDatabaseName("gstock")
		// 	.withUsername("mysql")
		// 	.withPassword("userpassword");
				
		// // Définir les propriétés de connexion JDBC dynamiquement dans le système
		// System.setProperty("spring.datasource.url", mysql.getJdbcUrl());
		// System.setProperty("spring.datasource.username", mysql.getUsername());
		// System.setProperty("spring.datasource.password", mysql.getPassword());
		// System.setProperty("spring.datasource.driver-class-name", "com.mysql.cj.jdbc.Driver");
		// System.setProperty("spring.jpa.database-platform", "org.hibernate.dialect.MySQL8Dialect");
				
		// mysql.start();
		// return mysql;
	}

}
