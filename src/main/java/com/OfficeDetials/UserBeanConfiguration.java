package com.OfficeDetials;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class UserBeanConfiguration {

	@Bean
	public RestTemplate methodName()
	{
		return new RestTemplate();
	}
}
