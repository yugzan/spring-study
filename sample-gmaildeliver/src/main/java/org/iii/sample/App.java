package org.iii.sample;

import javax.annotation.Resource;
import org.iii.sample.mail.MailDeliverProperty;
import org.iii.sample.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Entry data compose this app.
 */
@EnableAutoConfiguration
@ComponentScan("org.iii.sample")
public class App implements CommandLineRunner {

    @Autowired
    private MailDeliverProperty prop;
	
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	
	@Resource(name="mailservice")
	private MailService service;
	
	public void run(String... arg0) throws Exception {
		service.Init();
	}
}
