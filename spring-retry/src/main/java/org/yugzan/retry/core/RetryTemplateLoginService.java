package org.yugzan.retry.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

/**
 * @author yongzan
 * @date 2018/04/23 
 */
@Service
public class RetryTemplateLoginService implements LoginService{

	@Autowired
	private LoginOperate operate;

	//透過 AppConfig 設定產生的Bean呼叫使用
	@Autowired
	private RetryTemplate template;

	@Override
	public void tryLogin(String resourceUrl) {
		tryLogin(resourceUrl, null);
	}
	
	public void tryLogin(String resourceUrl, HttpEntity<String> entity) {
		final RetryCallback<Void, RuntimeException> retryCallback = new RetryCallback<Void, RuntimeException>() {
		    @Override
		    public Void doWithRetry(RetryContext retryContext) {
//		    	[RetryContext: count=1, lastException=org.springframework.web.client.HttpClientErrorException: 400 Bad Request, exhausted=false]
//		        System.out.println(retryContext);
//		        System.out.println(retryContext.getRetryCount());
		        retryContext.setAttribute("resourceUrl", resourceUrl);
				operate.login(resourceUrl,entity);
				return null;
		    }
		};
        final RecoveryCallback<Void> recoveryCallback = new RecoveryCallback<Void>() {
            public Void recover(RetryContext context) throws Exception {
            	System.out.println(String.format("%s-->%s", 
            			context.getAttribute("resourceUrl"), 
            			context.getLastThrowable().getMessage()));
                System.out.println("do recory " +context.getLastThrowable().getMessage());                
                return null;
            }
        };

        //再這填入需要操作的 RetryCallback 跟 RecoveryCallback
		template.execute(retryCallback, recoveryCallback);
		
	}

}
