package org.yugzan.linebot.model;

import java.util.Collection;

/**
 * @author yongzan
 * @date 2016/11/14
 */
public class BankResource {
	private String iso;

	private Collection<Collection<String>> data;

	public BankResource() {
	}

	public BankResource(Collection<Collection<String>> data) {
		super();
		this.data = data;
	}

	public String getIso() {
		return iso;
	}

	public void setIso(String iso) {
		this.iso = iso;
	}

	public Collection<Collection<String>> getData() {
		return data;
	}

	public void setData(Collection<Collection<String>> data) {
		this.data = data;
	}

}
