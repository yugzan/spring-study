package org.yugzan.bean;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.yugzan.bean.service.ISleepService;
import org.yugzan.bean.service.IWrokService;


@SpringBootApplication
public class Application implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Autowired
  private ApplicationContext context;
  @Autowired
  private IWrokService workService;
  @Autowired
  private ISleepService sleepService;

  @Override
  public void run(String... args) throws Exception {
    // 觀察 啟動的 bean
    String[] beans = context.getBeanDefinitionNames();
//    Arrays.sort(beans);
    for (String name : beans) {
      System.out.println(name);
    }
    System.out.println("You already start (" + beans.length + ") beans.");

    // 操作服務
    String name = "蔡補蛋";
    workService.goCompany(name);
    workService.working(name);
    sleepService.doSleep(name);

    // 關閉app
    ((ConfigurableApplicationContext) context).close();
  }

}
