package org.yugzan.account.basic;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.util.ClassUtils;
import org.yugzan.account.AccountManagerApplication;
import org.yugzan.account.DefaultAnnotationValues;

/**
 * @author  yugzan
 * @date    2015年9月28日
 * @project account-manager
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true , securedEnabled= true)
public class BasicSecurityConfiguration extends WebSecurityConfigurerAdapter implements ImportAware, BeanClassLoaderAware{
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	private String[] resource_handler = { DefaultAnnotationValues.RESOURCE_URI };

	private String[] resource_location = { DefaultAnnotationValues.RESOURCE_CLASS_PATH };
	
	private ClassLoader beanClassLoader;
	
	@Value("${basic.username:admin}")
	private String account_name;
	
	@Value("${basic.password:test}")
	private String account_password;
	
	@Value("${basic.roles:ADMIN}")
	private String account_roles;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
		.withUser(account_name)
		.password(account_password)
		.roles(account_roles);
        logger.error("inMemoryAuthentication :{}",account_name);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/js/**", "/css/**", "/images/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()
		.and()
		.logout()
		.and()
		.authorizeRequests()
		.antMatchers("/index.html", "/login.html", "/logout").permitAll() // for anonymous user
		.anyRequest().authenticated().and().csrf().disable();
	}

	
	/****************************
	 * AnnotationMetadata Handler
	 * */
	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.beanClassLoader = classLoader;
	}
	@Override
	public void setImportMetadata(AnnotationMetadata importMetadata) {
		Map<String, Object> enableAccountManagerAttrMap = importMetadata
				.getAnnotationAttributes(AccountManagerApplication.class.getName());
		AnnotationAttributes enableAccountManagerAttrs = AnnotationAttributes
				.fromMap(enableAccountManagerAttrMap);
		if (enableAccountManagerAttrs == null) {
			// search parent classes
			Class<?> currentClass = ClassUtils
					.resolveClassName(importMetadata.getClassName(), beanClassLoader);
			for (Class<?> classToInspect = currentClass; classToInspect != null; classToInspect = classToInspect
					.getSuperclass()) {
				AccountManagerApplication enableAccountManagerAnnotation = AnnotationUtils
						.findAnnotation(classToInspect, AccountManagerApplication.class);
				if (enableAccountManagerAnnotation == null) {
					continue;
				}
				enableAccountManagerAttrMap = AnnotationUtils
						.getAnnotationAttributes(enableAccountManagerAnnotation);
				enableAccountManagerAttrs = AnnotationAttributes
						.fromMap(enableAccountManagerAttrMap);
			}
		} // is null

		String[] uris = enableAccountManagerAttrs.getStringArray("resourceUri");
		String[] paths = enableAccountManagerAttrs.getStringArray("staticContent");
		if ((uris.length == paths.length) && uris.length > 0 && paths.length > 0) {
			resource_handler = uris;
			resource_location = paths;
		}
	}
}

