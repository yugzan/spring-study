package org.yugzan.amqp.core;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.yugzan.amqp.config.RabbitConfiguration;
import org.yugzan.amqp.model.Item;

/**
 * @author yongzan
 * @date 2018/11/05
 */
@Component
@RabbitListener(queues = RabbitConfiguration.queueName)
public class ReceiverTask {

  @RabbitHandler
  public void findItem(@Payload Item message) {
    System.out.println("<Receiver item:" + message.getId());
  }

}
