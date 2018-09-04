package org.yugzan.mongo;

import java.util.Arrays;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

		String[] beans = context.getBeanDefinitionNames();
		Arrays.sort(beans);
		for (String name : beans) {
			System.out.println(name);
		}
		System.out.println("Bean count(" + beans.length + ")");
	}

}
