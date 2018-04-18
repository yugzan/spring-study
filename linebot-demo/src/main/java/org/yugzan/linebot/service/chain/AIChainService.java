package org.yugzan.linebot.service.chain;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yugzan.linebot.service.action.AIAction;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;

/**
 * @author yongzan
 * @date 2018/04/11 
 */
@Service
@Qualifier("doAIChain")
public class AIChainService extends ChainService<AIAction, MessageEvent<TextMessageContent>>{

}
