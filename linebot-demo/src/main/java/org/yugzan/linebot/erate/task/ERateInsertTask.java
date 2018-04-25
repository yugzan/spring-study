package org.yugzan.linebot.erate.task;

import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.influxdb.DefaultInfluxDBTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.yugzan.linebot.erate.core.ERateService;
import org.yugzan.linebot.model.ResourceConverter;

import com.google.common.collect.Lists;

/**
 * @author yongzan
 * @date 2018/04/02
 */
@Component
@ConditionalOnExpression("${spring.influxdb.enable}")
public class ERateInsertTask {
	private static final Logger logger = LoggerFactory.getLogger(ERateInsertTask.class);
	
	private static final List<String> ORDER_ISO =  Lists.newArrayList("JPY", "USD","EUR","HKD","CNY","AUD","GBP","THB","SGD","CAD");
	
	@Autowired
	private DefaultInfluxDBTemplate dbTemplate;

	@Autowired
	private ERateService rateService;


    @PostConstruct
    private void initTask(){
        logger.info("task ERateTask");
    }
    
	@Scheduled(cron = "0 0/1 * * * ?")
	public void parserToDatabase() {
//		Currency.getAvailableCurrencies().stream().map(c->c.getCurrencyCode())
//		.collect(Collectors.toList()); 
		logger.info("ISO:{}", ORDER_ISO.toString());
		ORDER_ISO.stream().forEach( iso->{
			rateService.getRealtimeValue( iso, resource->{
				dbTemplate.write(ResourceConverter.convert(resource));
			}, err->{
				err.printStackTrace();
			});
		});
	}
	
}
