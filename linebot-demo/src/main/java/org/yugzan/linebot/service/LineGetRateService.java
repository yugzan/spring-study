package org.yugzan.linebot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.yugzan.linebot.service.annotation.EnableSemanticsService;
import org.yugzan.linebot.service.chain.AIChainService;
import org.yugzan.linebot.service.chain.ActionChainService;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;

/**
 * @author yongzan
 * @date 2018/04/09 
 */
@EnableSemanticsService
public class LineGetRateService implements SemanticsService{

	@Autowired
	private ActionChainService actionService;

	@Autowired
	private AIChainService aiService;
	
	@EventMapping
	public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
		System.out.println("event: " + event);
		//Try different chain Service is workable
		actionService.executeChain(event);
		aiService.executeChain(event);
		return null;
	}

}
