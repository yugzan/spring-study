package org.yugzan.linebot.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.yugzan.linebot.erate.core.ERateServiceImpl;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

/**
 * @author yongzan
 * @date 2018/04/02
 */
@LineMessageHandler
public class LineRealTimeRateService {
	
	@Autowired
	private ERateServiceImpl rateService;
	
	@Autowired
	private LineMessagingClient lineClient;
	
	@EventMapping
	public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
		System.out.println("event: " + event);
		
		String message = event.getMessage().getText().trim();
		if(!message.startsWith("匯率")) {
			return new TextMessage("今拍識，無法分析您的要求。");
		}else {
			lineClient.pushMessage(new PushMessage( event.getSource().getSenderId(),new TextMessage("燒等己勒吼~~讓我盤算一下指威抖數")  ));
			
		}
		String orderISO = event.getMessage().getText().replace("匯率","").trim().toUpperCase();
		Optional<String> result = parseSemantics(orderISO);
		return new TextMessage(result.orElse("今拍識，無法分析您的要求。"));
	}

	private Optional<String> parseSemantics(String value) {
		try {
			if(value.isEmpty()) {
				//TODO user define in future.
				value = "JPY";
			}
//			Optional<String> result =  Optional.of(rateService.getLastValue(value));
//			System.out.println(result);
//			return result;
			return Optional.of( rateService.toCommonString(rateService.getRealtimeValue(value)) );
		} catch (Exception e) {
//			e.printStackTrace();
			return Optional.empty();
		}
	}
}
