package org.yugzan.amqp.core;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.yugzan.amqp.model.Item;

/**
 * @author yongzan
 * @date 2018/11/05
 */
@Service
@EnableScheduling
public class PublishTask {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Scheduled(cron = "0/10 * * * * ?", zone = "Asia/Taipei")
  public void sendMessage() {

    Item item = new Item("Orange");
    rabbitTemplate.convertAndSend("exchange-dev", "dev.test", item);
    System.out.println(">Publish item:" + item.toString());
  }
}
