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
}
