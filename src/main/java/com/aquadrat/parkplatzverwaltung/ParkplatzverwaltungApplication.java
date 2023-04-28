package com.aquadrat.parkplatzverwaltung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ParkplatzverwaltungApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkplatzverwaltungApplication.class, args);
	}

}
