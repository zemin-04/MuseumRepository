package com.zhongda.museum.config;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ComponentScan(basePackages = { "com.zhongda.museum.controller" })
public class SwaggerConfig {

	@Bean
	public Docket data() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.groupName("data")
				.select()
				.apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
				.apis(RequestHandlerSelectors
						.withMethodAnnotation(ApiOperation.class))
				.paths(PathSelectors.ant("/**")).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("张家界博物馆语音导览公众号网页rest api接口")
				.contact(
						new Contact("罗杰", "http://localhost:8100/museum",
								"1250368725@qq.com"))
				.description("HTTP对外开放rest接口").version("2.7.0").build();
	}
}