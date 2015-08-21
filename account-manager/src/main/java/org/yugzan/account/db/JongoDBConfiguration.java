package org.yugzan.account.db;

import java.net.UnknownHostException;

import org.jongo.Jongo;
import org.jongo.MongoCollection;
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

	public String host = "127.0.0.1";

	public int port = 27017;

	public String dbname = "test";
	
	public String dbcollection = "users";

	@Bean
	public Jongo jongo() {
		DB db;
		try {
			db = new MongoClient(host, port).getDB(dbname);
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
