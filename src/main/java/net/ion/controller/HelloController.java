package net.ion.controller;

import java.util.Optional;

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
	@RequestMapping(value={"/", "/{page}.do", "{path}/{page}.do"}, method=RequestMethod.GET)
	public ModelAndView getPage(@PathVariable Optional<String> path ,@PathVariable Optional<String> page) {
		ModelAndView mv = new ModelAndView();
		final String startPage = "index";
		String target = "";
		
		if (path.isPresent() && page.isPresent()) {
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

		logger.debug(target);
		mv.setViewName(target);
		return mv;
	}

}