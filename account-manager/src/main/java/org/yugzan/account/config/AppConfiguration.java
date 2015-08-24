package org.yugzan.account.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author yongzan
 * @date 2015/8/24
 * 
 */

public class AppConfiguration {
	private static final Logger logger = LoggerFactory.getLogger(AppConfiguration.class);

	@Bean(name = "objectMapper")
	@Primary
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper
				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false)
				.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS,false)
				.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING,true)
				.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING,true)
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false).findAndRegisterModules();

		return objectMapper;
	}
}
