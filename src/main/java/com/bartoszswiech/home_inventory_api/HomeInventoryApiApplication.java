package com.bartoszswiech.home_inventory_api;

import com.bartoszswiech.home_inventory_api.beans.CONTAINER_TYPE;
import com.bartoszswiech.home_inventory_api.beans.Product;
import com.bartoszswiech.home_inventory_api.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HomeInventoryApiApplication {

	private static final Logger log = LoggerFactory.getLogger(HomeInventoryApiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(HomeInventoryApiApplication.class, args);
	}

}
