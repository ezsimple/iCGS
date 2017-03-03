package net.ion.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ion.dao.MessageRepository;
import net.ion.entity.SaveMessage;

@RestController
public class TestController {

	protected Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	MessageRepository dao;

	@RequestMapping("/i/want/search/{who}")
	public void list(@PathVariable("who") String who) {
		String path = "/hello/"+who;
		Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "createDate"));
		Iterable<SaveMessage> list = dao.findByPath(path, sort);
		SaveMessage obj = null;
		for(SaveMessage o : list) {
			obj = o;
		}
		
		// obj is last SaveMessage
		if (obj != null) {
			String id = obj.getId();
			String text = obj.getText();
			logger.debug(id + ":" + text);
			Iterable<SaveMessage> data = dao.findByIdLessThanEqualAndPath(id, path, sort);
			for(SaveMessage o : data) {
				logger.debug(o.getText());
			}
		}
		return;
	}

}
