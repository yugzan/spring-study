package org.yugzan.amqp;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ch.qos.logback.classic.Logger;

@SpringBootApplication
public class SpringRabbitMqApplication{

  private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
  
    public static void main(String[] args) {
        
        SpringApplication.run(SpringRabbitMqApplication.class, args);
    }

}
