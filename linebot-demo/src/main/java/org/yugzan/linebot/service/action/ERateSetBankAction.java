package org.yugzan.linebot.service.action;

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
 * @date 2018/04/18 
 */
@Component
@Order(3)
public class ERateSetBankAction extends ERateAction{

	@Autowired
	private ERateServiceImpl rateService;
	
	@Autowired
	private LineMessagingClient client;
	
	@Override
	protected void action(MessageEvent<TextMessageContent> event) {
		String lineId = event.getSource().getSenderId();
		String bankName =  event.getMessage().getText().trim().toUpperCase().replaceFirst("SETBANK", "").trim();
		
		UserResource resource = new UserResource();
		resource.setBankName(bankName);
		resource.setLineId(lineId);
		
		try {
			rateService.setUserResource(resource);
			client.replyMessage( new ReplyMessage(event.getReplyToken(), new TextMessage("設定銀行為：" + bankName) ));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	protected boolean isActive(MessageEvent<TextMessageContent> event) {
		return event.getMessage().getText().trim().toUpperCase().matches("^(SETBANK).+$");
	}
}


