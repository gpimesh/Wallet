package com.leovegas.wallet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.leovegas.wallet.utility.NumberGenerator;
import com.leovegas.wallet.utility.Util;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class MiscAppCfg {

	
	@Bean
	public Util utilBean() {
		return new Util();
	}
	
	@Bean
	public NumberGenerator numberGenerator() {
		
		return new NumberGenerator();
	}

	@Bean
	public Docket SwaggerConfiguration() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.ant("/wallet/**")) // look only this URL when Swagger create the API.
				.apis(RequestHandlerSelectors.basePackage("com.leovegas.wallet.controller")) // this helps to restrict other models and APIs showing in the documentation.
				.build();

	}
	    

}
