package org.yugzan.account;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;
import org.yugzan.account.basic.BasicSecurityConfiguration;
import org.yugzan.account.basic.BasicWebConfiguration;

/**
 * @author  yugzan
 * @date    2015年9月28日
 * @project account-manager
 */
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = { java.lang.annotation.ElementType.TYPE })
@Documented
@Import({
	BasicSecurityConfiguration.class,
	BasicWebConfiguration.class
})
public @interface UseBasicDB {

}

