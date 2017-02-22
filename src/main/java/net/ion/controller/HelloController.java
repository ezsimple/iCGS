package net.ion.controller;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {
	
	protected Log logger = LogFactory.getLog(this.getClass());
	
	/*
	 * If you are using Spring 4.1 and Java 8 you can use java.util.Optional 
	 * which is supported in @RequestParam, @PathVariable, @RequestHeader 
	 * and @MatrixVariable in Spring MVC
	 */
	@RequestMapping(value={"/", "/{page}.do", "{path}/{page}.do"}, method=RequestMethod.POST)
	public ModelAndView doPost(@PathVariable Optional<String> path 
			,@PathVariable Optional<String> page
			,HttpServletRequest request) throws AccessDeniedException {

		ModelAndView mv = new ModelAndView();
		String target = "index";

		if (path.isPresent() && page.isPresent()) {
			if (StringUtils.equals(path.get(), "chat") 
					&& StringUtils.equals(page.get(), "with")) {
				String x = request.getParameter("username");
				if (StringUtils.isEmpty(x))
					throw new AccessDeniedException("허용안함");
			}
			target = path.get()+"/"+page.get();
		}

		logger.debug("POST : "+target);
		mv.setViewName(target);

		return mv;
	}
	
	@RequestMapping(value={"/", "/{page}.do", "{path}/{page}.do"}, method=RequestMethod.GET)
	public ModelAndView doGet(@PathVariable Optional<String> path ,@PathVariable Optional<String> page) 
			throws AccessDeniedException {
		ModelAndView mv = new ModelAndView();
		final String startPage = "index";
		String target = "";
		
		if (path.isPresent() && page.isPresent()) {
			if (StringUtils.equals(path.get(), "chat") && StringUtils.equals(page.get(), "with")) {
				throw new AccessDeniedException("허용안함");
			}
			target = path.get()+"/"+page.get();
		}
		
		if (path.isPresent() && !page.isPresent()) {
			target = path.get()+"/"+startPage;
		}
		
		if (!path.isPresent() && page.isPresent()) {
			target = page.get();
		}
		
		if (!path.isPresent() && !page.isPresent()) {
			target = startPage;
		}

		logger.debug("GET : "+target);
		mv.setViewName(target);
		return mv;
	}

}