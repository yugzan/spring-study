package org.test.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author  yugzan
 * @date    2015年9月26日
 * @project account-manager
 */

@RestController
public class CustomControllerTest {

	@RequestMapping(value = "/org/{value}" , method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String getTest(@PathVariable("value") String value){
		return value;
		
	}
}

