package com.news_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class NewsManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsManagementApplication.class, args);
	}

}
