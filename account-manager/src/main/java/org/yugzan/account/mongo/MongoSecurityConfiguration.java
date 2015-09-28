package org.yugzan.account.mongo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.ClassUtils;
import org.yugzan.account.AccountManagerApplication;
import org.yugzan.account.mongo.api.AuthenticationFilter;
import org.yugzan.account.mongo.domain.Account;
import org.yugzan.account.mongo.service.MongoUserDetailsService;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true ,securedEnabled = true)
public class MongoSecurityConfiguration implements ImportAware,  BeanClassLoaderAware{

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
    private ClassLoader beanClassLoader;
    
    @Autowired
    private MongoUserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		if (!userDetailsService.userExists("admin")) {
			List<String> roles = new ArrayList<>();
			roles.add("ROLE_ADMIN");
			userDetailsService.createUser(new Account("admin", "pass", roles));
		}
      logger.error("userDetailsService :{}",userDetailsService.toString());
	}
	
    @Configuration
    @Order(1)
    public static  class APIsSecurityConfiguration extends WebSecurityConfigurerAdapter{
    	
    	@Value("${account.roles:ADMIN, USER}")
    	private String [] ROLES;

		
    	@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
			.antMatcher("/api/**")
			.authorizeRequests()
			.anyRequest().hasAnyRole(ROLES)
			.and()
			.anonymous().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint());
			
	        http.addFilterBefore(new AuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class);
		}
	    @Bean
	    public AuthenticationEntryPoint unauthorizedEntryPoint() {
	        return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	    }
    }
    
    @Configuration
    public static  class FormSecurityConfiguration extends WebSecurityConfigurerAdapter{
    	
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
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

	public void setImportMetadata(AnnotationMetadata importMetadata) {
        Map<String, Object> enableAccountManagerAttrMap = importMetadata.getAnnotationAttributes(AccountManagerApplication.class.getName());
        AnnotationAttributes enableAccountManagerAttrs = AnnotationAttributes.fromMap(enableAccountManagerAttrMap);
        if(enableAccountManagerAttrs == null) {
            // search parent classes
            Class<?> currentClass = ClassUtils.resolveClassName(importMetadata.getClassName(), beanClassLoader);
            for(Class<?> classToInspect = currentClass ;classToInspect != null; classToInspect = classToInspect.getSuperclass()) {
            	AccountManagerApplication enableAccountManagerAnnotation = AnnotationUtils.findAnnotation(classToInspect, AccountManagerApplication.class);
                if(enableAccountManagerAnnotation == null) {
                    continue;
                }
                enableAccountManagerAttrMap = AnnotationUtils.getAnnotationAttributes(enableAccountManagerAnnotation);
                enableAccountManagerAttrs = AnnotationAttributes.fromMap(enableAccountManagerAttrMap);
            }
        }// is null

	}
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }
}
