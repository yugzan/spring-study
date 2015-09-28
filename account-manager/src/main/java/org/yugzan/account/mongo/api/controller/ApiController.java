package org.yugzan.account.mongo.api.controller;

/**
 * @author  yugzan
 * @date    2015/9/6
 * @project account-manager
 */
public interface ApiController {
	public  String API_PATH = "/api/v1";
	/**
	 * APIs Login EndPoint
	 * */
	public  String AUTH_URL = API_PATH + "/auth";
	/**
	 * only print own user information
	 * */
	public  String ACCOUNT_URL = API_PATH + "/account";

}

