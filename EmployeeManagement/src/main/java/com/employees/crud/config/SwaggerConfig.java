package com.employees.crud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration //para decirle que es una clase de configuración
@EnableSwagger2 // habilita el módulo de swagger
public class SwaggerConfig {
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select() //inicializa un builder(patrón de diseño)
				//le pasamos las clases que va a autodocumentar, por ej el paquete que contiene los servicios o any si no sabemos donde están
				.apis(RequestHandlerSelectors.basePackage("com.employees.crud.controller")) 
				.paths(PathSelectors.any()) //paths son las rutas/url de los servicios a documentar	
				.build(); //porque es un patrón de diseño builder						 
	} 
}
