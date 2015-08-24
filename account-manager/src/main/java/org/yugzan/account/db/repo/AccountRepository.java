package org.yugzan.account.db.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.yugzan.account.db.domain.Account;

/**
 * @author yongzan
 * @date 2015/8/24
 * 
 */

public interface AccountRepository extends MongoRepository<Account, String>{
	public Account findByUsername(String name);
}
