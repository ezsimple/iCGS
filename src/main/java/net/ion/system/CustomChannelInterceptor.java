package net.ion.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

public class CustomChannelInterceptor extends ChannelInterceptorAdapter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		logger.debug("preSend :"+channel);
		return super.preSend(message, channel);
	}

	@Override
	public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
		logger.debug("postSend :"+channel);
		super.postSend(message, channel, sent);
	}

	@Override
	public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
		logger.debug("afterSendCompletion :"+channel);
		super.afterSendCompletion(message, channel, sent, ex);
	}

	public boolean preReceive(MessageChannel channel) {
		logger.debug("preReceive :"+channel);
		return super.preReceive(channel);
	}

	@Override
	public Message<?> postReceive(Message<?> message, MessageChannel channel) {
		logger.debug("postReceive :"+channel);
		return super.postReceive(message, channel);
	}

	@Override
	public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
		logger.debug("afterReceiveCompletion :"+channel);
		super.afterReceiveCompletion(message, channel, ex);
	}

}
