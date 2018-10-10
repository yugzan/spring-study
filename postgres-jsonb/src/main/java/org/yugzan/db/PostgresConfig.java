package org.yugzan.db;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class PostgresConfig extends HikariConfig {
  private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

  @Value("${spring.datasource.url}")
  private String url;
  @Value("${spring.datasource.username}")
  private String username;
  @Value("${spring.datasource.password}")
  private String password;
  @Value("${spring.datasource.driverClassName}")
  private String driverClassName;

  @Bean
  public DataSource dataSource() {

    logger.info("postgres url: {}", url);
    logger.info("postgres username: {}", username);
    logger.info("postgres password: {}", password);

    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setDriverClassName(driverClassName);
    dataSource.setJdbcUrl(url);
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    dataSource.setAutoCommit(true);
    dataSource.setConnectionTimeout(60000);
    dataSource.setMaximumPoolSize(10);

    return dataSource;
  }

}
