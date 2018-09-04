package org.yugzan.mongo.core;

import java.util.Collection;
import java.util.Optional;

/**
 * @author yongzan
 * @date 2018/04/23
 */
public interface UserService<U, ID> {
	public U create(U user) throws RuntimeException;

	public Collection<U> getAll() throws RuntimeException;

	public Optional<U> get(ID id) throws RuntimeException;
	
	public U replace(ID id, U user) throws RuntimeException;

	public void remove(String id) throws RuntimeException;
}
