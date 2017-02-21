package net.ion.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    private List<SaveMessage> history(String path, Pageable pageable) {
    	Page<SaveMessage> page = msgSvc.findByPath(path, pageable);
    	return page.getContent();
    }

    @RequestMapping(value="/hello/{who}/list/{page}", method = RequestMethod.GET)
    public HttpEntity historyWith(@PathVariable String who 
    		,@PageableDefault(sort = "createDate" ,direction = Direction.ASC ,size = 50) 
    		Pageable pageable) throws Exception {
    	String path = "/hello/"+who;
		return new ResponseEntity(this.history(path, pageable), HttpStatus.OK);
    }

    @MessageMapping("/hello/{who}")
    @SendTo("/topic/{who}")
    public BackMessage talkWith(@DestinationVariable String who
    		, ChatMessage mesg) throws Exception {
    	String text;
    	SaveMessage saved;
    	String id;
    	String path = "/hello/"+who;

    	logger.info(mesg.getText());
    	text = StringUtils.trim(mesg.getText());
		if (StringUtils.isEmpty(text))
			return null;

    	saved = msgSvc.save(who,text,path);
		id = saved.getId();
		BackController.sendBack("/topic/"+who, new BackMessage(id,who,text));
		
		who = "bot";
		text = simSvc.exec(mesg.getText());
		saved = msgSvc.save(who,text,path);
		id = saved.getId();
        return new BackMessage(id,who,text);
    }

}