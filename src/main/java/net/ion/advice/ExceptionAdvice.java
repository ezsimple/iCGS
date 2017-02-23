package net.ion.advice;

import java.nio.file.AccessDeniedException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionAdvice extends RuntimeException {

	protected Log logger = LogFactory.getLog(this.getClass());
	final String FORBIDDEN_VIEW = "/error/403";

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ModelAndView doForbidden(HttpServletRequest request,Exception e) throws Exception { 
		// If the exception is annotated with @ResponseStatus rethrow it 
		// and let the framework handle it 
		// AnnotationUtils is a Spring Framework utility class.
		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
			throw e;

		// Otherwise setup and send the user to a default error-view. 
		ModelAndView mv = new ModelAndView(); 
		mv.addObject("exception", e); 
		mv.addObject("url", request.getRequestURL()); 
		mv.setViewName(FORBIDDEN_VIEW); 

		return mv;
	}

}
