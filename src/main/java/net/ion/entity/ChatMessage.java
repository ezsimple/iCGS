package net.ion.entity;

import java.util.Date;

public class ChatMessage {
	
    private String mesg;
    private Date reqTime;

    @SuppressWarnings("unused")
	private ChatMessage() { }

    public ChatMessage(String mesg) {
        this.mesg = mesg;
    }
    
    public ChatMessage setReqtime(Date reqTime) {
    	this.reqTime = reqTime;
    	return this;
    }

    public String getMesg() {
        return this.mesg;
    }
    
    public Date getReqtime() {
    	return this.reqTime;
    }


}
