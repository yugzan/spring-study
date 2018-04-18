package org.yugzan.linebot.service.action;

import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.yugzan.linebot.erate.core.ERateServiceImpl;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;

/**
 * @author yongzan
 * @date 2018/04/10 
 */
@Component
@Order(1)
public class ERateRealtimeAction extends ERateAction{

	@Autowired
	private ERateServiceImpl rateService;
	
	@Autowired
	private LineMessagingClient client;
	
	private List<String> currencyList;
	
	@PostConstruct
	private void initCurrency() {
		currencyList = Currency.getAvailableCurrencies().stream().map(c->c.getCurrencyCode())
		.collect(Collectors.toList());
	}
	@Override
	protected void action(MessageEvent<TextMessageContent> event) {
		logger.info("action");
		String orderISO = event.getMessage().getText().replace("匯率","").trim().toUpperCase();
		try {
			if(orderISO.isEmpty()) {
				//TODO user define in future.
				orderISO = "JPY";
			}
			String result = rateService.toCommonString(rateService.getRealtimeValue(orderISO));
			client.replyMessage( new ReplyMessage(event.getReplyToken(), new TextMessage(result)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected boolean isActive(MessageEvent<TextMessageContent> event) {
		String input = event.getMessage().getText().trim();
		return input.startsWith("匯率") ||currencyList.contains(input);
	}
 

}
