package net.ion.entity;

public class ChatMessage {
	
    private String name;

    @SuppressWarnings("unused")
	private ChatMessage() { }

    public ChatMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
