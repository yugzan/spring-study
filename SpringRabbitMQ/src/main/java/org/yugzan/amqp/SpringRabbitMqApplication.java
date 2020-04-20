package org.yugzan.amqp;

import java.util.stream.Stream;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableRabbit
public class SpringRabbitMqApplication {

  public static void main(String[] args) {

    ConfigurableApplicationContext context =
        SpringApplication.run(SpringRabbitMqApplication.class, args);

    Long count = Stream.of(context.getBeanDefinitionNames())
        .sorted()
        .map(m -> {
      System.out.println(m);
      return m;
    }).count();
    System.out.println(String.format("Bean(%d)", count));
  }

}
