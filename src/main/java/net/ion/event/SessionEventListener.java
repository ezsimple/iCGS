package net.ion.event;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import net.ion.repository.SessionRepository;

public class SessionEventListener {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Resource
	SessionRepository sessionRepository;

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

}
