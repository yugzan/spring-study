package org.yugzan.mongo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.yugzan.mongo.core.UserModel;

/**
 * @author yongzan
 * @date 2018/05/14
 */
@Repository
public interface UserRepository extends MongoRepository<UserModel, String> {

}
