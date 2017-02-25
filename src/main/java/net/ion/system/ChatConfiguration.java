package net.ion.system;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;

import net.ion.event.SessionEventListener;

@Configuration
public class ChatConfiguration {
	protected Log logger = LogFactory.getLog(this.getClass());

	@Bean
	@Description("stomp session connected/disconnected detection")
	public SessionEventListener connectionEventListener() {
		return new SessionEventListener();
	}

	@Bean
	@Description("Spring Actuator endpoint to expose WebSocket stats")
	public WebSocketEndpoint websocketEndpoint(WebSocketMessageBrokerStats stats) {
		logger.info(stats.getWebSocketSessionStatsInfo());
		return new WebSocketEndpoint(stats);
	}

}
