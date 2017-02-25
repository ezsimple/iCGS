package net.ion.event;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

public class SessionEventListener {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	SessionRepository sessionRepository;

	public final String SIMP_SESSION_ID = "simpSessionId";
	public final String NATIVE_HEADERS = "nativeHeaders";
	public final String USER_NAME = "username";

	@EventListener
	private void handleSessionConnected(SessionConnectEvent event) {

		MessageHeaders headers = event.getMessage().getHeaders();
		String sessionId = (String) headers.get(SIMP_SESSION_ID);
		Map<String, LinkedList> nativeHeaders = (Map<String, LinkedList>) headers.get(NATIVE_HEADERS);
		List<String> usernameList = nativeHeaders.get(USER_NAME);
		String username = usernameList.get(0);
		// logger.info(sessionId);
		// logger.info(nativeHeaders);
		// if (usernameList != null) {
		//	logger.info(usernameList.get(0));
		// }
		sessionRepository.add(sessionId, new ConnectEvent(username));
	}

	@EventListener
	private void handleSessionDisconnect(SessionDisconnectEvent event) {

		String sessionId = event.getSessionId();
		Optional.ofNullable(sessionRepository.get(sessionId))
			.ifPresent(x -> {sessionRepository.remove(sessionId);});
	}

}
