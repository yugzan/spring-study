package org.yugzan.account.mongo.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.filter.GenericFilterBean;

/**
 * @author  yugzan
 * @date    2015年9月27日
 * @project account-manager
 */
public class AuthenticationFilter extends GenericFilterBean{ //UsernamePasswordAuthenticationFilter
	
	private final static Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
	
	private AuthenticationManager authenticationManager;
	/**
	 * @param authenticationManager
	 */
	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = asHttp(request);
        HttpServletResponse httpResponse = asHttp(response);

        
        Optional<String> basicAuthorization = Optional.ofNullable(httpRequest.getHeader("Authorization"));

        Optional<List<String>> decodeAuth = decodeBasicAuthentication(basicAuthorization);
        

        
        try {
        	decodeAuth.ifPresent( auth->{
                String username = auth.get(0);
                String password = auth.get(1);

            	logger.debug("Trying to authenticate user : {}, password : [PROTECTED]; ",username);
            	
            	processAdminAuthentication(username , password, httpRequest);
                
                logger.debug("AuthenticationFilter is passing request down the filter chain");
        	});

            chain.doFilter(request, response);
        } catch (InternalAuthenticationServiceException internalAuthenticationServiceException) {
            SecurityContextHolder.clearContext();
            logger.error("Internal authentication service exception", internalAuthenticationServiceException);
            httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (AuthenticationException authenticationException) {
            SecurityContextHolder.clearContext();
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, authenticationException.getMessage());
        }
		
	}
	private Optional<List<String>> decodeBasicAuthentication(Optional<String> basicAuth){
		Optional<List<String>> result = Optional.empty();
		if(!basicAuth.isPresent()){
			return result;
		}
        int beginPasswordIndexPosition = basicAuth.get().indexOf(" ") + 1;
        String encodedAuth = basicAuth.get().substring(beginPasswordIndexPosition);
        String[] arr = new String(Base64.decode(encodedAuth.getBytes())).split(":");

        List<String> auth = new ArrayList<>();
        try{
        	auth.add(arr[0]);
        	auth.add(arr[1]);
        	result = Optional.ofNullable( auth );
        }catch(Exception e){
        	// user and password not null
        }
        return result;
	}
	

	private void processAdminAuthentication(String username, String password,HttpServletRequest request) {
        Authentication resultOfAuthentication = tryToAuthenticateUserPassword(username, password, request);
        SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication);
		
	}


	/**
	 * @param username
	 * @param password
	 * @param request
	 * @return
	 */
	private Authentication tryToAuthenticateUserPassword(String username, String password,
			HttpServletRequest request) {
    	return tryToAuthenticate(new APIsToken(username, password,request));
	}



	/**
	 * @param apIsToken
	 * @return
	 */
	private Authentication tryToAuthenticate(Authentication requestAuthentication) {
        Authentication responseAuthentication = authenticationManager.authenticate(requestAuthentication);
        if (responseAuthentication == null || !responseAuthentication.isAuthenticated()) {
            throw new InternalAuthenticationServiceException("Unable to authenticate Domain User for provided credentials");
        }
        logger.debug("User successfully authenticated");
        
        
        return responseAuthentication;
	}



	private HttpServletRequest asHttp(ServletRequest request) {
        return (HttpServletRequest) request;
    }

    private HttpServletResponse asHttp(ServletResponse response) {
        return (HttpServletResponse) response;
    }
}

