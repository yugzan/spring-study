package org.yugzan.linebot.service.action;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.jayway.jsonpath.JsonPath;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author yongzan
 * @date 2018/04/11 
 */
@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class AIResonseAction extends AIAction{
	
	@Autowired
	private LineMessagingClient client;
	
    public interface QingServer {
        @GET("api.php")
        Call<ResponseBody> replyText(@Query("key")String key,@Query("appid")String appid,@Query("msg")String text);
    }// "api.php?key=free&appid=0&msg=" + text;
	

    @Override
	protected void action(MessageEvent<TextMessageContent> event) {
		logger.info("action");
		Optional<String> result = Optional.empty();
		try {
	    	String text =  URLEncoder.encode(event.getMessage().getText().trim(), "UTF-8");
	        Retrofit retrofit = new Retrofit.Builder()
	        		.baseUrl("http://api.qingyunke.com/").build();
	        
	        QingServer service = retrofit.create(QingServer.class);
	        System.out.println(text);

	        Response<ResponseBody> response = service.replyText("free","0",text).execute();

	        String raw = response.body().string();

	        result = Optional.ofNullable( JsonPath.read(raw, "$.content"));
		}catch(IOException e) {
			
		}
		client.replyMessage( new ReplyMessage(event.getReplyToken(), new TextMessage(result.orElse("..."))));
	}

	@Override
	protected boolean isActive(MessageEvent<TextMessageContent> event) {
		return !event.getMessage().getText().isEmpty();
	}

}
