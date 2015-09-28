package org.yugzan.account.mongo.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * @author  yugzan
 * @date    2015年9月27日
 * @project account-manager
 */
public class APIsToken extends UsernamePasswordAuthenticationToken{

	private static final long serialVersionUID = 1L;

	/**
	 * @param principal
	 * @param credentials
	 */
	public APIsToken(Object principal, Object credentials,HttpServletRequest httpRequest) {
		super(principal, credentials);
		setDetails(new WebAuthenticationDetails(httpRequest));
	}

	@Override
	public void setDetails(Object details) {
		super.setDetails(details);
	}

}

