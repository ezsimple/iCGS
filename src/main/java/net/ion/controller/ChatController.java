package net.ion.controller;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import net.ion.dao.SessionRepository;
import net.ion.entity.BackMessage;
import net.ion.entity.ChatMessage;
import net.ion.entity.SaveMessage;
import net.ion.service.SaveMessageService;
import net.ion.service.SimsimiService;

@Controller
public class ChatController {
	
	protected Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	SimsimiService simSvc;

	@Autowired
	SaveMessageService msgSvc;
	
	private String mkPath(final String who) {
		return "/hello/"+who; // 특수기호인 : 는 사용할 수 없다.
	}

    private List<SaveMessage> history(String last_id, String path, Pageable pageable) {
    	logger.debug("last_id : "+last_id);

		if (StringUtils.isEmpty(last_id))
			return(msgSvc.findByPath(path, pageable).getContent());

		final String id = last_id;
		SaveMessage o = msgSvc.findById(id);
		if(o == null)
			return Collections.emptyList();
		logger.debug(o.getId() + ":" + o.getText());

		return (List<SaveMessage>) (msgSvc.findByCreateDateGreaterThanAndPath(o.getCreateDate(), path, null));
    }

    @RequestMapping(value="/hello/{who}/list/{page}", method = RequestMethod.POST)
    public HttpEntity historyWith(@PathVariable String who 
    		,@PageableDefault(sort = "createDate" ,direction = Direction.ASC ,size = 50) Pageable pageable
    		,@RequestParam("last_id") String last_id
    		) throws Exception {
    	String path = mkPath(who);
		return new ResponseEntity(this.history(last_id, path, pageable), HttpStatus.OK);
    }

    @MessageMapping("/hello/{who}")
    @SendTo("/topic/{who}")
    public BackMessage talkWith(@DestinationVariable String who
    		, ChatMessage mesg) throws Exception {
    	String text;
    	SaveMessage saved;
    	String id = "";
    	Date date;
    	String path = mkPath(who);

    	text = StringUtils.trim(mesg.getText());
		if (StringUtils.isEmpty(text))
			return null;

    	saved = msgSvc.save(who,text,path);
		id = saved.getId();
		date = saved.getCreateDate();
		BackController.sendBack("/topic/"+who, new BackMessage(id,who,text,date));
		
		who = "bot";
		text = simSvc.exec(mesg.getText());
		if(text == null)
			return null;
		saved = msgSvc.save(who,text,path);
		id = saved.getId();
		date = saved.getCreateDate();
        return new BackMessage(id,who,text,date);
    }
    
    @SubscribeMapping("/advice/{who}") // 이때 who의 값은 고객명이 된다.
    public void adviceTo(@DestinationVariable String who, ChatMessage mesg) {
    	String text;
    	SaveMessage saved;
    	String id = "";
    	Date date;
    	String path = mkPath(who);

    	text = StringUtils.trim(mesg.getText());
		if (StringUtils.isEmpty(text))
			return;

		String oper = "oper";
    	saved = msgSvc.save(oper,text,path);
		id = saved.getId();
		date = saved.getCreateDate();
		BackController.sendBack("/topic/"+who, new BackMessage(id,oper,text,date));
    }
}