package com.aurora.redisPoC;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RedisPoCApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisPoCApplication.class, args);
	}

}
