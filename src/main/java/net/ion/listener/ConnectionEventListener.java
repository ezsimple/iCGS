package net.ion.listener;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

public class ConnectionEventListener {

	protected Log logger = LogFactory.getLog(this.getClass());

	public final String SIMP_SESSION_ID = "simpSessionId";
    public final String NATIVE_HEADERS = "nativeHeaders";
    public final String BIZ_ID = "username";

	@EventListener
	private void handleSessionConnected(SessionConnectEvent event) {
	
//		logger.warn(event.getMessage().toString());
		
        MessageHeaders headers = event.getMessage().getHeaders();
        String sessionId = (String) headers.get(SIMP_SESSION_ID);
        Map<String, LinkedList> nativeHeaders = (Map<String, LinkedList>) headers.get(NATIVE_HEADERS);
        List<String> bizIdList = nativeHeaders.get(BIZ_ID);
        logger.info(sessionId);
        logger.info(nativeHeaders);
        if (bizIdList != null) {
           logger.info(bizIdList.get(0));
        }

		// // We store the session as we need to be idempotent in the disconnect
		// event processing
		// participantRepository.add(headers.getSessionId(), loginEvent);
	}

	@EventListener
	private void handleSessionDisconnect(SessionDisconnectEvent event) {
	
		logger.warn(event.getSessionId());

		// Optional.ofNullable(participantRepository.getParticipant(event.getSessionId()))
		// .ifPresent(login -> {
		// messagingTemplate.convertAndSend(logoutDestination, new
		// LogoutEvent(login.getUsername()));
		// participantRepository.removeParticipant(event.getSessionId());
		// });
	}

}
