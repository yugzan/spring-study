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
		StringBuffer buf = new StringBuffer("我的指令：\n");
		buf.append("查詢\n");
		buf.append("查詢 {幣別代號}\n");
		buf.append("即時匯率\n");
		buf.append("即時匯率 {幣別代號}\n");
		buf.append("SET {幣別代號} {需通知數值}\n");
		buf.append("SETBANK {銀行名稱}\n");
		client.replyMessage( new ReplyMessage(text.getReplyToken(), new TextMessage(buf.toString() + "需要幫你叫救護車嗎？")));
	}

	@Override
	protected boolean isActive(MessageEvent<TextMessageContent> text) {
		return text.getMessage().getText().equals("help");
	}

}
