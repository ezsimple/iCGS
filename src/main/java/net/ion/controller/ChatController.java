package net.ion.controller;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;

import net.ion.entity.BotMessage;
import net.ion.entity.ChatMessage;
import net.ion.entity.Message;
import net.ion.service.MessageService;
import net.ion.service.SimsimiService;

@Controller
public class ChatController {
	
	protected Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	SimsimiService simSvc;

	@Autowired
	MessageService msgSvc;

    public List<BotMessage> history(String path) {
		List<Message> messages = (List<Message>) msgSvc.findByPath(path);
		List<BotMessage> list = new ArrayList();
		for(Message m : messages) {
			BotMessage res = new BotMessage("", "");
			if("bot".equals(m.getWho())) res.setResponse(m.getMessage()).setRestime(m.getDate());
			else res.setRequest(m.getMessage()).setReqtime(m.getDate());
			list.add(res);
		}
		return list;
    }

    @RequestMapping(value="/hello/{who}/list/{page}", method = RequestMethod.GET)
    public HttpEntity historyWith(@PathVariable String who ,@PathVariable String page) throws Exception {
    	String path = "/hello/"+who;
    	List<BotMessage> chattingList = this.history(path);
		return new ResponseEntity(chattingList, HttpStatus.OK);
    }
    
    @RequestMapping("/{who}/list/{page}")
    public String historyWith(@PathVariable String who
    	,@PageableDefault(direction = Direction.DESC, size = 50) Pageable page) {
    	String path = "/hello/"+who;
		return "index";
    }
    
	
    @MessageMapping("/hello/{who}")
    @SendTo("/topic/{who}")
    public BotMessage talkWith(@DestinationVariable String who
    		, ChatMessage message) throws Exception {
    	String path = "/hello/"+who;
    	message.setReqtime(new Date());
    	msgSvc.save(who, message.getMesg(),path);
		String res = simSvc.exec(message.getMesg());
		msgSvc.save("bot",res,path);
        return new BotMessage(message.getMesg(),res)
        		.setReqtime(message.getReqtime())
        		.setRestime(new Date());
    }

}
