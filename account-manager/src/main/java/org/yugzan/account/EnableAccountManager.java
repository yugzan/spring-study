package org.yugzan.account;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.yugzan.account.config.SecurityConfiguration;
import org.yugzan.account.config.Web;
import org.yugzan.account.config.WebConfiguration;

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
@EnableWebMvc
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({SecurityConfiguration.class, WebConfiguration.class})
@Configuration
public @interface EnableAccountManager {
	boolean value() default false;
	String user() default Web.USER;
	String pw()   default Web.PW;
	String role() default Web.ROLE;
	String resourceUri() default Web.RESOURCE_URI;
	String staticContent() default Web.RESOURCE_CLASS_PATH;
}
