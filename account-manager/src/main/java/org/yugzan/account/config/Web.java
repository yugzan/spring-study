package org.yugzan.account.config;

/**
 * @author yongzan
 * @date 2015/8/14
 * 
 */
public interface Web {
	String USER = "user";
	String PW = "password";
	String ROLE = "USER";
	String RESOURCE_URI = "/web/**";
	String RESOURCE_CLASS_PATH = "classpath:/web/";
}
