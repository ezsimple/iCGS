package net.ion.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;

public class BrokerAvailabilityListener implements ApplicationListener<BrokerAvailabilityEvent> {
	private final Logger logger = LoggerFactory.getLogger(BrokerAvailabilityListener.class);

	@Override
	public void onApplicationEvent(BrokerAvailabilityEvent event) {
		logger.info("BrokerAvailabilityEvent timestamp: " + event.getTimestamp());
		logger.info("BrokerAvailabilityEvent brokerAvailable: " + event.isBrokerAvailable());
		logger.info("BrokerAvailabilityEvent: " + event.toString());
	}

}
