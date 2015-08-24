package org.test.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yugzan.account.EnableAccountManager;

@SpringBootApplication
//@EnableAccountManager(user = "test" , pw = "test")
@EnableAccountManager(user = "test" , pw = "test", resourceUri = {"/org/**"} ,staticContent = {"classpath:/web/" }  )
public class AccountManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountManagerApplication.class, args);
    }
}
