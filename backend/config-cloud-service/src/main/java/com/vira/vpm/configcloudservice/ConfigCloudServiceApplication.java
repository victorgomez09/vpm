package com.vira.vpm.configcloudservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigCloudServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigCloudServiceApplication.class, args);
	}

}
