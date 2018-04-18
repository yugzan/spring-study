package org.yugzan.linebot.service.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yongzan
 * @date 2017/8/4
 */
public abstract class AbstractAction<T> {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	public boolean excute(T text) {
		if(isActive(text)) {
			this.action(text);
			return true;
		}
		return false;
	}
	
	protected abstract void action(T text);
	
	protected abstract boolean isActive(T text);

	
}
