package org.yugzan.retry;

import java.nio.charset.Charset;
import java.util.Collections;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.yugzan.retry.core.LoginService;
import org.yugzan.retry.core.RetryLoginService;
import org.yugzan.retry.core.RetryTemplateLoginService;

/**
 * @author yongzan
 * @date 2018/04/23 
 */
@Service
public class MainTestService {
	/**
	 * 該類別使用 RetryTemplate 設定Retry屬性再實作使用方法
	 * */
    @Autowired
    @Qualifier("retryTemplateLoginService")
    private RetryTemplateLoginService retryTemplateLoginService;

	/**
	 * 該類別使用 @Retryable 於實作方法宣告並設定Retry屬性
	 * */
    @Autowired
    @Qualifier("retryLoginService")
    private RetryLoginService retryLoginService;
    
	public void start() {

		System.out.println("==================");
		System.out.println("RetryLoginService");
		run(retryLoginService);
		System.out.println("==================");
		System.out.println("RetryTemplateLoginService");
		run(retryTemplateLoginService);
	}
	

    private void run(LoginService service){
    	
    	String url = "http://127.0.0.1:9000";
    	HttpEntity<String> authEntity = new HttpEntity<>("parameters", createHeaders("username","password") );

    	
    	System.out.println("=======>Test auth Get [/auth].");
    	service.tryLogin(url+"/auth", authEntity);
    	System.out.println("=======>Test non-auth Get [/auth].");
    	service.tryLogin(url+"/auth");
    	
    	System.out.println("=======>Test auth Get [/].");
    	service.tryLogin(url, authEntity);
    	System.out.println("=======>Test non-auth Get [/].");
    	service.tryLogin(url);
    	
    	System.out.println("=======>Test auth Get [/400].");
    	service.tryLogin(url+"/400", authEntity);
    	
    	System.out.println("=======>Test auth Get [/403].");
    	service.tryLogin(url+"/403", authEntity);
    	
    	System.out.println("=======>Test auth Get [/random].");
    	service.tryLogin(url+"/random", authEntity);
    	
    	System.out.println("=======>Test auth Get [/500].");
    	service.tryLogin(url+"/500", authEntity);
    }
    
    HttpHeaders createHeaders(String username, String password){
 	   return new HttpHeaders() {
			private static final long serialVersionUID = 1L;
		{
 	         String auth = username + ":" + password;
 	         byte[] encodedAuth = Base64.encodeBase64( 
 	            auth.getBytes(Charset.forName("UTF-8")) );
 	         String authHeader = "Basic " + new String( encodedAuth );
 	         set( "Authorization", authHeader );
 	         setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
 	      }};
 	}
}
