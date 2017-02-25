package net.ion.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

public class ConnectionEventListener {

	protected Log logger = LogFactory.getLog(this.getClass());

	@EventListener
	private void handleSessionConnected(SessionConnectEvent event) {
	
		String name = event.getMessage().getHeaders().get("simpHeartbeat").toString();
		logger.warn(event.getMessage().toString());
		logger.warn(name);

		SimpMessageHeaderAccessor headers =
		SimpMessageHeaderAccessor.wrap(event.getMessage());
		String username = headers.getSubscriptionId();
		logger.warn(headers);
		//
		// LoginEvent loginEvent = new LoginEvent(username);
		// messagingTemplate.convertAndSend(loginDestination, loginEvent);
		//
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
