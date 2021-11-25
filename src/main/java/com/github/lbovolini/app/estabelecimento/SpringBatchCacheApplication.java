package com.github.lbovolini.app.estabelecimento;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableBatchProcessing
@EnableScheduling
@EnableAutoConfiguration(exclude = {
		RedisRepositoriesAutoConfiguration.class,
})
public class SpringBatchCacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchCacheApplication.class, args);
	}

}
