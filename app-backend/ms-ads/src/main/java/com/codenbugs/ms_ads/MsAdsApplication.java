package com.codenbugs.ms_ads;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsAdsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAdsApplication.class, args);
	}

}
