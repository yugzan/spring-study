package org.yugzan.linebot.influx;

import java.util.Objects;
import java.util.Optional;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author yongzan
 * @date 2016/11/9
 * @Ref https://github.com/miwurster/spring-data-influxdb
 */
@Configuration
@ConditionalOnExpression("${database.influxdb.enable}")
@EnableConfigurationProperties(InfluxDBProperties.class)
public class InfluxDBConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(InfluxDBConfiguration.class);

    Optional<InfluxDB> db = Optional.empty();

    @Bean
    public InfluxDB influxFactory(final InfluxDBProperties properties) {
        Objects.requireNonNull(properties, "InfluxDB Properties is null.");
        if (!db.isPresent()) {
            db = Optional.of(InfluxDBFactory.connect(
                            properties.getUrl(),
                            properties.getUsername(), properties.getPassword()));
            
            logger.debug("Using InfluxDB '{}' on '{}'", properties.getDatabase(), properties.getUrl());
        }
        return db.get();
    }
    
    
    @Bean
    public InfluxDBTemplate influxTemplate(final InfluxDBProperties propertie){
        
        return new InfluxDBTemplate( influxFactory(propertie) , propertie);
    }


}
