package net.ion.system;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.trace.InMemoryTraceRepository;
import org.springframework.boot.actuate.trace.TraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.messaging.support.ExecutorSubscribableChannel;

@Configuration
public class WebSocketTraceChannelInterceptorConfiguration {
	
	protected Log logger = LogFactory.getLog(this.getClass());

	@Value("${management.websocket.trace-inbound:true}")
	private boolean enableTraceInboundChannel;

	@Value("${management.websocket.trace-outbound:false}")
	private boolean enableTraceOutboundChannel;

	@Autowired
	private ExecutorSubscribableChannel clientInboundChannel;

	@Autowired
	private ExecutorSubscribableChannel clientOutboundChannel;

	private TraceRepository traceRepository = new InMemoryTraceRepository();
	
	@Bean
	@Description("Spring Actuator endpoint to expose WebSocket traces")
	public WebSocketTraceEndpoint websocketTraceEndpoint() {
		return new WebSocketTraceEndpoint(traceRepository);
	}

	@Bean
	public WebSocketTraceChannelInterceptor webSocketTraceChannelInterceptor() {
		return new WebSocketTraceChannelInterceptor(traceRepository);
	}

	@PostConstruct
	private void addTraceInterceptor() {
		if (enableTraceInboundChannel) {
			clientInboundChannel.addInterceptor(webSocketTraceChannelInterceptor());
		}

		if (enableTraceOutboundChannel) {
			clientOutboundChannel.addInterceptor(webSocketTraceChannelInterceptor());
		}
	}

}
