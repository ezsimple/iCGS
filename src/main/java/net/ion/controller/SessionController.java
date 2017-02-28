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
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.ion.entity.BackMessage;
import net.ion.entity.ChatMessage;
import net.ion.entity.SaveMessage;
import net.ion.event.ConnectEvent;
import net.ion.service.SaveMessageService;
import net.ion.service.SimsimiService;

@Controller
public class SessionController {
	
	protected Log logger = LogFactory.getLog(this.getClass());
	
    // @RequestMapping(value="/hello/{who}/list/{page}", method = RequestMethod.GET)
    // public HttpEntity historyWith(@PathVariable String who 
    // 	,@PageableDefault(sort = "createDate" ,direction = Direction.ASC ,size = 50) 
    //		Pageable pageable) throws Exception {
	//	return new ResponseEntity(this.history(path, pageable), HttpStatus.OK);
    // }

    @MessageMapping("/queue/waiting")
    public void drawList(ConnectEvent event) {
        return;
    }
    
}