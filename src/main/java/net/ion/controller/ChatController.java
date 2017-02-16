package net.ion.controller;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import net.ion.entity.BotMessage;
import net.ion.entity.ChatMessage;
import net.ion.service.SimsimiService;

@Controller
public class ChatController {
	
	protected Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	SimsimiService svc;
	
    @MessageMapping("/hello/{userId}")
    @SendTo("/topic/greetings")
    public BotMessage greeting(ChatMessage message) throws Exception {
    	message.setReqtime(new Date());
		String res = svc.exec(message.getMesg());
        return new BotMessage(message.getMesg(),res).setReqtime(message.getReqtime()).setRestime(new Date());
    }

}
