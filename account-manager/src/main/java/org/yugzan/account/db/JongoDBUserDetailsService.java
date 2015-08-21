package org.yugzan.account.db;

import java.util.Optional;

import org.jongo.MongoCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author yongzan
 * @date 2015/8/21
 * 
 */
@Service
public class JongoDBUserDetailsService implements UserDetailsService {

	@Autowired
	private MongoCollection users;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserDetails> loadedUser;
		try {
			JongoDBClient client = users.findOne("{#: #}", JongoDBClient.USERNAME, username).as(JongoDBClient.class);
			loadedUser = Optional.of(new User(client.getUsername(), client.getPassword(), client.getRoles()));
			if (!loadedUser.isPresent()) {
				throw new InternalAuthenticationServiceException(
						"UserDetailsService returned null, which is an interface contract violation");
			}
		} catch (Exception repositoryProblem) {
			throw new InternalAuthenticationServiceException(
					repositoryProblem.getMessage(), repositoryProblem);
		}

		return loadedUser.get();
	}

}
