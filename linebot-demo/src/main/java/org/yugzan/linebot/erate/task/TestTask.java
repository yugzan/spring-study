package org.yugzan.linebot.erate.task;
/**
 * @author yongzan
 * @date 2018/04/18 
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.yugzan.linebot.service.LineGetRateService;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.event.source.UserSource;

@Component
@ConditionalOnExpression("${spring.influxdb.enable}")
public class TestTask {


    @Autowired
    private LineGetRateService testService ;

	
//	@Scheduled(cron = "0 0/1 * * * ?")
	public void queryTest() throws Exception {

	    
		Source source = new UserSource("user123");
//		TextMessageContent content = new TextMessageContent("user123", "SETBANK 兆豐商銀");

//		MessageEvent<TextMessageContent> event = new MessageEvent<TextMessageContent>(null, source, content, null);
//		testService.handleTextMessageEvent(event);
		
		testService.handleTextMessageEvent( new MessageEvent<TextMessageContent>(null, source,  new TextMessageContent("user123", "查詢") , null) );		

	}
}
