package org.yugzan.account.config;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.yugzan.account.EnableAccountManager;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter implements ImportAware,  BeanClassLoaderAware{
    private String resource_handler = Web.RESOURCE_URI;
    
    private String resource_location = Web.RESOURCE_CLASS_PATH;
	
	private ClassLoader beanClassLoader;
    
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry
		.addResourceHandler(resource_handler)
		.addResourceLocations(resource_location);
		registry
		.addResourceHandler("/admin/**")
		.addResourceLocations("classpath:/admin/");
		
	}
	

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		String uri = resource_handler.replace("*", "");
		registry.addViewController("/").setViewName("redirect:"+uri+"index.html");
		registry.addViewController("/login").setViewName("redirect:"+uri+"index.html");
		registry.addViewController("/logout").setViewName("redirect:"+uri+"index.html");
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
        resource_location =  Optional.of(enableAccountManagerAttrs.getString("staticContent")).orElse(Web.RESOURCE_CLASS_PATH);
	}

}
