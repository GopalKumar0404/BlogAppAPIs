package com.gopal.blog.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	public static final String AUTHORIZATION_HEADER = "Authorization";
	private String title="Blogging Application : Backend Course";
	private String description = "This project is developed by Gopal with help of learn code with Durgesh";
	private String version="1.0";
	private String termsOfService="Terms of Service";
	private String apiLicense="Api License";
	private String apiLicenseUrls="Api License Urls";
	private Contact contact;
	
	@Bean
	public Docket apiDocumentation() {

		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getinfo())
				.securityContexts(securityContexts())
				.securitySchemes(Arrays.asList(apiKey()))
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}

	private List<SecurityContext> securityContexts() {
		
		return Arrays.asList(SecurityContext.builder().securityReferences(securityReferences()).build());
	}

	private List<SecurityReference> securityReferences() {
		// TODO Auto-generated method stub
		AuthorizationScope scopes = new AuthorizationScope("global", "accessEverything");
		return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[] {scopes}));
	}

	private ApiKey apiKey() {
		// TODO Auto-generated method stub
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
	}

	private ApiInfo getinfo() {
		contact = new Contact("Gopal","http://localhost:8080/swagger-ui/index.html","GopalKumar24680@gmail.com");
		return new ApiInfo(title, description, version, termsOfService, contact,apiLicense,apiLicenseUrls, Collections.emptyList());
	}
}
