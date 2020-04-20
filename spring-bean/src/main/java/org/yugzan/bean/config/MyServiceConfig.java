package org.yugzan.bean.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.yugzan.bean.service.ISleepService;
import org.yugzan.bean.service.IWrokService;
import org.yugzan.bean.service.impl.DefaultSleepServiceImpl;
import org.yugzan.bean.service.impl.DefaultWorkServiceImpl;

/**
 * @author yongzan
 *
 * @date 2020年4月20日
 */
@Configuration
@Order(Ordered.LOWEST_PRECEDENCE)
public class MyServiceConfig {
  @Bean
  @ConditionalOnMissingBean(IWrokService.class)
  public IWrokService defaltWrokService() {
    return new DefaultWorkServiceImpl();
  }

  @Bean
  @ConditionalOnMissingBean(ISleepService.class)
  public ISleepService defaltSleepService() {
    return new DefaultSleepServiceImpl();
  }
}
