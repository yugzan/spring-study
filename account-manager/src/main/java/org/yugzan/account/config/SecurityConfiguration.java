package org.yugzan.account.config;


import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.yugzan.account.db.JongoDBUserDetailsService;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter implements ImportAware,  BeanClassLoaderAware{

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
    
	private ClassLoader beanClassLoader;
	
    private String resource_handler = Web.RESOURCE_URI;
    
    @Autowired
    private JongoDBUserDetailsService userDetailsService;
    
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		//auth.inMemoryAuthentication().withUser(account_name).password(account_password).roles(account_role);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

	    http.formLogin().defaultSuccessUrl(resource_handler+"/index.html")
	    .and().logout()
	    .and().authorizeRequests()
        .antMatchers("/index.html", "/home.html", "/login.html", "/access", "/logout").permitAll() //for anonymous user
        .and().authorizeRequests()
        .antMatchers("/web/*").hasAuthority("ROLE_ADMIN")
        .anyRequest().authenticated().and().csrf().disable();
	}
	
	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.beanClassLoader = classLoader;
	}

	@Override
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
		
        resource_handler =  Optional.of(enableAccountManagerAttrs.getString("resourceUri")).orElse(Web.RESOURCE_URI);
	}
}
