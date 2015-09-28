package org.yugzan.account;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/***
 *  Override and extends {@link #WebSecurityConfigurerAdapter} and {@link #WebMvcConfigurerAdapter}
 *  @Parmaeter basePackages ComponentScan value.
 *  @Parameter resourceUri append resource uri to control.
 *  @Parameter staticContent append resource class path. 
 * */

@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = { java.lang.annotation.ElementType.TYPE })
@Documented
@Configuration
@EnableAutoConfiguration
@ComponentScan
public @interface AccountManagerApplication {
	/**
	 * See  {@link @ComponentScan}
	 * */
	String[] value() default DefaultAnnotationValues.BASE_PACKAGES;
	/**
	 * See  {@link @ComponentScan}
	 * */
	String[] basePackages() default DefaultAnnotationValues.BASE_PACKAGES;
	/**
	 * See  {@link @ComponentScan}
	 * */
	Class<?>[] basePackageClasses() default {};
	/**
	 * See  {@link @ComponentScan}
	 * */
	Class<?>[] exclude() default {};
	

	/**
	 * String [] ex . {"/web/**" , "/js/**"}<br/>
	 * Auto Redirect is mapping to array first item. ex ."web/index.html" <br/>
	 * Default resourceUri {@link DefaultAnnotationValues}<br/>
	 * <br/>
	 * **Notice** resourceUri items size need equivalent  with staticContent items
	 * */
	String [] resourceUri() default DefaultAnnotationValues.RESOURCE_URI;
	/**
	 * String [] ex . {"classpath:/web/" , "classpath:/js/"} <br/>
	 * Default staticContent {@link DefaultAnnotationValues}<br/>
	 * <br/>
	 * **Notice** resourceUri items size need equivalent  with staticContent items
	 * */
	String [] staticContent() default DefaultAnnotationValues.RESOURCE_CLASS_PATH;
}
