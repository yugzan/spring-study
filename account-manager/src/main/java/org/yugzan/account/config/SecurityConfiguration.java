package org.yugzan.account.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.util.ClassUtils;
import org.yugzan.account.EnableAccountManager;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter implements ImportAware,  BeanClassLoaderAware{

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	private String account_name = Web.USER;
	
	private String account_password = Web.PW;
	
	private String account_role = Web.ROLE;
	
    private ClassLoader beanClassLoader;
    
    @Autowired
    private DataSource datasource;
    
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        JdbcUserDetailsManager userDetailsService = new JdbcUserDetailsManager();
        userDetailsService.setDataSource(datasource);
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
        
        auth.jdbcAuthentication().dataSource(datasource);
        
        if(!userDetailsService.userExists(account_name)) {
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority(account_role));
            User userDetails = new User(account_name, encoder.encode(account_password), authorities);
            userDetailsService.createUser(userDetails);
        }
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
