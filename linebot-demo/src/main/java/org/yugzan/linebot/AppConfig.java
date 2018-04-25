package org.yugzan.linebot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class AppConfig {

	@Bean
	@Primary
	public ObjectMapper objectMapper() {
		return ObjectMapperHolder.INSTANCE.get();
	}

	// @Bean
	// @Primary
	// public LineMessagingClient
	// buildLineClient(@Value("${line.bot.channel-token}") String token) {
	// //custom client
	// return LineMessagingClient.builder(token).build();
	// }
}
