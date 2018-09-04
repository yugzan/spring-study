package org.yugzan.aop;

import org.springframework.stereotype.Component;

@Component
public class SomeService implements Service<String>{

	@Override
	public String doService(String value) {

		return "enjoy this service";
	}

}
