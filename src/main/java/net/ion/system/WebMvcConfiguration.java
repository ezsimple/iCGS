package net.ion.system;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

// @EnableAutoConfiguration
// @EnableWebMvc
@Configuration
public class WebMvcConfiguration extends SpringBootServletInitializer {
	protected Log logger = LogFactory.getLog(this.getClass());
}