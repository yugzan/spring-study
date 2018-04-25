package org.yugzan.linebot.model;
/**
 * @author yongzan
 * @date 2018/04/03 
 */

import java.time.Instant;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

@Measurement(name = "bank_point")
public class BankPoint {

	@Column(name = "bankTag")
	private String bankTag;

	@Column(name = "iso")
	private String iso;

	@Column(name = "time")
	private Instant time;

	@Column(name = "lastTime")
	private String lastTime;

	@Column(name = "bankInfo")
	private String bankInfo;

	@Column(name = "bankName")
	private String bankName;

	@Column(name = "buyIn")
	private String buyIn;

	@Column(name = "buyOut")
	private String buyOut;

	@Column(name = "other")
	private String other;

	public String getBankTag() {
		return bankTag;
	}

	public String getIso() {
		return iso;
	}

	public String getLastTime() {
		return lastTime;
	}

	public String getBankInfo() {
		return bankInfo;
	}

	public String getBankName() {
		return bankName;
	}

	public String getBuyIn() {
		return buyIn;
	}

	public String getBuyOut() {
		return buyOut;
	}

	public String getOther() {
		return other;
	}

	public void setBankTag(String bankTag) {
		this.bankTag = bankTag;
	}

	public void setIso(String iso) {
		this.iso = iso;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public void setBankInfo(String bankInfo) {
		this.bankInfo = bankInfo;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public void setBuyIn(String buyIn) {
		this.buyIn = buyIn;
	}

	public void setBuyOut(String buyOut) {
		this.buyOut = buyOut;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public Instant getTime() {
		return time;
	}

	public void setTime(Instant time) {
		this.time = time;
	}

}
