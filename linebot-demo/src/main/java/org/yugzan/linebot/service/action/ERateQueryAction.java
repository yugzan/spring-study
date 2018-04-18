package org.yugzan.linebot.service.action;

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
@Order(2)
public class ERateQueryAction extends ERateAction{
	@Autowired
	private ERateServiceImpl rateService;
	
	@Autowired
	private LineMessagingClient client;

	@Override
	protected void action(MessageEvent<TextMessageContent> event) {
		logger.info("action");
		String orderISO = event.getMessage().getText().replace("查詢","").trim().toUpperCase();
		try {
			if(orderISO.isEmpty()) {
				//TODO user define in future.
				orderISO = "JPY";
			}
			String result = rateService.getLastValue(orderISO);
			client.replyMessage( new ReplyMessage(event.getReplyToken(), new TextMessage(result)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected boolean isActive(MessageEvent<TextMessageContent> event) {
		return event.getMessage().getText().trim().startsWith("查詢");
	}

}
