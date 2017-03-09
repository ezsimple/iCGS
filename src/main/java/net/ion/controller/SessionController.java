package net.ion.controller;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.ion.event.ConnectEvent;
import net.ion.repository.SessionRepository;

@Controller
public class SessionController {
	
	protected Log logger = LogFactory.getLog(this.getClass());
	
	@Resource
	SessionRepository sessionRepo;

    @RequestMapping(value="/queue/waiting", method = RequestMethod.GET)
    public HttpEntity waitingList (@PageableDefault(sort = "createDate" ,direction = Direction.ASC ,size = 50) 
    		Pageable pageable) throws Exception {
		return new ResponseEntity(sessionRepo.getActiveSessions(), HttpStatus.OK);
    }

    @MessageMapping("/queue/waiting")
    public void drawList(ConnectEvent event) {
        return;
    }
    
}