package com.example.SpringnovablesProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


//Desactiva que configure la Base de Datos, debido a SpringSecurity
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpringnovablesProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringnovablesProjectApplication.class, args);
	}

}
