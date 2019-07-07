/**
 * 
 */
package com.vidyo.framework.swagger;

import java.util.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.service.VendorExtension;

/**
 * @author ganesh
 *
 */
@Configuration
@ComponentScan
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.vidyo.rest.controllers")).build().apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() {
		Collection<VendorExtension> vendorExtensions = Collections.EMPTY_LIST;
		Contact contact = new Contact("", "", ""); 
		ApiInfo apiInfo = new ApiInfo("Services", "REST based Services ", "v1", "http://a.com", contact, "Terms of Services", "terms_content.html", vendorExtensions);
		return apiInfo;
	}
}
