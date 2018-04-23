package org.yugzan.retry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author yongzan
 * @date 2018/04/23 
 */
@Component
public class AppListener {

    @Autowired
    private MainTestService service;
    
	@EventListener
	public void handleContextRefresh(ContextStartedEvent event) {
    	service.start();
	}
}
