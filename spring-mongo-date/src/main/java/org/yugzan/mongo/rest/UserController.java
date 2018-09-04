package org.yugzan.mongo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.yugzan.mongo.core.ResourceCollection;
import org.yugzan.mongo.core.UserModel;
import org.yugzan.mongo.core.UserService;

/**
 * @author yongzan
 * @date 2018/05/14 
 */
@RestController
public class UserController {

	@Autowired
	private UserService<UserModel, String> service;
	
	@RequestMapping(method = RequestMethod.POST, value = "/users" )
	@ResponseStatus(value=HttpStatus.CREATED)
	@ResponseBody
	public UserModel create(@RequestBody UserModel user) {
		return service.create(user);//new ResponseEntity<UserModel>( , HttpStatus.CREATED );
	}

	@RequestMapping(method = RequestMethod.GET, value = "/users")
	@ResponseStatus(value=HttpStatus.OK)
	@ResponseBody
	public ResourceCollection<UserModel> list() {
		return new ResourceCollection<UserModel>(service.getAll());
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/users/{id}" )
	@ResponseStatus(value=HttpStatus.OK)
	@ResponseBody
	public UserModel get(@PathVariable  String id) {
		return service.get(id).orElseThrow(()->new RuntimeException("id is no found.") );
//		return new ResponseEntity<UserModel>(result , HttpStatus.OK );
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/users/{id}" )
	@ResponseStatus(value=HttpStatus.OK)
	@ResponseBody
	public UserModel update(@PathVariable  String id, @RequestBody UserModel changeModel) {
		return service.replace(id, changeModel);
	}
}
