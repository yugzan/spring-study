package org.yugzan.account;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.yugzan.account.config.AppConfiguration;
import org.yugzan.account.config.SecurityConfiguration;
import org.yugzan.account.config.Web;
import org.yugzan.account.config.WebConfiguration;
import org.yugzan.account.db.service.MongoDBUserDetailsService;


/***
 *  Override and extends {@link #WebSecurityConfigurerAdapter} and {@link #WebMvcConfigurerAdapter}
 *  @Parameter user setting account name.
 *  @Parameter pw    setting account password.
 *  @Parameter role  setting account role.
 *  @Parameter resourceUri append resource uri to control.
 *  @Parameter staticContent append resource class path. 
 * */

@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = { java.lang.annotation.ElementType.TYPE })
@Documented
@Import({
	AppConfiguration.class,
	MongoDBUserDetailsService.class,
	SecurityConfiguration.class,
	WebConfiguration.class
})
@Configuration
public @interface EnableAccountManager {
	boolean value() default false;
	/**
	 * Default user {@link Web}
	 * */
	String user() default Web.USER;
	/**
	 * Default password {@link Web}
	 * */
	String pw()   default Web.PW;
	/**
	 * Default role {@link Web}
	 * */
	String role() default Web.ROLE;
	/**
	 * String [] ex . {"/web/**" , "/js/**"}<br/>
	 * Auto Redirect is mapping to array first item. ex ."web/index.html" <br/>
	 * Default resourceUri {@link Web}<br/>
	 * <br/>
	 * **Notice** resourceUri items size need equivalent  with staticContent items
	 * */
	String [] resourceUri() default Web.RESOURCE_URI;
	/**
	 * String [] ex . {"classpath:/web/" , "classpath:/js/"} <br/>
	 * Default staticContent {@link Web}<br/>
	 * <br/>
	 * **Notice** resourceUri items size need equivalent  with staticContent items
	 * */
	String [] staticContent() default Web.RESOURCE_CLASS_PATH;
}
