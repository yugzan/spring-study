package org.iii.influx.test.task;

import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import org.iii.influx.config.InfluxDBTemplate;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author yongzan
 * @date 2016/11/10
 */
@Component
@ConditionalOnExpression("${database.influxdb.fakedata:false}")
public class InsertFakeTask{
    private static final Logger logger = LoggerFactory.getLogger(InsertFakeTask.class);
    
    @Autowired
    private InfluxDBTemplate dbTemplate;

    @PostConstruct
    private void initBean(){
        logger.info("task database.influxdb.fakedata : true");
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void insert() {
        OffsetDateTime start = OffsetDateTime.now();
        logger.info("{}",OffsetDateTime.now());
//        Integer[] count = new Integer[1000];
//        List<Integer> counter = Arrays.asList(count);
//        counter.parallelStream().forEach(action -> {
//            
//        });
        dbTemplate.write(newPoint());
        logger.info("{}", ( OffsetDateTime.now().toEpochSecond() - start.toEpochSecond() ) );
        logger.info("{}",  dbTemplate.query("select Temperature  from person WHERE time > now() - 10s").toString()  );
        //dbTemplate.query("select count(*) from person ").toString() 
    }
    private Random random = new SecureRandom();

    private Point newPoint() {
        return Point.measurement("person").time(OffsetDateTime.now().toInstant().toEpochMilli(), TimeUnit.MILLISECONDS)
                .tag("version", "0.0.1").addField("Temperature", random.nextInt(40))
                .addField("Pulse", random.nextInt(100))
                .addField("Respiratory rate", random.nextInt(1000))
                .addField("Blood pressure", random.nextInt(200)).build();
    }

}
