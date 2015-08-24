package org.yugzan.account.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;

/**
 * @author yongzan
 * @date 2015/8/24
 * 
 */

@Configuration
@EnableMongoRepositories(basePackages = "spring.security.boot.mongodb.repo")
public class MongoDBConfiguration extends AbstractMongoConfiguration {

	@Value("${mongo.host}")
	private String host;

	@Value("${mongo.port}")
	private int port;
	
	@Value("${mongo.dbname}")
	private String dbname;

	@Override
	protected String getDatabaseName() {
		return dbname;
	}

	@Override
	public Mongo mongo() throws Exception {
		MongoClient client = new MongoClient(host, port);
		client.setWriteConcern(WriteConcern.SAFE);
		return client;
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongo(), getDatabaseName());
	}
}
