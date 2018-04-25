package org.yugzan.linebot.service.action;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.yugzan.linebot.erate.core.ERateServiceImpl;
import org.yugzan.linebot.erate.core.UserDao;
import org.yugzan.linebot.model.UserResource;

import com.google.common.collect.Lists;
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
	
	@Autowired(required = false)
	private UserDao userDao;
	
	@Autowired
	private LineMessagingClient client;

	@Override
	protected void action(MessageEvent<TextMessageContent> event) {
		logger.info("action");
		String order = event.getMessage().getText().replace("查詢","").trim().toUpperCase();
		List<String> orderISO = Lists.newArrayList();
		Optional<UserResource> user = Optional.empty();
		if(userDao != null) {
			user = userDao.read(event.getSource().getSenderId());
		}
		try {
			if(order.isEmpty()) {
				if(user.isPresent()) {
					orderISO.addAll( user.get().getThreshold().keySet());
				}else {
					orderISO.add("JPY");
					orderISO.add("USD");
				}
			}else {
				orderISO.add(order);
			}
			System.out.println( orderISO );
			String result = (user.isPresent())?
					rateService.getLastValue(orderISO, user.get().getBankName()):
					rateService.getLastValue(orderISO);
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
