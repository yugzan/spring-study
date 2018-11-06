package org.yugzan.amqp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

/**
 * @author yongzan
 * @date 2018/11/05
 */
@Configuration
public class ApplicationConfig {

  @Bean
  public TaskScheduler taskScheduler() {
    return new ConcurrentTaskScheduler(); // single threaded by default
  }
}
