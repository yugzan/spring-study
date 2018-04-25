package org.yugzan.linebot.model;

import java.util.HashMap;

/**
 * @author yongzan
 * @date 2018/04/17
 */
public class UserResource {

	private String lineId;

	private String bankName;

	private HashMap<String, String> threshold;

	
	
	public UserResource(String lineId, String bankName, HashMap<String, String> threshold) {
		super();
		this.lineId = lineId;
		this.bankName = bankName;
		this.threshold = threshold;
	}

	public UserResource() {

	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public HashMap<String, String> getThreshold() {
		return threshold;
	}

	public void setThreshold(HashMap<String, String> threshold) {
		this.threshold = threshold;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

}
