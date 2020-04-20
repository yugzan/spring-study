package org.yugzan.bean.custom;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.yugzan.bean.service.IWrokService;

/**
 * @author yongzan
 *
 * @date 2020年4月20日
 */
@Service
public class OtherProjectCustomWorkService implements IWrokService, InitializingBean {

  @Override
  public void goCompany(String name) {
    System.out.println(name + "開車去公司");
  }

  @Override
  public void working(String name) {
    System.out.println(name + "被追殺趕工中");
  }


  @Override
  public void afterPropertiesSet() throws Exception {
    System.out.println("啟動:" + this.getClass().getSimpleName());
  }

}
