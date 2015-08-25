package org.test.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.yugzan.account.EnableAccountManager;


/**
 * must to load class path "org.yugzan.account" 
 * TODO ClassPathScanningCandidateComponentProvider
 * */

@SpringBootApplication
@ComponentScan("org.yugzan.account")
@EnableAccountManager(resourceUri = {"/org/**"} ,staticContent = {"classpath:/web/" }  )
public class AccountManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountManagerApplication.class, args);
    }
}
