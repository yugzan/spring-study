package org.yugzan.linebot.erate.core;

import java.util.List;
import java.util.function.Consumer;

import org.yugzan.linebot.model.BankPoint;
import org.yugzan.linebot.model.BankResource;
import org.yugzan.linebot.model.UserResource;

/**
 * @author yongzan
 * @date 2018/04/02 
 */
public interface ERateService {

	public final static String REGEX = "[-+\"`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？A-z0-9\\s]";
	public final static String REGEX_SWIFT = "([a-zA-Z]{4}[a-zA-Z]{2}[a-zA-Z0-9]{2}([a-zA-Z0-9]{3})?)";
	// http://tw.fnzcode.com/swift/taiwan/
	
	public final static String SQL_BY_LASTONE=
			"SELECT * FROM \"%s\" WHERE time > now() - %s AND \"iso\"='%s' GROUP BY \"bankTag\" LIMIT 1";
	//bank_point , 1m , JPY 
	
	public final static String RESULT_TEMPLAT="%s(): 買入:%s 賣出:%s";
	public final static String SIMPLE_RESULT_TEMPLAT="%s(%s): :%s";
	/**
	 * Convert raw object to string.
	 * */
	public String toCommonString(BankResource resource) throws Exception;
	
	/**
	 *  Real-time fetch URL request get value. 
	 * */
	public BankResource getRealtimeValue(String orderISO) throws Exception;
	
	/**
	 *  Real-time fetch URL request get value. 
	 * */
	public void getRealtimeValue(String orderISO,Consumer<BankResource> sucess, Consumer<Throwable> error);
	
	/**
	 *  Get value from Database. 
	 * */	
	public String getLastValue(List<String> orderISOs) throws Exception;
	
	/**
	 *  Get value from Database. 
	 * */	
	public String getLastValue(List<String> orderISOs, String filterBank) throws Exception;
	
	/**
	 *  Setting user resource value to Database. 
	 * */	
	public void setUserResource(UserResource resource) throws Exception;
	/**
	 *  Direct query string to query bank point. 
	 * */
	public List<BankPoint> query(String queryString) throws Exception;
	
}
