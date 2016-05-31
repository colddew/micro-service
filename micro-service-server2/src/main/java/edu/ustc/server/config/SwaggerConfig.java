package edu.ustc.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket swaggerSpringBootPlugin() {
		// show nothing
		// return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.none()).build();
		return new Docket(DocumentationType.SWAGGER_2).groupName("micro-service-server2").useDefaultResponseMessages(false)
			.forCodeGeneration(true).pathMapping("/").select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.regex("/api/.*"))
			.build().apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfo("micro-service-server2 api", null, "1.0", null, null, null, null);
	}
}
