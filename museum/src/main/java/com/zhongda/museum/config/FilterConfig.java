package com.zhongda.museum.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zhongda.museum.filter.CorsFilter;

@Configuration
public class FilterConfig {

	/**
	 * 注册CorsFilter
	 */
	@Bean
	public FilterRegistrationBean corsFilterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		CorsFilter corsFilter = new CorsFilter();
		filterRegistrationBean.setFilter(corsFilter);
		filterRegistrationBean.addUrlPatterns("/*");
		filterRegistrationBean.setName("corsFilter");
		filterRegistrationBean.setOrder(1);
		return filterRegistrationBean;
	}

}
