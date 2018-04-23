package org.yugzan.retry.core;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * @author yongzan
 * @date 2018/04/23 
 */
@Component
public class LoginOperate {
	
	public void login(String resourceUrl) {
		login(resourceUrl, null);
	}
	
	public void login(String resourceUrl, HttpEntity<String> entity) {
    	System.out.println("Run login");
    	try{
    		RestTemplate restTemplate = new RestTemplate();
        	ResponseEntity<String> response = restTemplate.exchange(resourceUrl, HttpMethod.GET, entity ,String.class);

        	System.out.println("finish :" + response.getStatusCode());
        }catch(HttpClientErrorException e) {
        	// 過濾HttpClientErrorException 錯誤如果是驗證錯誤狀態碼就終止嘗試執行
        	if(e.getLocalizedMessage().contains("401")) {
            	System.out.println("stop retry");
            	return ;
        	}else {
        		throw e;
        	}
        }catch(Exception e) {
    		throw e;
    	}		
	}
}
