package org.yugzan.account.db.api;

/**
 * @author  yugzan
 * @date    2015/9/6
 * @project account-manager
 */
public interface ApiController {
	public  String API_PATH = "/api/v1";
	/**
	 * only print own user information
	 * */
	public  String ACCOUNT_URL = API_PATH + "/account";

}

