package com.hukapp.service.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.hukapp.service.auth.config.DatabaseProperties;
import com.hukapp.service.auth.config.RsaKeyProperties;

@SpringBootApplication
@EnableConfigurationProperties({DatabaseProperties.class, RsaKeyProperties.class})
@EnableAspectJAutoProxy
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}
