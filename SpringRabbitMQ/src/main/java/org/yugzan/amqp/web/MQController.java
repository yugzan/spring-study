package org.yugzan.amqp.web;


import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.yugzan.amqp.config.RabbitConfiguration;

import ch.qos.logback.classic.Logger;



@RestController
public class MQController {
  
  private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
  
  @RequestMapping(value = "/" , method = RequestMethod.GET )
  @ResponseStatus(value = HttpStatus.OK)
  public String sendMQ() {
    ApplicationContext context =
        new AnnotationConfigApplicationContext(RabbitConfiguration.class);
    AmqpTemplate template = context.getBean(AmqpTemplate.class);

    template.convertAndSend("samplequeue", "foo");

    String foo = (String) template.receiveAndConvert("samplequeue"); 
    return foo;
  }
}
