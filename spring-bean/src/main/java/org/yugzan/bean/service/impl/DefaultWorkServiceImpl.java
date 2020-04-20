package org.yugzan.bean.service.impl;
/**
 * @author yongzan
 *
 * @date 2020年4月20日
 */

import org.springframework.beans.factory.InitializingBean;
import org.yugzan.bean.service.IWrokService;

public class DefaultWorkServiceImpl implements IWrokService, InitializingBean {

  @Override
  public void goCompany(String name) {
    System.out.println(name + "搭公車去公司");
  }

  @Override
  public void working(String name) {
    System.out.println(name + "正在趕工中");
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    System.out.println("啟動:" + this.getClass().getSimpleName());
  }

}
