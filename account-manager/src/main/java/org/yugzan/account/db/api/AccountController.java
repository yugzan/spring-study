package org.yugzan.account.db.api;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.yugzan.account.db.repo.AccountRepository;
import org.yugzan.account.db.service.ResourceNotFoundException;

/**
 * @author yugzan
 * @date 2015/9/6
 * @project account-manager
 */
@RestController
public class AccountController implements ApiController {

	@Autowired
	private AccountRepository repo;

	@RequestMapping(method = RequestMethod.GET, value = ACCOUNT_URL)
	@ResponseStatus(value = HttpStatus.OK)
	public UserDetails accountinfo() throws ResourceNotFoundException {

		Optional<UserDetails> userDetails = Optional.ofNullable(
				repo.findByUsername(
						SecurityContextHolder.getContext().getAuthentication().getName()
				));
		
		if (userDetails.isPresent()) {
			return userDetails.get();
		}else {
			throw new ResourceNotFoundException();
		}

	}

}
