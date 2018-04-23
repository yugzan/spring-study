package org.yugzan.retry.core;

import org.springframework.http.HttpEntity;

/**
 * @author yongzan
 * @date 2018/04/23 
 */
public interface LoginService {

	public void tryLogin(String resourceUrl);
	
	public void tryLogin(String resourceUrl, HttpEntity<String> entity);
}
