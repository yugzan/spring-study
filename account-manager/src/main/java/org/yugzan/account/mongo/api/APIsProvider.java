package org.yugzan.account.mongo.api;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * @author  yugzan
 * @date    2015年9月27日
 * @project account-manager
 */
public class APIsProvider implements AuthenticationProvider{
	
	public static final String INVALID_BACKEND_ADMIN_CREDENTIALS = "Invalid Admin Credentials";
	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Optional<String> username = Optional.ofNullable(authentication.getPrincipal().toString());
        Optional<String> password = Optional.ofNullable(authentication.getCredentials().toString());

        if (credentialsMissing(username, password)) {
            throw new BadCredentialsException(INVALID_BACKEND_ADMIN_CREDENTIALS);
        }

        return new UsernamePasswordAuthenticationToken(username.get(), password.get(),
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
	}
    private boolean credentialsMissing(Optional<String> username, Optional<String> password) {
        return !username.isPresent() || !password.isPresent();
    }

	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> authentication) {

		return APIsToken.class.equals(authentication);
	}

}

