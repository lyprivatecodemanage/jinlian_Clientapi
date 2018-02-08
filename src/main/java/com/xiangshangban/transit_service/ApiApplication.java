package com.xiangshangban.transit_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Hello world!
 *
 */
@EnableAutoConfiguration(exclude = { MultipartAutoConfiguration.class })
@EnableTransactionManagement
@SpringBootApplication
@ServletComponentScan
public class ApiApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
	
}
