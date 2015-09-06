package org.yugzan.account.db.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yugzan.account.db.domain.Account;
import org.yugzan.account.db.repo.AccountRepository;


/**
 * @author yongzan
 * @date 2015/8/24
 * 
 */

@Service
@Transactional
public class MongoDBUserDetailsService  implements UserDetailsManager{

	@Autowired
	private AccountRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    Optional<UserDetails> loadedUser;
	    try {
	      Account account = repo.findByUsername(username);

	      loadedUser =
	          Optional.of(new User(account.getUsername(), account.getPassword(), account.getAuthorities() ));
	      if (!loadedUser.isPresent()) {
	        throw new InternalAuthenticationServiceException(
	            "UserDetailsService returned null, which is an interface contract violation");
	      }
	    } catch (Exception repositoryProblem) {
	      throw new InternalAuthenticationServiceException(repositoryProblem.getMessage(),
	          repositoryProblem);
	    }

	    return loadedUser.get();
	}

	@Override
	public void createUser(UserDetails user) {
		if (!userExists(user.getUsername())){
			repo.insert((Account) user);
		}
	}

	@Override
	public void updateUser(UserDetails user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(String username) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean userExists(String username) {
		return (Optional.ofNullable(repo.findByUsername(username)).isPresent()) ? true : false;
	}

}
