package net.ion.system;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

// STOMP is text based but also allows for the transmission of binary messages.

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
    	super.configureMessageBroker(registry);

    	ThreadPoolTaskScheduler pingScheduler = new ThreadPoolTaskScheduler();
    	pingScheduler.setThreadNamePrefix("pingJob");
    	pingScheduler.initialize();
		registry.enableSimpleBroker("/topic","/queue")
			.setHeartbeatValue(new long[]{10000, 10000})
			.setTaskScheduler(pingScheduler);
        registry.configureBrokerChannel().taskExecutor().keepAliveSeconds(20);
        registry.setApplicationDestinationPrefixes("/chat","/event");

		// registry.configureBrokerChannel().setInterceptors(interceptors);

    }
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/endpoint").withSockJS();
	}

}