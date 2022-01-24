package com.enuri.gateway;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;

import com.enuri.gateway.banner.BannerPrinter;

@RefreshScope
@EnableDiscoveryClient
@SpringBootApplication
public class ApiGatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ApiGatewayServerApplication.class);
		app.setBannerMode(Mode.OFF);
		ConfigurableApplicationContext context = app.run(args);
	    app.setBannerMode(Mode.CONSOLE);
		new BannerPrinter(context).print(ApiGatewayServerApplication.class);
	}
}
