package org.yugzan.linebot.influx.model;

import java.util.Map;

/**
 * @author yongzan
 * @date 2018/04/17
 */
public class UserResource {

	private String lineId;

	private String bankName;

	private Map<String, String> threshold;

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Map<String, String> getThreshold() {
		return threshold;
	}

	public void setThreshold(Map<String, String> threshold) {
		this.threshold = threshold;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

}
