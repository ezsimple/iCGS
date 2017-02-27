package net.ion.event;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository
public class SessionRepository {
	
	protected Log logger = LogFactory.getLog(this.getClass());
	private Map<String, ConnectEvent> activeSessions = new ConcurrentHashMap<>();

	private void log(String prefix,String sessionId) {
		logger.debug(prefix+" : "+ get(sessionId).getUsername());
	}

	public void add(String sessionId, ConnectEvent event) {
		activeSessions.put(sessionId, event);
		log("add", sessionId);
	}

	public void remove(String sessionId) {
		log("del", sessionId);
		activeSessions.remove(sessionId);
	}

	public ConnectEvent get(String sessionId) {
		return activeSessions.get(sessionId);
	}

	public Map<String, ConnectEvent> getActiveSessions() {
		return activeSessions;
	}

	public void setActiveSessions(Map<String, ConnectEvent> activeSessions) {
		this.activeSessions = activeSessions;
	}

}
