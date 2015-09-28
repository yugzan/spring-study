package org.yugzan.account.mongo.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author yongzan
 * @date 2015/8/24
 * 
 */

@Document
public class Account extends AbstractDocument implements UserDetails, CredentialsContainer {	

	private static final long serialVersionUID = 1L;

	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String ROLES = "roles";


	private String username;
	private String password;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;

	private List<String> roles;
	
	/**
	 * @param username
	 * @param password
	 * @param roles
	 */
	
	@PersistenceConstructor
	public Account(String username, String password, List<String> roles) throws Exception {
		this(username, password, roles, true, true, true, true);
	}

	public Account(String username, String password, List<String> roles,
			boolean accountNonExpired, boolean accountNonLocked,
			boolean credentialsNonExpired, boolean enabled) throws Exception {
		this.username = username;
		this.password = password;
		this.roles = roles;
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
		this.enabled = enabled;
	}

	@Override
	public void eraseCredentials() {
		password = null;
		 
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		roles.forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.toString()));
		});
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("username", username)
				.append("password", password).append("roles", roles)
				.append("accountNonExpired", accountNonExpired)
				.append("accountNonLocked", accountNonLocked)
				.append("credentialsNonExpired", credentialsNonExpired)
				.append("enabled", enabled).toString();
	}
}
