package org.yugzan.retry;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author yongzan
 * @date 2018/04/24 
 */
@Component
public class RestTemplateFactory implements FactoryBean<RestTemplate>, InitializingBean {
	
	private RestTemplate restTemplate;
	
	@Override
	public void afterPropertiesSet() throws Exception {
        restTemplate = new RestTemplate();
	}

	@Override
	public RestTemplate getObject() throws Exception {
		return restTemplate;
	}

	@Override
	public Class<?> getObjectType() {
		return RestTemplate.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
