package net.ion.event;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import net.ion.entity.BackMessage;
import net.ion.repository.SessionRepository;
import net.ion.service.SaveMessageService;
import net.ion.service.WisenutService;

public class SessionEventListener {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	SessionRepository sessionRepository;
	
	@Autowired
	SaveMessageService saveMessageService;
	
	// @Autowired WisenutService wnSvc;

	private final String SIMP_SESSION_ID = "simpSessionId";
	private final String NATIVE_HEADERS = "nativeHeaders";
	private final String USER_NAME = "username";
	private final String QUEUE_WAITING = "/queue/waiting";
	private final String ADD = "+";
	private final String DEL = "-";

	@Autowired
    private SimpMessagingTemplate template;
    
	// @Autowired
	// private static SimpMessageSendingOperations template;
    
    private void notifyEvent(Object evt) {
        template.convertAndSend(QUEUE_WAITING, evt);
    }

	@EventListener
	private void handleSessionConnect(SessionConnectEvent event) {
		MessageHeaders headers = event.getMessage().getHeaders();
		String simpSessionId = (String) headers.get(SIMP_SESSION_ID);
		Map<String, LinkedList> nativeHeaders = (Map<String, LinkedList>) headers.get(NATIVE_HEADERS);
		List<String> usernameList = nativeHeaders.get(USER_NAME);
		if (usernameList == null) return;
		String username = usernameList.get(0);

		ConnectEvent evt = new ConnectEvent(ADD,username,simpSessionId);
		sessionRepository.add(simpSessionId, evt);
		notifyEvent(evt);

	}

	@EventListener
	private void handleSessionConnected(SessionConnectedEvent event) {
		MessageHeaders headers = event.getMessage().getHeaders();
		String simpSessionId = (String) headers.get(SIMP_SESSION_ID);
		Map<String, LinkedList> nativeHeaders = (Map<String, LinkedList>) headers.get(NATIVE_HEADERS);
		logger.debug("handleSessionConnected");
	}

	@EventListener
	private void handleSessionDisconnect(SessionDisconnectEvent event) {

		logger.info("SessionDisconnectEvent: " + event.toString());

		String simpSessionId = event.getSessionId();
		ConnectEvent o = sessionRepository.get(simpSessionId);
		if (o == null) return;
		String username = o.getUsername();

		Object evt = new ConnectEvent(DEL,username,simpSessionId);
		Optional.ofNullable(sessionRepository.get(simpSessionId))
			.ifPresent(x -> {
				sessionRepository.remove(simpSessionId); 
				notifyEvent(evt);
		});
		
	}

    private void greeting(String destination) {
	    String[] who = StringUtils.split(destination, "/");
    	final String text = "안녕하세요. " + who[1] + " 고객님. 채팅봇 입니다.";
    	// String path = "/hello/"+who[1];
    	// SaveMessage saveMessage = saveMessageService.findOneByPath(path);
    	// String id = saveMessage.getId();
    	// logger.debug(id);
    	BackMessage msg = new BackMessage("","bot",text,new Date());
        template.convertAndSend(destination, msg);
    }
	
    // 사용자 고객에게만 인사말 출력되도록 기능 구현
	@EventListener
	private void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
	      Message<byte[]> message = event.getMessage();
	      MessageHeaders headers = event.getMessage().getHeaders();
	      StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
	      StompCommand command = accessor.getCommand();
	      if (command.equals(StompCommand.SUBSCRIBE)) {
	          String sessionId = accessor.getSessionId();
	          String stompSubscriptionId = accessor.getSubscriptionId();
	          String destination = accessor.getDestination();
	          // Handle subscription event here
	          // e.g. send welcome message to *destination*
	          logger.debug("StompCommand.SUBSCRIBE : {}",destination);
	          if(StringUtils.startsWith(destination, "/topic")) {
	      		Map<String, LinkedList> nativeHeaders = (Map<String, LinkedList>) headers.get(NATIVE_HEADERS);
	    		List<String> usernameList = nativeHeaders.get(USER_NAME);
	    		if (usernameList == null) return;
	    		String username = usernameList.get(0);
	    		// 운영자가 아닌 고객(사용자)에게만 인사하기
	    		if(!"운영자".equals(username))
	    			greeting(destination);
	          }
	       }
		
	}

}
