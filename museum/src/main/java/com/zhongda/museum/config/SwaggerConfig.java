package com.zhongda.museum.config;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.zhongda.museum.utils.TokenUtils;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
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
				.globalOperationParameters(setHeaderToken())
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
	
	private List<Parameter> setHeaderToken() {
		ParameterBuilder tokenParam = new ParameterBuilder();
		List<Parameter> paramList = new ArrayList<Parameter>();
		tokenParam.name("Authorization")
				.defaultValue(
						TokenUtils.TOKEN_HEADER_PREFIX
								+ " eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1MzQwODA4MzYsImV4cCI6MTUzNDY4NTYzNiwib3BlbmlkIjoib29sVUIxZXJhS2dvU296TENIdFhjVWtCVzhZQSIsInVzZXJOYW1lIjoiU2tpcENsb3VkIn0.IcHuO0Wod01MMrQ4EtMqyOCKAOWZ9sZ9Eg5xXgdw0go")
				.description(
						"Token令牌( " + TokenUtils.TOKEN_HEADER_PREFIX + " 开头)").modelRef(new ModelRef("string"))
				.parameterType("header").required(false).build();
		paramList.add((Parameter) tokenParam.build());
		return paramList;
	}
}