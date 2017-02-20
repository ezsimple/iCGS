package net.ion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import net.ion.entity.BackMessage;

@Controller
public class BackController {
	private static SimpMessageSendingOperations messagingTemplate;

	@Autowired
	public BackController(SimpMessageSendingOperations messagingTemplate) {
	    this.messagingTemplate = messagingTemplate;
	}
	
	public static void sendBack(final String topic, BackMessage mesg) {
		BackController.messagingTemplate.convertAndSend(topic, mesg);
	}

}
