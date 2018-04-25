package org.yugzan.linebot.service.action;

import java.util.Currency;
import java.util.HashMap;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.yugzan.linebot.erate.core.ERateServiceImpl;
import org.yugzan.linebot.model.UserResource;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;

/**
 * @author yongzan
 * @date 2018/04/19 
 */
@Component
@Order(4)
public class ERateSetCurrencyAction extends ERateAction{
	
	
	@Autowired
	private LineMessagingClient client;
	
	@Autowired
	private ERateServiceImpl rateService;
	
	@Override
	protected void action(MessageEvent<TextMessageContent> content) {
		String lineId = content.getSource().getSenderId();
		String delCommand =  content.getMessage().getText().trim().toUpperCase().replaceFirst("SET", "").trim();
		String iso = delCommand.replaceAll("[^a-zA-Z]","");
		String value = delCommand.replaceAll(iso,"").trim();
		
		Optional<Currency> finder = Currency.getAvailableCurrencies()
		.stream()
		.filter(c->c.getCurrencyCode().equals(iso)).findAny();

		if(!finder.isPresent()) {
			client.replyMessage( new ReplyMessage(content.getReplyToken(), new TextMessage("設定失敗") ));
		}else {
			HashMap<String, String> map = new HashMap<>();
			map.put(finder.get().getCurrencyCode(),  value);
			UserResource resource = new UserResource();
			resource.setLineId(lineId);
			resource.setThreshold(map);
			try {
				rateService.setUserResource(resource);
				client.replyMessage( new ReplyMessage(content.getReplyToken(), new TextMessage("設定幣別：" + finder.get().getCurrencyCode() + value ) ));
			} catch (Exception e) {
				client.replyMessage( new ReplyMessage(content.getReplyToken(), new TextMessage("設定失敗") ));
				e.printStackTrace();
			}
		}
	}

	@Override
	protected boolean isActive(MessageEvent<TextMessageContent> content) {
		return content.getMessage().getText().trim().toUpperCase().matches("^(SET)\\s*[a-zA-Z]+\\s*[+-]?([0-9]+[.])?[0-9]+$");
	}
}
