package org.yugzan.linebot;

import java.util.Optional;
import org.influxdb.InfluxDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.influxdb.DefaultInfluxDBTemplate;
import org.springframework.data.influxdb.InfluxDBConnectionFactory;
import org.springframework.data.influxdb.InfluxDBProperties;


/**
 * @author yongzan
 * @date 2016/11/9
 * @Ref https://github.com/miwurster/spring-data-influxdb
 */
@Configuration
@ConditionalOnExpression("${spring.influxdb.enable}")
@EnableConfigurationProperties(InfluxDBProperties.class)
public class InfluxDBConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(InfluxDBConfiguration.class);

    Optional<InfluxDB> db = Optional.empty();
    
    @Bean
    public InfluxDBConnectionFactory connectionFactory(final InfluxDBProperties properties)
    {
      return new InfluxDBConnectionFactory(properties);
    }

    @Bean
    public DefaultInfluxDBTemplate influxTemplate(final InfluxDBProperties propertie){
    	logger.info("Init DefaultInfluxDBTemplate.");
        return new DefaultInfluxDBTemplate( connectionFactory(propertie) );
    }


}
