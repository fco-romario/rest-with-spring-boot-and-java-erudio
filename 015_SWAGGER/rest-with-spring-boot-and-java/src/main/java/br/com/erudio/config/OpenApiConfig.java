package br.com.erudio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
	
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
					.title("RESTfull API with Java 22 and Spring 3")
					.version("v1")
					.description("Some description about your API")
					.termsOfService("Link termos e serviçõs")
					.license(
						new License()
							.name("Apache 2.0")
							.url("http://localhost:8080/api/person/v1")
						)
					);
				
	}
}
