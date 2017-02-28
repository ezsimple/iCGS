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
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import net.ion.dao.SessionRepository;

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
	private void handleSessionConnected(SessionConnectEvent event) {
		MessageHeaders headers = event.getMessage().getHeaders();
		String sessionId = (String) headers.get(SIMP_SESSION_ID);
		Map<String, LinkedList> nativeHeaders = (Map<String, LinkedList>) headers.get(NATIVE_HEADERS);
		List<String> usernameList = nativeHeaders.get(USER_NAME);
		if (usernameList == null) return;
		String username = usernameList.get(0);

		ConnectEvent evt = new ConnectEvent(ADD,username,sessionId);
		sessionRepository.add(sessionId, evt);
		notifyEvent(evt);

	}

	@EventListener
	private void handleSessionDisconnect(SessionDisconnectEvent event) {

		String sessionId = event.getSessionId();
		ConnectEvent o = sessionRepository.get(sessionId);
		if (o == null) return;
		String username = o.getUsername();

		Object evt = new ConnectEvent(DEL,username,sessionId);
		Optional.ofNullable(sessionRepository.get(sessionId))
			.ifPresent(x -> {
				sessionRepository.remove(sessionId); 
				notifyEvent(evt);
		});
	}

}
