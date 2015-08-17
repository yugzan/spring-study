package org.yugzan.account.config;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.util.ClassUtils;
import org.yugzan.account.EnableAccountManager;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter implements ImportAware,  BeanClassLoaderAware{

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	private String account_name = Web.USER;
	
	private String account_password = Web.PW;
	
	private String account_role = Web.ROLE;
	
    private ClassLoader beanClassLoader;
    
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.inMemoryAuthentication()
			.withUser(account_name).password(account_password).roles(account_role);
        logger.error("inMemoryAuthentication :{}",account_name);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.anyRequest()
		.authenticated()
		.and()
		.formLogin()
		.and()
		.httpBasic();
	}

	public void setImportMetadata(AnnotationMetadata importMetadata) {
        Map<String, Object> enableAccountManagerAttrMap = importMetadata.getAnnotationAttributes(EnableAccountManager.class.getName());
        AnnotationAttributes enableAccountManagerAttrs = AnnotationAttributes.fromMap(enableAccountManagerAttrMap);
        if(enableAccountManagerAttrs == null) {
            // search parent classes
            Class<?> currentClass = ClassUtils.resolveClassName(importMetadata.getClassName(), beanClassLoader);
            for(Class<?> classToInspect = currentClass ;classToInspect != null; classToInspect = classToInspect.getSuperclass()) {
            	EnableAccountManager enableAccountManagerAnnotation = AnnotationUtils.findAnnotation(classToInspect, EnableAccountManager.class);
                if(enableAccountManagerAnnotation == null) {
                    continue;
                }
                enableAccountManagerAttrMap = AnnotationUtils.getAnnotationAttributes(enableAccountManagerAnnotation);
                enableAccountManagerAttrs = AnnotationAttributes.fromMap(enableAccountManagerAttrMap);
            }
        }// is null
        account_name = Optional.of(enableAccountManagerAttrs.getString("user")).orElse(Web.USER);
        account_password = Optional.of(enableAccountManagerAttrs.getString("pw")).orElse(Web.PW);
        account_role = Optional.of(enableAccountManagerAttrs.getString("role")).orElse(Web.ROLE);

	}
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }
}
