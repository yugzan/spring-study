package org.yugzan.mongo.core;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yugzan.mongo.repo.UserRepository;

/**
 * @author yongzan
 * @date 2018/05/14
 */
@Service
public class UserServiceImpl implements UserService<UserModel, String> {

	@Autowired
	private UserRepository repository;
	
	
	@Override
	public UserModel create(UserModel user) throws RuntimeException {
		user.setCreateTime(OffsetDateTime.now());
		return repository.insert(user);
	}

	@Override
	public Collection<UserModel> getAll() throws RuntimeException {
		return repository.findAll();
	}

	@Override
	public Optional<UserModel> get(String id) throws RuntimeException {
		return (repository.exists(id))?
				Optional.of(repository.findOne(id)):Optional.empty();
	}

	@Override
	public UserModel replace(String id, UserModel user) throws RuntimeException {
		UserModel old = get(id).orElseThrow( ()-> new RuntimeException(""));
		old.setName(user.getName());
		old.setModifyTime(OffsetDateTime.now());
		return repository.save(old);
	}

	@Override
	public void remove(String id) throws RuntimeException {
		repository.delete(id);
	}

}
