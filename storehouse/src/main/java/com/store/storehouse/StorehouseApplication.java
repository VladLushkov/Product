package com.store.storehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class StorehouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(StorehouseApplication.class, args);
	}

}
