package org.test.account;

import org.springframework.boot.SpringApplication;
import org.yugzan.account.AccountManagerApplication;
import org.yugzan.account.UseBasicDB;
import org.yugzan.account.UseDBClassPath;
import org.yugzan.account.UseMongoDB;

/**
 * must to load class path "org.yugzan.account" 
 * TODO ClassPathScanningCandidateComponentProvider
 */
//@UseBasicDB
@UseMongoDB
@AccountManagerApplication(
		basePackages = { "org.test.account", UseDBClassPath.MONGO}, 
		resourceUri = 	{"/myuri/**" }, 
		staticContent = { "classpath:/web/" })
public class CoustomApplicationTest {
	public static void main(String[] args) {
		SpringApplication.run(CoustomApplicationTest.class, args);
	}
}
