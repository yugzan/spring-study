package org.yugzan.account.mongo.api.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.yugzan.account.mongo.repo.AccountRepository;
import org.yugzan.account.mongo.service.ResourceNotFoundException;

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
	public UserDetails accountinfo(@AuthenticationPrincipal Authentication auth) throws ResourceNotFoundException {
//		SecurityContextHolder.getContext().getAuthentication().getName()
		Optional<String> username = Optional.ofNullable(auth.getName());
		
		Optional<UserDetails> userDetails = (username.isPresent())?
				Optional.ofNullable(repo.findByUsername(username.get())):
				Optional.empty();
		
		if (userDetails.isPresent()) {
			return userDetails.get();
		}else {
			throw new ResourceNotFoundException();
		}

	}

}
