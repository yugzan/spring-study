package org.yugzan.linebot.erate.core;

import org.yugzan.linebot.influx.model.BankResource;

/**
 * @author yongzan
 * @date 2018/04/02 
 */
public interface ERateService {

	public final static String REGEX = "[-+\"`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？A-z0-9\\s]";
	public final static String REGEX_SWIFT = "([a-zA-Z]{4}[a-zA-Z]{2}[a-zA-Z0-9]{2}([a-zA-Z0-9]{3})?)";
	// http://tw.fnzcode.com/swift/taiwan/
	
	/**
	 * Convert raw object to string.
	 * */
	public String toCommonString(BankResource resource) throws Exception;
	
	/**
	 *  Real-time fetch URL request get value. 
	 * */
	public BankResource getRealtimeValue(String orderISO) throws Exception;
	/**
	 *  Get value from Database. 
	 * */	
	public String getLastValue(String orderISO) throws Exception;
	
}
