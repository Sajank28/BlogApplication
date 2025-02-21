package com.springboot.blog;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Blog Application",
				description = "SpringBoot Blog App Rest API's documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Sajan",
						email = "sajan@gmail.com",
						url="http://localhost:8080"
				),
				license = @License(
						name = "Apache 2.0",
						url = "\"http://localhost:8080\""
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot Blog App documentation",
				url="https://github.com/Sajank28/BlogApplication"
		)
)
public class BlogApplication {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}


	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

}
