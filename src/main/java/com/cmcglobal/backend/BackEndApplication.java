package com.cmcglobal.backend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.stream.Collectors;
import java.util.stream.Stream;
@SecurityScheme(
		name = "Authorization",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "Bearer [token]",
		scheme = "bearer",
		in = SecuritySchemeIn.HEADER,
		description = "Access token"
)
@OpenAPIDefinition(
		info = @Info(title = "BackEnd API", version = "1.0", description = "Documentation BackEnd API v1.0"))
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class,
		UserDetailsServiceAutoConfiguration.class})
@EnableScheduling
@EnableTransactionManagement
public class BackEndApplication {

	@Value("${open-api.server}")
	private String serverUrl;

	public static void main(String[] args) {
		SpringApplication.run(BackEndApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI() {
		java.security.Security.addProvider(
				new org.bouncycastle.jce.provider.BouncyCastleProvider()
		);
		return new OpenAPI().servers(Stream.of(new io.swagger.v3.oas.models.servers.Server().url(serverUrl)).collect(Collectors.toList()));

	}

}
