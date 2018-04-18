package org.yugzan.linebot.influx.model;

import java.time.Instant;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

/**
 * @author yongzan
 * @date 2018/04/17
 */
@Measurement(name = "user_point")
public class UserPoint {

	@Column(name = "time")
	private Instant time;

	@Column(name = "lineId")
	private String lineId;

	@Column(name = "threshold")
	private String threshold;

	@Column(name = "bankName")
	private String bankName;

	public Instant getTime() {
		return time;
	}

	public String getThreshold() {
		return threshold;
	}

	public String getBankName() {
		return bankName;
	}

	public void setTime(Instant time) {
		this.time = time;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

}
