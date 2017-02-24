package net.ion.system;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;

import net.ion.listener.ConnectionEventListener;

@Configuration
public class ChatConfiguration {

	@Bean
	@Description("Tracks user presence (join / leave) and broacasts it to all connected users")
	public ConnectionEventListener connectionEventListener() {
		return new ConnectionEventListener();
	}

	@Bean
	@Description("Spring Actuator endpoint to expose WebSocket stats")
	public WebSocketEndpoint websocketEndpoint(WebSocketMessageBrokerStats stats) {
		return new WebSocketEndpoint(stats);
	}

}
