package es.iesfranciscodelosrios.ryg.config;

import java.io.File;
import java.io.InputStream;
import java.net.URLStreamHandler;
import java.time.LocalDate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {

		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("es.iesfranciscodelosrios.ryg")).paths(PathSelectors.any())
				.build().pathMapping("/").directModelSubstitute(LocalDate.class, String.class)
				.genericModelSubstitutes(ResponseEntity.class).useDefaultResponseMessages(false)
				.ignoredParameterTypes(InputStream.class).ignoredParameterTypes(File.class)
				.ignoredParameterTypes(URLStreamHandler.class).ignoredParameterTypes(java.net.URL.class)
				.ignoredParameterTypes(java.net.URI.class);
	}

}
