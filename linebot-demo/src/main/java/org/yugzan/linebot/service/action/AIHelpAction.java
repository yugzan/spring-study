package org.yugzan.linebot.service.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;

/**
 * @author yongzan
 * @date 2018/04/11 
 */
@Component
@Order(1)
public class AIHelpAction extends AIAction{
	
	@Autowired
	private LineMessagingClient client;
	
	@Override
	protected void action(MessageEvent<TextMessageContent> text) {
		logger.info("action");
		client.replyMessage( new ReplyMessage(text.getReplyToken(), new TextMessage("需要幫你叫救護車嗎？")));
	}

	@Override
	protected boolean isActive(MessageEvent<TextMessageContent> text) {
		return text.getMessage().getText().equals("help");
	}

}
