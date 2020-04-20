package org.yugzan.bean.service.impl;

import org.springframework.beans.factory.InitializingBean;
import org.yugzan.bean.service.ISleepService;

/**
 * @author yongzan
 *
 * @date 2020年4月20日
 */
public class DefaultSleepServiceImpl implements ISleepService, InitializingBean {

  @Override
  public void doSleep(String name) {
    System.out.println(name + "睡覺中");
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    System.out.println("啟動:" + this.getClass().getSimpleName());
  }

}
