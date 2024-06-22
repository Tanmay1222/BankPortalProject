package com.project.debtCollectionManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.project.*")
@EntityScan(basePackages = "com.project.entities")
@EnableJpaRepositories(basePackages = "com.project.repositories")
@EnableDiscoveryClient(autoRegister = true)
public class DebtCollectionManagement1Application {

	public static void main(String[] args) {
		SpringApplication.run(DebtCollectionManagement1Application.class, args);
	}

}
