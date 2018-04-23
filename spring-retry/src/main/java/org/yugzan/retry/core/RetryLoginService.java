package org.yugzan.retry.core;

import java.net.ConnectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

/**
 * @author yongzan
 * @date 2018/04/23 
 */
@Service
public class RetryLoginService implements LoginService{

	@Autowired
	private LoginOperate operate;

	@Override
	public void tryLogin(String resourceUrl) {
		tryLogin(resourceUrl, null);
	}
	
    @Retryable(
    		maxAttempts = 3,  //重試次數
    		value = { //出現哪種類型錯誤要處理什麼
    				ConnectException.class, 
    				HttpServerErrorException.class,
    				HttpClientErrorException.class,
    				ResourceAccessException.class })
	public void tryLogin(String resourceUrl, HttpEntity<String> entity) {
    	operate.login(resourceUrl, entity);
	}

    
    //一般伺服器或用戶操作錯誤大部份是 HttpClientErrorException 和 HttpServerErrorException
    @Recover
    public void recover1(HttpClientErrorException e, String resourceUrl, HttpEntity<String> entity) {
        //　tryLogin 對應的參數可以回傳
    	System.out.println(String.format("%s-->%s", resourceUrl, e.getMessage()));
        System.out.println("do recover HttpClientErrorException");
    }
    @Recover
    public void recover1(HttpServerErrorException e) {
        System.out.println(e.getMessage());
        System.out.println("do recover HttpServerErrorException");
    }
    
    //通常網路延遲連接錯誤有 ConnectException 和 ResourceAccessException
    @Recover
    public void recover1(java.net.ConnectException e) {
        System.out.println(e.getMessage());
        System.out.println("do recover ConnectException");
    }
    @Recover
    public void recover1(ResourceAccessException e) {
        System.out.println(e.getMessage());
        System.out.println("do recover ResourceAccessException");
    }

}
