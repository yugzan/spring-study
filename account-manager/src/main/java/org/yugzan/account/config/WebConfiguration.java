package org.yugzan.account.config;

import java.util.Map;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.yugzan.account.EnableAccountManager;

@Configuration
@EnableWebMvc
public class WebConfiguration extends WebMvcConfigurerAdapter implements ImportAware,  BeanClassLoaderAware{
    private String[] resource_handler = {Web.RESOURCE_URI};
    
    private String[] resource_location = {Web.RESOURCE_CLASS_PATH};
	
	private ClassLoader beanClassLoader;
    
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		for(int point = 0 ;point<resource_handler.length; point ++) {
			registry
			.addResourceHandler(resource_handler[point])
			.addResourceLocations(resource_location[point]);
		}
		registry
		.addResourceHandler("/admin/**")
		.addResourceLocations("classpath:/admin/");
		
	}
	

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		String uri = resource_handler[0].replace("*", "")+ "index.html";
		registry.addRedirectViewController("/", uri);
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
		
        String []uris = enableAccountManagerAttrs.getStringArray("resourceUri");
        String []paths = enableAccountManagerAttrs.getStringArray("staticContent");
        if((uris.length == paths.length) && uris.length>0 && paths.length >0) {
            resource_handler =  uris;
            resource_location = paths;
        }
	}

}
