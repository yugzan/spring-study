package org.yugzan.linebot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LinebotDemoApplication implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(LinebotDemoApplication.class, args);
    }

	
    @Override
    public void run(String... args) throws Exception {

    }
}
