package com.github.lbovolini.app.estabelecimento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(enableDefaultTransactions = false)
public class SpringBatchCacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchCacheApplication.class, args);
	}

}
