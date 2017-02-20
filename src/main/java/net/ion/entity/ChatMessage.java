package net.ion.entity;

import lombok.Data;

@Data
public class ChatMessage {
	
    private String text;

	private ChatMessage() { }

    public ChatMessage(String text) {
        this.text = text;
    }
    
}