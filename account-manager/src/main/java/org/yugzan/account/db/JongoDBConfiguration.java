package org.yugzan.account.db;

import java.net.UnknownHostException;

import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

/**
 * @author yongzan
 * @date 2015/8/21
 * 
 */


@Configuration
public class JongoDBConfiguration {
	private static final Logger logger = LoggerFactory.getLogger(JongoDBConfiguration.class);
	
	@Value("${jongo.host}")
	public String host;
	@Value("${jongo.port}")
	public int port = 27017;
	@Value("${jongo.dbname}")
	public String dbname;
	@Value("${jongo.dbcollection}")
	public String dbcollection;

	@Bean
	public Jongo jongo() {
		DB db;
		try {
			db = new MongoClient(host, port).getDB(dbname);
			logger.info("HOST:{},PORT:{}", host, port);
		} catch (UnknownHostException e) {
			throw new MongoException("Connection error : ", e);
		}
		return new Jongo(db);
	}
	
	@Bean
	public MongoCollection users() {
	    return jongo().getCollection(dbcollection);
	}
}
