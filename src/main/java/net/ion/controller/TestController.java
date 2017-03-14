package net.ion.controller;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.ion.entity.SaveMessage;
import net.ion.repository.MessageRepository;
import net.ion.service.WisenutService;

@RestController
public class TestController {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	MessageRepository dao;
	
	@Autowired
	WisenutService svc;
	
	
	@RequestMapping("/wn")
	public void wn() throws Exception {
		svc.exec("");
	}
	
	
	@RequestMapping("/hello/find/lostmessages")
	public HttpEntity findLostMessages(@RequestParam("username") String who,
			@RequestParam("last_id") String last_id) {
		String path = "/hello/"+who;
		Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "createDate"));
		final String id = last_id;
		logger.debug(id.length());
		SaveMessage o = dao.findById(id);
		String text = o.getText();
		Date day = o.getCreateDate();
		logger.debug(id + ":" + text);
		Iterable<SaveMessage> result = dao.findByCreateDateGreaterThanAndPath(day.getTime(), path, sort);
		return new ResponseEntity(result, HttpStatus.OK);
	}

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
			Date day = obj.getCreateDate();
			logger.debug(id + ":" + text);
			Iterable<SaveMessage> data = dao.findByCreateDateLessThanEqualAndPath(day.getTime(), path, sort);
			for(SaveMessage o : data) {
				logger.debug(o.getText());
			}
		}
		return;
	}

}
