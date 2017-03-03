package net.ion.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ion.dao.MessageRepository;
import net.ion.entity.SaveMessage;

@RestController
public class SearchController {

	protected Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	MessageRepository dao;

	@RequestMapping("/i/want/search/{who}")
	public void list(@PathVariable("who") String who) {
		String path = "/hello/"+who;
		Iterable<SaveMessage> list = dao.findByPath(path);
		SaveMessage obj = null;
		for(SaveMessage o : list) {
			obj = o;
			// logger.debug(o.getText());
		}
		
		// obj is last SaveMessage
		if (obj != null) {
			String id = obj.getId();
			Iterable<SaveMessage> data = dao.findByIdLessThanEqualAndPath(id, path);
			for(SaveMessage o : data) {
				logger.debug(o.getText());
			}
		}

		
		
		return;
	}

}
