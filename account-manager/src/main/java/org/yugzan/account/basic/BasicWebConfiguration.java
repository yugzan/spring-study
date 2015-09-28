package org.yugzan.account.basic;

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
import org.yugzan.account.AccountManagerApplication;
import org.yugzan.account.DefaultAnnotationValues;

/**
 * @author yugzan
 * @date 2015年9月28日
 * @project account-manager
 */
@Configuration
@EnableWebMvc
public class BasicWebConfiguration extends WebMvcConfigurerAdapter
		implements ImportAware, BeanClassLoaderAware {

	private String[] resource_handler = { DefaultAnnotationValues.RESOURCE_URI };

	private String[] resource_location = { DefaultAnnotationValues.RESOURCE_CLASS_PATH };

	private ClassLoader beanClassLoader;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		for (int point = 0; point < resource_handler.length; point++) {
			registry.addResourceHandler(resource_handler[point])
					.addResourceLocations(resource_location[point]);
		}

		registry.addResourceHandler("/admin/**")
				.addResourceLocations("classpath:/admin/");

	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		String uri = resource_handler[0].replace("*", "");
		registry.addRedirectViewController("/", uri + "index.html");
	}

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
