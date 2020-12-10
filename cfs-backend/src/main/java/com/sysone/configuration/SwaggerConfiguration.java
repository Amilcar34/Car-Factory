package com.sysone.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {
  
  @Bean
  public Docket api(){
	return new Docket(DocumentationType.SWAGGER_2).apiInfo(getApiInfo()).select()
		.paths(PathSelectors.any()).build();
  }
  
  private ApiInfo getApiInfo(){
	Contact contact = new Contact("Amilcar Alberino Longo",
		"https://www.linkedin.com/in/amilcar-lujan-alberino-longo-563a6613b/", "alberino-l@hotmail.com");
	
	ApiInfoBuilder apiBuilder = new ApiInfoBuilder();
	return apiBuilder.description("Testeo y organizador de recursos REST.\n").title("Sys One Excercise")
		.contact(contact).version("v1").build();
  }
  
}
