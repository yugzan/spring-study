package org.iii.sample.mail;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yugzan
 *
 */
@Component
@ConfigurationProperties(prefix = "maildeliver")
public class MailDeliverProperty {
	private String host;
	private int port;
	private String username;
	private String password;
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "MailDeliverProperty [host=" + host + ", port=" + port + ", username="
				+ username + ", password=" + password + "]";
	}
}
