package tut.webdata.config;

import javax.servlet.Filter;
import javax.servlet.ServletRegistration;

import org.springframework.core.annotation.Order;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Order(2)
public class WebAppInitializer extends
		AbstractAnnotationConfigDispatcherServletInitializer {

  //{!begin addToRootContext}
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { SecurityConfig.class, PersistenceConfig.class,
				MongoConfiguration.class, JPAConfiguration.class };  //, GemfireConfiguration.class
	}
  //{!end addToRootContext}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
//		characterEncodingFilter.setForceEncoding(true);
		return new Filter[] { characterEncodingFilter};
	}
	
//	@Override
//    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
//        registration.setInitParameter("defaultHtmlEscape", "true");
//        registration.setInitParameter("spring.profiles.active", "default");
//    }
}
