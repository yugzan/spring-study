package org.yugzan.linebot.erate.task;

import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.yugzan.linebot.erate.core.ERateService;
import org.yugzan.linebot.influx.InfluxDBTemplate;
import org.yugzan.linebot.influx.model.BankResource;
import org.yugzan.linebot.influx.model.ResourceConverter;
import com.google.common.collect.Lists;

/**
 * @author yongzan
 * @date 2018/04/02
 */
@Component
@ConditionalOnExpression("${database.influxdb.enable}")
public class ERateInsertTask {
	private static final Logger logger = LoggerFactory.getLogger(ERateInsertTask.class);
	
	private static final List<String> ORDER_ISO =  Lists.newArrayList("JPY", "USD","EUR","HKD","CNY","AUD","GBP","THB","SGD","CAD");
	
	@Autowired
	private InfluxDBTemplate dbTemplate;

	@Autowired
	private ERateService rateService;


    @PostConstruct
    private void initTask(){
        logger.info("task ERateInsertTask");
    }
    
	@Scheduled(cron = "0 0/1 * * * ?")
	public void parser() {
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
	
//	@Scheduled(cron = "*/5 * * * * ?")
	public void queryTest() {

		logger.error("queryTest");
		try {
			System.out.println( rateService.getLastValue(ORDER_ISO.get(0)) );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
