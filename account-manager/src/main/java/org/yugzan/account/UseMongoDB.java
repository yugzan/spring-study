package org.yugzan.account;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;
import org.yugzan.account.mongo.AppConfiguration;
import org.yugzan.account.mongo.MongoSecurityConfiguration;
import org.yugzan.account.mongo.MongoWebConfiguration;
import org.yugzan.account.mongo.service.MongoUserDetailsService;

/**
 * @author  yugzan
 * @date    2015年9月26日
 * @project account-manager
 */
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = { java.lang.annotation.ElementType.TYPE })
@Documented
@Import({
	AppConfiguration.class,
	MongoUserDetailsService.class,
	MongoSecurityConfiguration.class,
	MongoWebConfiguration.class
})
public @interface UseMongoDB {

}

